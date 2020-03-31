package com.example.ayhttp;

import java.net.SocketAddress;
import java.util.HashMap;

class Headers {
    String protocol;
    String url;
    String type;
    int port;
    SocketAddress address;
    HashMap<String, String> headers = new HashMap<>();

    Headers(){

    }

    Headers(String protocol, String url, String type, int port){
        this.protocol = protocol;
        this.url = url;
        this.type = type;
        this.port = port;
    }

    void addHeader(String key, String value){
        headers.put(key, value);
    }



}
