package com.example.ayhttp;

import java.io.IOException;
import java.util.List;

class Chain {

    private final List<Interceptor> interceptors;
    private final HttpCodec httpCodec;
    private final int index;
    private final Request request;
    private final Call call;
    private final Connection connection;
    private final StreamAllocation streamAllocation;
    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;

    Chain(List<Interceptor> interceptors, HttpCodec httpCodec, int index, Request request,
                 Call call, Connection connection, StreamAllocation streamAllocation, int connectTimeout, int readTimeout, int writeTimeout){
        this.interceptors = interceptors;
        this.httpCodec = httpCodec;
        this.index = index;
        this.request = request;
        this.call = call;
        this.connection = connection;
        this.streamAllocation = streamAllocation;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }

    Request request(){
        return request;
    }

    HttpCodec httpCodec(){
        return httpCodec;
    }

    Response proceed(Request request) throws IOException {
        return proceed(request, httpCodec, null, connection);
    }

    StreamAllocation streamAllocation(){
        return streamAllocation;
    }

    Response proceed(Request request, HttpCodec httpCodec, StreamAllocation streamAllocation, Connection connection) throws IOException {
        if (index >= interceptors.size())
            throw new IndexOutOfBoundsException("index >= interceptors'size");

        Chain next = new Chain(interceptors, httpCodec, index + 1, request, call, connection,
                null, connectTimeout, readTimeout, writeTimeout);
        Interceptor interceptor = interceptors.get(index);
        Response response = interceptor.intercept(next);
        if (response == null) throw new NullPointerException("Interceptor" + interceptor +
                "return a null response");

        return response;
    }


    int connectTimeoutMillis() {
        return connectTimeout;
    }

    int readTimeoutMillis() {
        return readTimeout;
    }

    int writeTimeoutMillis() {
        return writeTimeout;
    }
}
