package com.example.ayhttp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

class HttpCodec {

    private InputStream in;
    private OutputStream out;
    private boolean closed;

    HttpCodec(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }

    void writeHeader(Request request) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(request.header.type + " / " + "HTTP/1.1\r\n");
        for (HashMap.Entry entry : request.header.headers.entrySet()){
            strings.add(entry.getKey() +  ": " + entry.getValue() + "\r\n");
        }
        strings.add("\r\n");
        int count = 0;
        for (String str : strings){
            request.request[count] = str;
            count++;
        }
        for (String s : strings){
            out.write(s.getBytes());
        }
    }

    synchronized void flush() throws IOException {
        if (closed) return;
        out.flush();
    }

    synchronized void finishRequest() throws IOException {
        out.flush();
    }


}
