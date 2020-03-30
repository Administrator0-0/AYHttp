package com.example.ayhttp;

class Response {

    Request request;

    private Response(Builder builder){
        this.request = builder.request;
    }

    public Request getRequest() {
        return request;
    }



    public class Builder{

        Request request;

        public Builder request(Request request){
            this.request = request;
            return this;
        }

        public Response build(){
            return new Response(this);
        }
    }
}
