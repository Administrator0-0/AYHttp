package com.example.ayhttp;

class Response {

    final Request request;
    final Headers headers;
    final ResponseBody body;
    final int code;
    final String message;


    private Response(Builder builder){
        this.request = builder.request;
        this.body = builder.body;
        this.code = builder.code;
        this.message = builder.message;
        this.headers = builder.headers;
    }

    Request request() {
        return request;
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
