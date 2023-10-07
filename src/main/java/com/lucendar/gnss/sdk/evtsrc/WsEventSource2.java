package com.lucendar.gnss.sdk.evtsrc;

import com.lucendar.common.stomp.ReconnectScheduler;
import com.lucendar.common.stomp.StompClient;
import com.lucendar.common.stomp.StompFrame;
import com.lucendar.common.stomp.StompListener;
import com.lucendar.common.stomp.SubscribeReq;
import com.lucendar.common.stomp.SubscriptionListener;
import com.lucendar.common.stomp.WebSocketCloseStatus;
import com.lucendar.gnss.sdk.HttpConsts;
import com.lucendar.gnss.sdk.types.GnssApiConnParams;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Stomp-Websocket Event Source which use `okhttp` and `stomp-lib`.
 */
public class WsEventSource2 implements GnssEventSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsEventSource2.class);

    private final StompClient stompClient;

    private static class ListenerEntry {
        GnssEventType eventType;
        GnssEventListener listener;
    }

    /**
     * key: event type name
     */
    private final Map<String, ListenerEntry> listenerEntries = new HashMap<>();

    private final StompListener stompListener = new StompListener() {

        @Override
        public void afterConnected(@NotNull StompClient stompClient) {
        }

        @Override
        public void onErrorFrameReceived(@NotNull StompClient stompClient, @NotNull StompFrame stompFrame) {
            stompClient.close(WebSocketCloseStatus.NORMAL_CLOSURE);
        }
    };


    /**
     * @param connParams
     * @param reconnectScheduler
     * @param client
     * @param token
     * @param listenerRegs
     * @param reconnectInterval reconnect interval in milli-seconds
     * @implNote the `onFailure` method will be called on all listeners when the underlying websocket encountered
     * failure.
     */
    public WsEventSource2(
            GnssApiConnParams connParams,
            ReconnectScheduler reconnectScheduler,
            OkHttpClient client,
            String token,
            GnssEventListenerReg[] listenerRegs,
            int reconnectInterval) {

        String wsUrl = connParams.getApiBasePath().replace("http://", "ws://")
                .replace("https://", "wss://");
        if (!wsUrl.endsWith("/"))
            wsUrl += "/";
        if (!wsUrl.endsWith("/v1/"))
            wsUrl += "v1/";
        wsUrl += "ws";

        Request.Builder builder = new Request.Builder()
                .url(wsUrl);

        builder.header(HttpConsts.HEADER_X_AUTH_TOKEN, token);
        if (connParams.getAppId() != null)
            builder.header(HttpConsts.HEADER_X_APP_ID, connParams.getAppId());
        Request request = builder.build();

        SubscribeReq[] reqs = new SubscribeReq[listenerRegs.length];
        for (int i = 0; i < listenerRegs.length; i++) {
            GnssEventListenerReg reg = listenerRegs[i];
            SubscriptionListener subscriptionListener = new SubscriptionListener() {
                @Override
                public void onMessage(@NotNull String subscriptionId, @NotNull StompFrame stompFrame) {
                    int p = subscriptionId.indexOf('-');
                    if (p < 0)
                        return;

                    String typ = subscriptionId.substring(0, p);
                    if (typ.isEmpty())
                        return;

                    ListenerEntry e = listenerEntries.get(typ);
                    if (e != null) {
                        GnssEvent event = GnssEvent.of(e.eventType, stompFrame.getBody());
                        try {
                            e.listener.onEvent(event);
                        } catch (Throwable t) {
                            LOGGER.error("Error occurred when call `GnssEventListener.onEvent()`.", t);
                        }
                    }
                }
            };
            reqs[i] = new SubscribeReq(reg.getEventType().name(), reg.wsDestOfQueue(token), subscriptionListener);
            ListenerEntry entry = new ListenerEntry();
            entry.eventType = reg.getEventType();
            entry.listener = reg.getListener();

            listenerEntries.put(entry.eventType.name(), entry);
        }

        stompClient = new StompClient(
                reconnectScheduler,
                client,
                connParams.getUsername(),
                connParams.getPassword(),
                request,
                reqs,
                stompListener,
                15_0000,
                reconnectInterval);
    }


    @Override
    public void cancel() {
        stompClient.cancel();
    }
}
