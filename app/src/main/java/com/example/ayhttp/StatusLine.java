package com.example.ayhttp;

import java.io.IOException;
import java.net.ProtocolException;

public class StatusLine {

    public final int code;
    public final String message;

    public StatusLine(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static StatusLine get(Response response){
        return new StatusLine(response.code, response.message);
    }

    public static StatusLine parse(String statusLine) throws IOException {
        int codeStart = 0;
        if (statusLine.startsWith("HTTP/1.")) {
            if (statusLine.length() < 9 || statusLine.charAt(8) != ' ') {
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
            int httpMinorVersion = statusLine.charAt(7) - '0';
            codeStart = 9;
            if (httpMinorVersion == 0) {
                //TODO HTTP_1_0
            } else if (httpMinorVersion == 1) {
                //TODO HTTP_1_1
            } else {
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
        }
        if (statusLine.length() < codeStart + 3) {
            throw new ProtocolException("Unexpected status line: " + statusLine);
        }
        int code;
        code = Integer.parseInt(statusLine.substring(codeStart, codeStart + 3));
        String message = "";
        if (statusLine.length() > codeStart + 3){
            if (statusLine.charAt(codeStart + 3) != ' '){
                throw new ProtocolException("Unexpected status line: " + statusLine);
            }
            message = statusLine.substring(codeStart + 4);
        }
        return new StatusLine(code, message);
    }
}
