package com.example.ayhttp;

public class Response {

    final Request request;
    final Headers headers;
    final ResponseBody body;
    final int code;
    final String message;
    final long sentRequestMillis;
    final long currentTimeMillis;


    private Response(Builder builder){
        this.request = builder.request;
        this.body = builder.body;
        this.code = builder.code;
        this.message = builder.message;
        this.headers = builder.headers;
        this.sentRequestMillis = builder.sentRequestMillis;
        this.currentTimeMillis = builder.currentTimeMillis;
    }

    Request request() {
        return request;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public ResponseBody body() {
        return body;
    }

    public String message(){
        return message;
    }



    static class Builder{

        Request request;
        Headers headers;
        ResponseBody body;
        String message;
        long sentRequestMillis;
        long currentTimeMillis;
        int code = -1;

        Builder(){
            headers = new Headers();
        }

        Builder(Response response){
            this.request = response.request;
            this.headers = response.headers;
            this.body = response.body;
            this.message = response.message;
            this.sentRequestMillis = response.sentRequestMillis;
            this.currentTimeMillis = response.currentTimeMillis;
        }


        Builder request(Request request){
            this.request = request;
            return this;
        }

        Response build(){
            return new Response(this);
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder body(ResponseBody body){
            this.body = body;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder headers(Headers headers){
            this.headers = headers;
            return this;
        }

        public Builder sentRequestAtMillis(long sentRequestMillis) {
            this.sentRequestMillis = sentRequestMillis;
            return this;
        }

        public Builder receivedResponseAtMillis(long currentTimeMillis) {
            this.currentTimeMillis = currentTimeMillis;
            return this;
        }
    }
}
