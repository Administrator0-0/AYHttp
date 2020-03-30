package com.example.ayhttp;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Util {


    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

    public static int getDuration(String name, long duration, TimeUnit unit){
        if (duration < 0) throw new IllegalArgumentException(name + "< 0");
        if (unit == null) throw new NullPointerException("TimeUnit is null");
        long mills = unit.toMillis(duration);
        if (mills > Integer.MAX_VALUE) throw new IllegalArgumentException(name + "is too large");
        if (mills == 0 && duration > 0) throw new IllegalArgumentException(name + "is too small");
        return (int)mills;
    }
}
