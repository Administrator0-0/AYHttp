package com.example.ayhttp;

import java.io.IOException;
import java.net.ProtocolException;

public class CallServerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpCodec httpCodec = chain.httpCodec();
        long sentRequestMillis = System.currentTimeMillis();
        httpCodec.writeRequest(request);
        Response.Builder responseBuilder = null;
        if (request.header("Expect") != null && request.header("Expect").equalsIgnoreCase("100-continue")){
            httpCodec.finishRequest();
            responseBuilder = httpCodec.readResponseHeaders(true);
        }
        if (responseBuilder == null) {

        }
        httpCodec.finishRequest();
        if (responseBuilder == null){
            responseBuilder = httpCodec.readResponseHeaders(false);
        }
        Response response = responseBuilder
                .request(request)
                .sentRequestAtMillis(sentRequestMillis)
                .receivedResponseAtMillis(System.currentTimeMillis())
                .build();
        int code = response.code;
        if (code == 100){
            responseBuilder = httpCodec.readResponseHeaders(false);
            response = responseBuilder
                    .request(request)
                    .sentRequestAtMillis(sentRequestMillis)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .build();
            code = response.code;
        }
        response = response.newBuilder()
                .body(httpCodec.openResponseBody(response))
                .build();

        if ((code == 204 || code == 205) && response.body().contentLength() > 0) {
            throw new ProtocolException(
                    "HTTP " + code + " had non-zero Content-Length: " + response.body().contentLength());
        }

        return response;
    }
}
