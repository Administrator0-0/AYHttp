package com.example.ayhttp;

import java.io.IOException;

public class CallServerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpCodec httpCodec = chain.httpCodec();
        long sentRequestMillis = System.currentTimeMillis();
        httpCodec.writeRequest(request);
        Response.Builder responseBuilder = null;
        if (request.header("Expect").equalsIgnoreCase("100-continue")){
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





        return null;
    }
}
