package com.lucendar.gnss.sdk.evtsrc;

import com.lucendar.gnss.sdk.HttpConsts;
import com.lucendar.gnss.sdk.types.GnssApiConnParams;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class SseEventSource implements GnssEventSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SseEventSource.class);

    private final OkHttpClient httpClient;

    private final GnssApiConnParams connParams;

    private final String token;

    class TypedEventSourceListener extends EventSourceListener {
        private final GnssEventType eventType;
        private final GnssEventListener gnssEventListener;

        public TypedEventSourceListener(GnssEventType eventType, GnssEventListener gnssEventListener) {
            this.eventType = eventType;
            this.gnssEventListener = gnssEventListener;
        }

        @Override
        public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
            GnssEvent event = GnssEvent.of(eventType, data);
            gnssEventListener.onEvent(event);
        }

        @Override
        public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
            gnssEventListener.onFailure(eventType, t);
        }

        @Override
        public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
            gnssEventListener.afterSubscribed(eventType);
        }
    }

//    private final EventSourceListener eventSourceListener = new EventSourceListener() {
//
//        @Override
//        public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
//            LOGGER.debug("onOpen");
//
//            for (GnssEventType typ : GnssEventType.values()) {
//                int idx = typ.ordinal();
//                GnssEventListener l = listeners[idx];
//                if (l != null)
//                    l.afterSubscribed(typ);
//            }
//        }
//
//        @Override
//        public void onClosed(@NotNull EventSource eventSource) {
//            super.onClosed(eventSource);
//        }
//
//        @Override
//        public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
//            LOGGER.debug("onEvent: id={}, type={}, data={}", id, type, data);
//
//            if (type == null)
//                return;
//
//            GnssEventType eventType = GnssEventType.tryParse(type);
//            if (eventType == null)
//                return;
//
//            GnssEventListener l = listeners[eventType.ordinal()];
//            if (l != null) {
//                GnssEvent event = GnssEvent.of(eventType, data);
//                l.on(event);
//            }
//        }
//
//        @Override
//        public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
//            LOGGER.error("onFailure", t);
//        }
//    };

//    private final GnssEventListener[] listeners = new GnssEventListener[GnssEventType.values().length];
    private final EventSource[] eventSources = new EventSource[GnssEventType.values().length];

    private static Request.Builder requestBuilder(GnssApiConnParams connParams, String token, String url) {
        Request.Builder r = new Request.Builder()
                .header(HttpHeaders.AUTHORIZATION, connParams.authorizationHeaderValue())
                ;

        if (token != null)
            r.header(HttpConsts.HEADER_X_AUTH_TOKEN, token);

        if (connParams.getAppId() != null)
            r.header(HttpConsts.HEADER_X_APP_ID, connParams.getAppId());

        r.url(url);

        return r;
    }

    public SseEventSource(
            OkHttpClient httpClient,
            GnssApiConnParams connParams,
            String token,
            GnssEventListenerReg[] listeners) {
        this.httpClient = httpClient;
        this.connParams = connParams;
        this.token = token;

        for (GnssEventListenerReg reg : listeners) {
            GnssEventType eventType = reg.getEventType();
            TypedEventSourceListener l = new TypedEventSourceListener(eventType, reg.getListener());


//            this.listeners[eventType.ordinal()] = reg.getListener();
            Request.Builder rb = requestBuilder(connParams, token, connParams.getApiBasePath() + "/sse/" + eventType.name());
            eventSources[eventType.ordinal()] = EventSources.createFactory(httpClient).newEventSource(rb.build(), l);
        }
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public GnssApiConnParams getConnParams() {
        return connParams;
    }

    public String getToken() {
        return token;
    }

    @Override
    public void cancel() {
        for (int i = 0; i < eventSources.length; i++) {
            EventSource eventSource = eventSources[i];

            if (eventSource != null) {
                eventSource.cancel();
                eventSources[i] = null;
            }
        }
    }
}
