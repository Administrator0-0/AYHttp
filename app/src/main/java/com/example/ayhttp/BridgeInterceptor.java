package com.example.ayhttp;

import java.io.IOException;

public class BridgeInterceptor implements Interceptor{


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        Response response = chain.proceed(request, null, chain.streamAllocation(), null);

        return response;
    }
}
