package com.lucendar.gnss.sdk.clnt;

import okhttp3.Request;

import java.util.function.Consumer;

public interface ReqHooker extends Consumer<Request> {

    void use(Request req, String method, String path, String qryParams);

    void useBody(String body);

    default void use(Request req) {
        var url = req.url();
        var method = req.method();
        var path = url.encodedPath();
        var qryParams = url.encodedQuery();

        use(req, method, path, qryParams);
    }


    @Override
    default void accept(Request request) {
        use(request);
    }
}
