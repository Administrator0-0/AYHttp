package com.example.ayhttp;

import java.util.HashMap;

class Headers {

    HashMap<String, String> headers = new HashMap<>();

    void addHeader(String key, String value){
        headers.put(key, value);
    }

    Headers addLenient(String line){
        int index = line.indexOf(":", 1);
        if (index != -1){
            return addLenient(line.substring(0, index), line.substring(index));
        }else if (line.startsWith(":")){
            return addLenient("", line.substring(1));
        }else {
            return addLenient("", line);
        }
    }

    Headers addLenient(String key, String value){
        headers.put(key, value);
        return this;
    }



}
