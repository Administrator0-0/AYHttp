package com.example.ayhttp;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpCodec {


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
