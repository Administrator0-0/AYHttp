package com.example.ayhttp;


import java.io.IOException;

class ConnectInterceptor implements Interceptor{

    private AYHttpClient client;

    ConnectInterceptor(AYHttpClient client){
        this.client = client;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request  = chain.request();
        StreamAllocation streamAllocation = chain.streamAllocation();
        HttpCodec httpCodec = streamAllocation.newStream(client, chain);
        Connection connection = streamAllocation.connection();
        return chain.proceed(request, httpCodec, chain.streamAllocation(), connection);
    }
}
