package com.example.hardplayer;

import com.example.hardplayer.models.Track;

import java.util.ArrayList;
import java.util.Optional;

public class TrackQueueManager {
    private static int currentIndexPosition = 0;
    private static ArrayList<Track> tracksQueue = new ArrayList<>();

    public static void setCurrentIndexPosition(int currentIndexPosition) {
        TrackQueueManager.currentIndexPosition = currentIndexPosition;
    }

    public static int getCurrentIndexPosition() {
        return currentIndexPosition;
    }

    public static void addToQueue(Track track) {
        tracksQueue.add(track);
    }

    public static void setTracksQueue(ArrayList<Track> tracksQueue, int startPosition) {
        TrackQueueManager.tracksQueue = tracksQueue;
        TrackQueueManager.currentIndexPosition = startPosition;
    }

    public static void setTracksQueue(ArrayList<Track> tracksQueue) {
        TrackQueueManager.tracksQueue = tracksQueue;
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

    public static Track getAndSwitchToNextTrack() {
        currentIndexPosition++;
        if (currentIndexPosition >= 0 && currentIndexPosition < tracksQueue.size())
            return tracksQueue.get(currentIndexPosition);

        return null;
    }

    public static Track getAndSwitchToPreviousTrack() {
        currentIndexPosition--;
        if (currentIndexPosition >= 0 && currentIndexPosition < tracksQueue.size())
            return tracksQueue.get(currentIndexPosition);

        return null;
    }
}
