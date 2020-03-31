package com.example.ayhttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Call{
    private Request request;
    private AYHttpClient mClient;
    private boolean executed;
    private RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;

    Call(Request request, AYHttpClient client){
        this.request = request;
        this.mClient = client;
        retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(client);
    }

    public void enqueue(Callback callback){
        synchronized (this){
            if (executed) throw new IllegalStateException("Already executed");
            executed = true;
        }
      mClient.dispatcher.enqueue(new AsyncCall(callback));
    }

    public Response execute(){
        return new SyncCall().execute();
    }

    final class AsyncCall implements Runnable {

        private final Callback callback;

        AsyncCall(Callback callback){
            this.callback = callback;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response response = getResponseByInterceptorChain();
//                if (false) {
//                    signalledCallback = true;
//                    callback.onFailure(Call.this, new IOException("Canceled"));
//                } else {
                    signalledCallback = true;
                    callback.onResponse(Call.this, response);
//                }
            } catch (IOException e) {
                if (signalledCallback) {

                } else {
                    callback.onFailure(Call.this, e);
                }
            } finally {
                mClient.dispatcher.finished(this);
            }
        }
    }

    final class SyncCall {

        Response execute(){
            synchronized (this){
                if (executed) throw new IllegalStateException("Already executed");
                executed = true;
            }
            try {
                mClient.dispatcher.executed(new SyncCall());
                return getResponseByInterceptorChain();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mClient.dispatcher.finished(this);
            }
            return null;
        }
    }

    private Response getResponseByInterceptorChain() throws IOException {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(mClient.interceptors);
        interceptors.add(retryAndFollowUpInterceptor);
        interceptors.add(new BridgeInterceptor());
        interceptors.add(new ConnectInterceptor(mClient));
        interceptors.add(new CallServerInterceptor());
        Chain chain = new Chain(interceptors, null, 0, request, this,
                null, null,
                mClient.connectTimeout, mClient.readTimeout, mClient.writeTimeout);
        return chain.proceed(request);
    }

}
