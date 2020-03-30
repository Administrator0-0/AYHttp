package com.example.ayhttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Call{

    private Request request;
    private AYHttpClient mClient;
    private boolean executed;

    public Call(Request request, AYHttpClient client){
        this.request = request;
        this.mClient = client;
    }

    public void enquene(){
        synchronized (this){
            if (executed) throw new IllegalStateException("Already executed");
            executed = true;
        }
      mClient.dispatcher.enqueue(new AsyncCall());
    }

    public Response execute(){
        synchronized (this){
            if (executed) throw new IllegalStateException("Already executed");
            executed = true;
        }
        try {
            mClient.dispatcher.executed(new SyncCall());
            return getResponseByInterceptorChain();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    Response getResponseByInterceptorChain() throws IOException {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(mClient.interceptors);
        interceptors.add(new BridgeInterceptor());
        interceptors.add(new ConnectInterceptor(mClient));
        interceptors.add(new CallServerInterceptor());
        Chain chain = new Chain(interceptors, null, 0, request, this,null,
                mClient.connectTimeout, mClient.readTimeout, mClient.writeTimeout);
        return chain.proceed(request);
    }

}
