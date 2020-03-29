package com.example.ayhttp;

public class Request {
    RequestHeader header;
    String request;
    String url;


    public Request url(String url){
        this.url = url;
        return this;
    }

    public Request header(String key, String value){
        if (header == null) header = new RequestHeader();
        header.addHeader(key, value);
        return this;
    }

    public class Builder{



        public Request build(){
            return null;
        }
    }
}
