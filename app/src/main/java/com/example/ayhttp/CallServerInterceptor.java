package com.example.ayhttp;

import java.io.IOException;

public class CallServerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpCodec httpCodec = chain.httpCodec();
        httpCodec.writeHeader(request);

        return null;
    }
}
