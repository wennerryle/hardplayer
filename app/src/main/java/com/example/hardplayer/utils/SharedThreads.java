package com.example.hardplayer.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharedThreads {
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
