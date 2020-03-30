package com.example.ayhttp;

import java.net.Socket;

public class Request {

    final Headers header;
    private Socket socket;
    String[] request;

    private Request(Headers header){
        this.header = header;
    }


    public class Builder{

        private Headers header;
        private String url;
        private String type = "GET";

        public Builder get(){
            type = "GET";
            return this;
        }

        public Builder post(){
            type = "POST";
            return this;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder header(String key, String value){
            if (header == null) header = new Headers(type, url);
            header.addHeader(key, value);
            return this;
        }

        public Request build(){
            if (header == null) header = new Headers(type, url);
            return new Request(header);
        }
    }
}
