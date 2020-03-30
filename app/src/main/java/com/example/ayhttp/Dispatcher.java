package com.example.ayhttp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Dispatcher {
    private int maxRequests = 64;
    private final Deque<AsyncCall> readyAsyncCalls = new ArrayDeque<>();
    private final Deque<AsyncCall> runningAsyncCalls = new ArrayDeque<>();
    private final Deque<SyncCall> runningSyncCalls = new ArrayDeque<>();
    private ExecutorService executorService;


    synchronized ExecutorService executorService(){
        if (executorService == null){
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), Util.threadFactory("Dispatcher", false));
        }
        return executorService;
    }

    synchronized void enqueue(AsyncCall call){
        if (runningAsyncCalls.size() < maxRequests){
            runningAsyncCalls.add(call);
            executorService.execute(call);
        }else {
            readyAsyncCalls.add(call);
        }
    }

    synchronized void executed(SyncCall call) {
        runningSyncCalls.add(call);
    }
}
