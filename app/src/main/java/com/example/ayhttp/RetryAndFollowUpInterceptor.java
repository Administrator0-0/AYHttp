package com.example.ayhttp;

import java.io.IOException;
import java.net.InetSocketAddress;

class RetryAndFollowUpInterceptor implements Interceptor{

    private AYHttpClient client;

    RetryAndFollowUpInterceptor(AYHttpClient client){
        this.client = client;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        StreamAllocation streamAllocation = new StreamAllocation(client.connectionPool,
                new InetSocketAddress(request.headers.url, request.headers.port));
        return chain.proceed(request, null, streamAllocation, null);
    }
}
