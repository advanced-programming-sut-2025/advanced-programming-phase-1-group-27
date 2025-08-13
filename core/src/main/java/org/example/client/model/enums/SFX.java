package org.example.client.model.enums;

import com.badlogic.gdx.audio.Sound;
import org.example.client.model.GameAssetManager;

public enum SFX {
    THOR(GameAssetManager.getGameAssetManager().getThorSound());

    private final Sound sound;

    SFX(Sound sound) {
        this.sound = sound;
    }

    public void play() {
        sound.play(0.2f);
    }
}
