package com.example.ayhttp;


import java.io.IOException;

class ConnectInterceptor implements Interceptor{

    private AYHttpClient client;

    public ConnectInterceptor(AYHttpClient client){
        this.client = client;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request  = chain.request();
        HttpCodec httpCodec = new HttpCodec();
        Connection connection = new Connection();
        return chain.proceed(request, httpCodec, connection);
    }
}
