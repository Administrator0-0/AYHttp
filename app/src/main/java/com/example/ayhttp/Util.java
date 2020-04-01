package com.example.ayhttp;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class Util {


    private static LinkedList<byte[]>bufferPool = new LinkedList<>();

    static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

    static int getDuration(String name, long duration, TimeUnit unit){
        if (duration < 0) throw new IllegalArgumentException(name + "< 0");
        if (unit == null) throw new NullPointerException("TimeUnit is null");
        long mills = unit.toMillis(duration);
        if (mills > Integer.MAX_VALUE) throw new IllegalArgumentException(name + "is too large");
        if (mills == 0 && duration > 0) throw new IllegalArgumentException(name + "is too small");
        return (int)mills;
    }

    static long stringToLong(String s) {
        if (s == null) return -1;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static String readUtf8Line(InputStream in, int limit) throws IOException {
        byte[]b = new byte[1024];
        StringBuilder builder = new StringBuilder();
        int len;
        int count = 0;
        while (true){
            try {
                if (count <= limit - b.length) {
                    len = in.read(b);
                } else if (count <= limit){
                    len = in.read(b, 0, limit - count);
                } else {
                    break;
                }
                bufferPool.add(b);
                builder.append(new String(b, 0, len, "UTF-8"));
                count += len;
            }catch (SocketTimeoutException e){
                e.printStackTrace();
                break;
            }
        }
        String[]temp = builder.toString().split("\r\n");
        StringBuilder builder2 = new StringBuilder();
        for (String s : temp){
            builder2.append(s);
        }
        Log.d("aaa", "readUtf8Line: "+builder2.toString().length());
        return builder2.toString();
    }
}
