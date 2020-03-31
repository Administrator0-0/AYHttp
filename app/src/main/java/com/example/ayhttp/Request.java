package com.example.ayhttp;

public class Request {

    final Headers headers;
    final String protocol;
    final String url;
    final String type;
    final int port;

    private Request(Builder builder){
        this.headers = builder.headers;
        this.protocol = builder.protocol;
        this.url = builder.url;
        this.type = builder.type;
        this.port = builder.port;
    }

    String header(String key) {
        return headers.headers.get(key);
    }

    Builder newBuilder() {
        return new Builder(this);
    }


    public static class Builder{

        private Headers headers;
        private String url;
        private int port = 80;
        private String protocol = "HTTP_1_1";
        private String type = "GET";


        public Builder(){
            headers = new Headers();
        }

        Builder(Request request){
            this.headers = request.headers;
            this.protocol = request.protocol;
            this.url = request.url;
            this.type = request.type;
            this.port = request.port;
        }

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

        public Builder port(int port){
            this.port = port;
            return this;
        }

        public Builder header(String key, String value){
            headers.addHeader(key, value);
            return this;
        }

        public Request build(){
            return new Request(this);
        }
    }
}
