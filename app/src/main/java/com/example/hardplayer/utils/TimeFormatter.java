package com.example.hardplayer.utils;

import android.annotation.SuppressLint;

public class TimeFormatter {
    @SuppressLint("DefaultLocale")
    public static String formatMilliseconds(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
