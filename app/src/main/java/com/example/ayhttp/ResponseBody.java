package com.example.ayhttp;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class ResponseBody implements Closeable {

    private final InputStream in;
    private final String contentTypeString;
    private final long contentLength;

    ResponseBody(InputStream in, String contentTypeString, long contentLength){
        this.in = in;
        this.contentLength = contentLength;
        this.contentTypeString = contentTypeString;
    }


    String contentTypeString(){
        return contentTypeString;
    }

    long contentLength(){
        return contentLength;
    }

    InputStream in(){
        return in;
    }

    @Override
    public void close() throws IOException {

    }
}
