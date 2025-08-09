package org.example.common.models;

public class MusicInfo {
    private String songId;
    private String songName;
    private float offset = 0f;

    public MusicInfo(String songId, String songName) {
        this.songId = songId;
        this.songName = songName;
    }

    public MusicInfo(String songId, String songName, float offset) {
        this.songId = songId;
        this.songName = songName;
        this.offset = offset;
    }

    public String getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public float getOffset() {
        return offset;
    }
}
