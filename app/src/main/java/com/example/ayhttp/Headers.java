package com.example.ayhttp;

import java.net.SocketAddress;
import java.util.HashMap;

class Headers {
    String type;
    String url;
    SocketAddress address;
    HashMap<String, String> headers = new HashMap<>();

    Headers(String type, String url){
        this.type = type;
        this.url = url;
    }

    void addHeader(String key, String value){
        headers.put(key, value);
    }



}
