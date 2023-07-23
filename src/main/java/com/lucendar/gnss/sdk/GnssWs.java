package com.lucendar.gnss.sdk;

import com.lucendar.gnss.sdk.types.GnssEvent;
import com.lucendar.gnss.sdk.types.GnssEventListener;
import com.lucendar.gnss.sdk.types.GnssEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class GnssWs extends StompSessionHandlerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(GnssWs.class);

    /**
     * 默认重连间隔，单位：秒
     */
    public static final int DEFAULT_RECONNECT_INTERVAL = 10;

    private final TaskScheduler taskScheduler;

    private final WebSocketStompClient stompClient;
    private final String appId;
    private final String token;
    private final String wsUrl;
    private final String username;
    private final String password;

    private final int reconnectInterval;
    private final GnssEventType[] subscribedEventTypes;
    protected final ConcurrentLinkedDeque<GnssEventListener>[] listeners = new ConcurrentLinkedDeque[GnssEventType.values().length];

    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicReference<StompSession> session = new AtomicReference<>();

    private String destOf(String queueName) {
        return "/user/" + token + "/queue/" + queueName;
    }

    private void _subscribe(StompSession session, GnssEventType eventType) {
        String dest = destOf(eventType.name());
        LOGGER.debug("Subscribe " + dest);
        session.subscribe(dest, new GnssStompFrameHandler(eventType));
    }

    private void doSubscribe(StompSession session) {
        if (subscribedEventTypes != null) {
            for (GnssEventType typ : subscribedEventTypes) {
                _subscribe(session, typ);
            }
        }
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        doSubscribe(session);
    }

    protected void onException(StompSession session, Throwable throwable) {
        LOGGER.error("Error occurred in stomp session handler: " + throwable.getMessage(), throwable);
        disconnect();

        if (active.get()) {
            taskScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    if (active.get()) {
                        connect();
                    }
                }
            }, Instant.now().plusSeconds(reconnectInterval));
        }
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        onException(session, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        onException(session, exception);
    }

    class GnssStompFrameHandler implements StompFrameHandler {
        private final GnssEventType eventType;

        public GnssStompFrameHandler(GnssEventType eventType) {
            this.eventType = eventType;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            String s = payload.toString();
            GnssEvent event = GnssEvent.of(eventType, s);
            GnssWs.this.notify(event);
        }
    }


    public GnssWs(
            TaskScheduler taskScheduler,
            String appId,
            String apiUrlPrefix,
            String token,
            String username,
            String password,
            GnssEventType[] subscribedEventTypes,
            int reconnectInterval
            ) {
        for (GnssEventType t : GnssEventType.values()) {
            listeners[t.ordinal()] = new ConcurrentLinkedDeque<>();
        }

        this.taskScheduler = taskScheduler;

        String wsUrl = apiUrlPrefix.replace("http://", "ws://")
                .replace("https://", "wss://");
        if (!wsUrl.endsWith("/"))
            wsUrl += "/";
        if (!wsUrl.endsWith("/v1/"))
            wsUrl += "v1/";
        wsUrl += "ws";
        this.wsUrl = wsUrl;

        this.appId = appId;
        this.token = token;
        this.username = username;
        this.password = password;
        this.reconnectInterval = reconnectInterval;
        this.subscribedEventTypes = subscribedEventTypes;

        StandardWebSocketClient client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(taskScheduler);
    }

    public GnssWs(
            TaskScheduler taskScheduler,
            String appId,
            String apiUrlPrefix,
            String token,
            String username,
            String password,
            GnssEventType[] subscribedEventTypes) {
        this(taskScheduler,
                appId,
                apiUrlPrefix,
                token,
                username,
                password,
                subscribedEventTypes,
                DEFAULT_RECONNECT_INTERVAL);
    }

    public GnssWs(
            TaskScheduler taskScheduler,
            String appId,
            String apiUrlPrefix,
            String token,
            String username,
            String password) {
        this(taskScheduler, appId, apiUrlPrefix, token, username, password, GnssEventType.values());
    }
    protected void notify(GnssEvent event) {
        LOGGER.debug("notify " + event);
        ConcurrentLinkedDeque<GnssEventListener> queue = listeners[event.getEventType().ordinal()];

        for (GnssEventListener l : queue) {
            try {
                l.on(event);
            } catch (Throwable t) {
                LOGGER.error("Error occurred when notify event: " + event + " to " + l, t);
            }
        }
    }

    public void addListener(GnssEventType eventType, GnssEventListener listener) {
        listeners[eventType.ordinal()].add(listener);
    }

    public void removeListener(GnssEventType eventType, GnssEventListener listener) {
        listeners[eventType.ordinal()].remove(listener);
    }

    /**
     * The WebSocketHttpHeaders(spring-websocket: 6.0.8) has a bug on forEach
     * which does not delegate the operation to the private headers.
     *
     * It likes this bug has been fixed in 6.0.10.
     */
    static class Headers extends WebSocketHttpHeaders {
        @Override
        public void forEach(BiConsumer<? super String, ? super List<String>> action) {
            try {
                var f = WebSocketHttpHeaders.class.getDeclaredField("headers");
                f.setAccessible(true);
                var headers = (HttpHeaders)f.get(this);
                headers.forEach(action);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void connect() {
        Headers headers = new Headers();
        headers.add(GnssClient.HEADER_APP_ID, appId);
        headers.add(GnssClient.HEADER_X_AUTH_TOKEN, token);
        headers.add("login", username);
        headers.add("passcode", password);

        stompClient.connectAsync(wsUrl, headers, this)
                .thenAccept(sess -> {
                    LOGGER.debug("Websocket session created: " + sess);
                    this.session.set(sess);
                    notify(GnssEvent.of(GnssEventType.after_connect, null));
                });
    }

    protected void disconnect() {
        StompSession sess = this.session.get();
        if (sess != null) {
            if (this.session.compareAndSet(sess, null)) {
                sess.disconnect();
            }
        }
    }

    public boolean isConnected() {
        return session.get() != null;
    }

    public boolean isActive() {
        return active.get();
    }

    public void open() {
        if (active.compareAndSet(false, true)) {
            LOGGER.debug("open()");
            connect();
        }
    }

    public void close() {
        if (active.compareAndSet(true, false)) {
            LOGGER.debug("close()");
            disconnect();
        }
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        super.handleFrame(headers, payload);
        LOGGER.debug("Received frame: " + payload);
    }

}

