package org.example.server.models.enums;

import com.badlogic.gdx.audio.Sound;
import org.example.common.models.GameAssetManager;

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
