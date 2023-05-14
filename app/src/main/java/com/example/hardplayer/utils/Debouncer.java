package com.example.hardplayer.utils;

import android.os.Handler;

public class Debouncer {
    private Handler handler = new Handler();
    private Runnable runnable;
    private boolean isCooldown = false;
    private long delay;

    public Debouncer(Runnable runnable, long delay) {
        this.runnable = runnable;
        this.delay = delay;

        handler.postDelayed(() -> isCooldown = false, delay);
    }

    public void run() {
        if (isCooldown) return;

        isCooldown = true;
        runnable.run();

        handler.postDelayed(() -> isCooldown = false, delay);
    }
}