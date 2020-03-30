package com.example.ayhttp;

import java.net.SocketAddress;

class StreamAllocation {

    private ConnectionPool connectionPool;
    private SocketAddress address;
    private Connection connection;


    StreamAllocation(ConnectionPool connectionPool, SocketAddress address){
        this.connectionPool = connectionPool;
        this.address = address;
    }


    HttpCodec newStream(AYHttpClient client, Chain chain){
        int connectTimeout = chain.connectTimeoutMillis();
        int readTimeout = chain.readTimeoutMillis();
        int writeTimeout = chain.writeTimeoutMillis();

        connection = new Connection(connectionPool, address);
        connection.connect(connectTimeout, readTimeout);
        return connection.newCodec();
    }

    synchronized Connection connection(){
        return connection;
    }
}
