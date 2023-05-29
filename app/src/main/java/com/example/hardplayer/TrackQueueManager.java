package com.example.hardplayer;

import com.example.hardplayer.models.Track;

import java.util.ArrayList;
import java.util.Optional;

public class TrackQueueManager {
    private static ArrayList<Track> tracksQueue = new ArrayList<>();

    public static void addToQueue(Track track) {
        tracksQueue.add(track);
    }

    public static void removeFromQueue(int index) {
        if (index >= 0 && index < tracksQueue.size())
            tracksQueue.remove(index);
    }

    public static void clearQueue() {
        tracksQueue.clear();
    }

    public static boolean isQueueEmpty() {
        return tracksQueue.isEmpty();
    }

    public static Optional<Track> getAndSwitchToNextTrack() {
        if (!tracksQueue.isEmpty())
            return Optional.ofNullable(tracksQueue.remove(0));

        return Optional.ofNullable(null);
    }
}
