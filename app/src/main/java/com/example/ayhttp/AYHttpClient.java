package com.example.ayhttp;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AYHttpClient {
    final Dispatcher dispatcher;
    final List<Interceptor> interceptors;
    final int connectTimeout;
    final int readTimeout;
    final int writeTimeout;
    final ConnectionPool connectionPool;


    public AYHttpClient(){
        this(new Builder());
    }

    private AYHttpClient(Builder builder){
        this.dispatcher = builder.dispatcher;
        this.interceptors = builder.interceptors;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.connectionPool = builder.connectionPool;
    }

    public Call newCall(Request request){
        return new Call(request, this);
    }

    public static final class Builder{
        Dispatcher dispatcher;
        final List<Interceptor> interceptors = new ArrayList<>();
        int connectTimeout;
        int readTimeout;
        int writeTimeout;
        ConnectionPool connectionPool;

        Builder(){
            dispatcher = new Dispatcher();
            connectTimeout = 10_000;
            readTimeout = 10_000;
            writeTimeout = 10_000;
        }

        public Builder connectTimeout(long timeout, TimeUnit unit) {
            connectTimeout = Util.getDuration("connectTimeout", timeout, unit);
            return this;
        }

        public Builder readTimeout(long timeout, TimeUnit unit) {
            connectTimeout = Util.getDuration("readTimeout", timeout, unit);
            return this;
        }

        public Builder writeTimeout(long timeout, TimeUnit unit) {
            connectTimeout = Util.getDuration("writeTimeout", timeout, unit);
            return this;
        }

        public AYHttpClient build(){
            return new AYHttpClient(this);
        }
    }
}
