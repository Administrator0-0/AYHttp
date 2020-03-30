package com.example.ayhttp;

class Response {

    Request request;
    ResponseBody body;

    private Response(Builder builder){
        this.request = builder.request;
    }

    Request getRequest() {
        return request;
    }


    class Builder{

        Request request;
        Headers headers;
        int code = -1;


        Builder request(Request request){
            this.request = request;
            return this;
        }

        Response build(){
            return new Response(this);
        }
    }
}
