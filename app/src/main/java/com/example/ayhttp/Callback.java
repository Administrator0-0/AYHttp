package com.example.ayhttp;

import java.io.IOException;

public interface Callback {
    void onResponse(Call call, Response response) throws IOException;
    void onFailure(Call call, IOException e);
}
