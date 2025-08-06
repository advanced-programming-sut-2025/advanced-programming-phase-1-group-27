package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import org.example.client.model.ClientApp;
import org.example.common.models.Message;
import org.example.common.models.MusicInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class RadioMenuController {
    public void uploadSong(File file) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(null, Message.Type.upload_song_request));
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] chunk = new byte[bytesRead];
                System.arraycopy(buffer, 0, chunk, 0, bytesRead);
                ClientApp.getServerConnectionThread().sendBinaryPacket(chunk);
            }

            Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                    new Message(null, Message.Type.upload_complete),
                    TIMEOUT_MILLIS
            );
            String songId = response.getFromBody("songId");
            ClientApp.getCurrentGame().addSong(songId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSong(MusicInfo musicInfo) {
        String songId = musicInfo.getSongId();
        float offset = (System.currentTimeMillis() - musicInfo.getStartTime()) / 1000f;
        ClientApp.getServerConnectionThread().setOnDownloadComplete(fileHandle -> {
            Music music = Gdx.audio.newMusic(fileHandle);
            ClientApp.getCurrentGame().setCurrentMusic(music);
            music.setPosition(offset);
        });
        ClientApp.getServerConnectionThread().sendMessage(
                new Message(new HashMap<>() {{
                    put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                    put("username", ClientApp.getLoggedInUser().getUsername());
                    put("offset", offset);
                    put("songId", songId);
                }}, Message.Type.play_song_request)
        );
    }

    public MusicInfo getPlayerMusicInfo(String playerName) {
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                new Message(new HashMap<>() {{
                    put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                    put("playerName", playerName);
                }}, Message.Type.get_player_music),
                TIMEOUT_MILLIS
        );
        String songId = response.getFromBody("songId");
        if (songId == null)
            return null;
        long startTime = ((Number) response.getFromBody("startTime")).longValue();
        return new MusicInfo(songId, startTime);
    }
}
