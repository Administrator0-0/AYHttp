package com.example.ayhttp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

class HttpCodec {

    private InputStream in;
    private OutputStream out;

    HttpCodec(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }

    void writeHeader(Request request){
        ArrayList<String> strings = new ArrayList<>();
        strings.add(request.header.type + " / " + "HTTP/1.1\r\n");
        for (HashMap.Entry entry : request.header.headers.entrySet()){
            strings.add(entry.getKey() +  ": " + entry.getValue() + "\r\n");
        }
        strings.add("\r\n");
        request.header.address = new InetSocketAddress(request.header.url, 80);
        int count = 0;
        for (String str : strings){
            request.request[count] = str;
            count++;
        }
    }
}
