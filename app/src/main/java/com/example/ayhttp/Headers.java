package com.example.ayhttp;

import java.net.SocketAddress;
import java.util.HashMap;

class Headers {
    String type;
    String url;
    int port;
    SocketAddress address;
    HashMap<String, String> headers = new HashMap<>();

    Headers(String type, String url, int port){
        this.type = type;
        this.url = url;
        this.port = port;
    }

    void addHeader(String key, String value){
        headers.put(key, value);
    }



}
