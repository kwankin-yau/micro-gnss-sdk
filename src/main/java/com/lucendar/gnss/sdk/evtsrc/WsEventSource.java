package com.lucendar.gnss.sdk.evtsrc;

import com.lucendar.gnss.sdk.HttpConsts;
import com.lucendar.gnss.sdk.types.GnssApiConnParams;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class WsEventSource extends StompSessionHandlerAdapter implements GnssWs {

    public static final Logger LOGGER = LoggerFactory.getLogger(WsEventSource.class);

    /**
     * 默认重连间隔，单位：毫秒
     */
    public static final int DEFAULT_RECONNECT_INTERVAL = 10_000;

    private final TaskScheduler taskScheduler;

    private final WebSocketStompClient stompClient;
    private final String appId;
    private final String token;
    private final String wsUrl;
    private final String username;
    private final String password;

    private final int reconnectInterval;

    static class ManagedListener {
        GnssEventListener listener;
        AtomicBoolean afterSubscribeNotified = new AtomicBoolean();
    }
    private final ManagedListener[] listeners = new ManagedListener[GnssEventType.values().length];

    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicReference<StompSession> session = new AtomicReference<>();

    private String destOf(String queueName) {
        return "/user/" + token + "/queue/" + queueName;
    }

    private void _subscribe(StompSession session, GnssEventType eventType) {
        String dest = destOf(eventType.name());
        LOGGER.debug("Subscribe " + dest);
        session.subscribe(dest, new GnssStompFrameHandler(eventType));

        int idx = eventType.ordinal();
        ManagedListener l = listeners[idx];
        if (l != null) {
            if (l.afterSubscribeNotified.compareAndSet(false, true)) {
                l.listener.afterSubscribed(eventType);
            }
        }

    }

    private void doSubscribe(StompSession session) {
        for (GnssEventType type : GnssEventType.values()) {
            int idx = type.ordinal();
            ManagedListener l = listeners[idx];
            if (l != null)
                _subscribe(session, type);
        }
    }

    @Override
    public void afterConnected(@NotNull StompSession session, @NotNull StompHeaders connectedHeaders) {
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
            }, Instant.now().plusMillis(reconnectInterval));
        }
    }

    @Override
    public void handleException(
            @NotNull StompSession session,
            @NotNull StompCommand command,
            @NotNull StompHeaders headers,
            @NotNull byte[] payload,
            @NotNull Throwable exception) {
        onException(session, exception);
    }

    @Override
    public void handleTransportError(@NotNull StompSession session, @NotNull Throwable exception) {
        onException(session, exception);
    }

    class GnssStompFrameHandler implements StompFrameHandler {
        private final GnssEventType eventType;

        public GnssStompFrameHandler(GnssEventType eventType) {
            this.eventType = eventType;
        }

        @Override
        public Type getPayloadType(@NotNull StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(@NotNull StompHeaders headers, Object payload) {
            if (payload == null)
                return;

            LOGGER.debug("handleFrame: " + payload);
            String s = payload.toString();
            GnssEvent event = GnssEvent.of(eventType, s);
            WsEventSource.this.notify(event);
        }
    }


    public WsEventSource(
            TaskScheduler taskScheduler,
            GnssApiConnParams connParams,
            String token,
            GnssEventListenerReg[] listenerRegs,
            int reconnectInterval
            ) {
        for (GnssEventListenerReg reg : listenerRegs) {
            GnssEventType eventType = reg.getEventType();
            int idx = eventType.ordinal();
            listeners[idx] = new ManagedListener();
            listeners[idx].listener = reg.getListener();
        }

        this.taskScheduler = taskScheduler;

        String wsUrl = connParams.getApiBasePath().replace("http://", "ws://")
                .replace("https://", "wss://");
        if (!wsUrl.endsWith("/"))
            wsUrl += "/";
        if (!wsUrl.endsWith("/v1/"))
            wsUrl += "v1/";
        wsUrl += "ws";
        this.wsUrl = wsUrl;

        this.appId = connParams.getAppId();
        this.token = token;
        this.username = connParams.getUsername();
        this.password = connParams.getPassword();
        this.reconnectInterval = reconnectInterval;

        StandardWebSocketClient client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(taskScheduler);
    }

    public WsEventSource(
            TaskScheduler taskScheduler,
            GnssApiConnParams connParams,
            String token,
            GnssEventListenerReg[] listenerRegs) {
        this(taskScheduler,
                connParams,
                token,
                listenerRegs,
                DEFAULT_RECONNECT_INTERVAL);
    }

    protected void notify(GnssEvent event) {
        LOGGER.debug("notify " + event);
        for (ManagedListener l : listeners) {
            if (l != null) {
                try {
                    l.listener.onEvent(event);
                } catch (Throwable t) {
                    LOGGER.error("Error occurred when notify event: " + event + " to " + l, t);
                }
            }
        }
    }

    @Override
    public void cancel() {
        stompClient.stop();
    }

    /**
     * The WebSocketHttpHeaders(spring-websocket: 6.0.8) has a bug on forEach
     * which does not delegate the operation to the private headers.
     *
     * It likes this bug has been fixed in 6.0.10.
     */
    static class Headers extends WebSocketHttpHeaders {
        @Override
        public void forEach(@NotNull BiConsumer<? super String, ? super List<String>> action) {
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
        headers.add(HttpConsts.HEADER_X_APP_ID, appId);
        headers.add(HttpConsts.HEADER_X_AUTH_TOKEN, token);
        headers.add("login", username);
        headers.add("passcode", password);

        stompClient.connect(wsUrl, headers, this);
    }

    protected void disconnect() {
        StompSession sess = this.session.get();
        if (sess != null) {
            if (this.session.compareAndSet(sess, null)) {
                sess.disconnect();
            }
        }
    }

    @Override
    public boolean isConnected() {
        return session.get() != null;
    }

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public void open() {
        if (active.compareAndSet(false, true)) {
            LOGGER.debug("open()");
            connect();
        }
    }

    @Override
    public void close() {
        if (active.compareAndSet(true, false)) {
            LOGGER.debug("close()");
            disconnect();
        }
    }

    @Override
    public void handleFrame(@NotNull StompHeaders headers, Object payload) {
        super.handleFrame(headers, payload);
        LOGGER.debug("Received frame: " + payload);
    }

}

