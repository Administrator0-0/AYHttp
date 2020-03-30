package com.example.ayhttp;

import java.io.IOException;

public interface Interceptor {

    public Response intercept(Chain chain) throws IOException;
}
