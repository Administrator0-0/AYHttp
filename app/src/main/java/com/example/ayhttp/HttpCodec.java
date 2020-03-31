package com.example.ayhttp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

class HttpCodec {
    private static final int STATE_IDLE = 0; // Idle connections are ready to write request headers.
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_CLOSED = 6;

    private InputStream in;
    private OutputStream out;
    private boolean closed;
    int state = STATE_IDLE;
    private int headerLimit = 256 * 1024;

    HttpCodec(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }

    void writeRequest(Request request) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(request.headers.type + " / " + "HTTP/1.1\r\n");
        for (HashMap.Entry entry : request.headers.headers.entrySet()){
            strings.add(entry.getKey() +  ": " + entry.getValue() + "\r\n");
        }
        strings.add("Host: " + request.headers.url + "\r\n");
        strings.add("\r\n");
        int count = 0;
        for (String str : strings){
            request.request[count] = str;
            count++;
        }
        for (String s : strings){
            out.write(s.getBytes());
        }
        state = STATE_OPEN_REQUEST_BODY;
    }

    Response.Builder readResponseHeaders(boolean expectContinue){
        if (state != STATE_OPEN_REQUEST_BODY && state != STATE_READ_RESPONSE_HEADERS) {
            throw new IllegalStateException("state: " + state);
        }
        try {
            StatusLine statusLine = StatusLine.parse(readHeaderLine());
            Response.Builder builder = new Response.Builder()
                    .code(statusLine.code)
                    .message(statusLine.message);
            if (expectContinue && statusLine.code == 100) {
                return null;
            }else if (statusLine.code == 100){
                state = STATE_READ_RESPONSE_HEADERS;
                return builder;
            }
            state = STATE_OPEN_RESPONSE_BODY;
            return builder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    ResponseBody openResponseBody(Response response){
//        String contentType = response.
//    }

//    private Headers readHeaders(){
//        Headers headers = new Headers();
//    }

    private String readHeaderLine() throws IOException {
        String line = Util.readUtf8Line(in, headerLimit);
        headerLimit -= line.length();
        return line;
    }

    synchronized void flush() throws IOException {
        if (closed) return;
        out.flush();
    }

    synchronized void finishRequest() throws IOException {
        out.flush();
    }


}
