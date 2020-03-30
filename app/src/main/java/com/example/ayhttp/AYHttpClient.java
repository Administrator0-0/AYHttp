package com.example.ayhttp;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AYHttpClient {

    Dispatcher dispatcher;
    final List<Interceptor> interceptors;
    int connectTimeout;
    int readTimeout;
    int writeTimeout;


    AYHttpClient(Builder builder){
        this.dispatcher = builder.dispatcher;
        this.interceptors = builder.interceptors;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
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

        public Builder(){
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
