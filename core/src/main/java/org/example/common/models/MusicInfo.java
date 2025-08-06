package org.example.common.models;

public class MusicInfo {
    String songId;
    long startTime;

    public MusicInfo(String songId, long startTime) {
        this.songId = songId;
        this.startTime = startTime;
    }

    public String getSongId() {
        return songId;
    }

    public long getStartTime() {
        return startTime;
    }
}
