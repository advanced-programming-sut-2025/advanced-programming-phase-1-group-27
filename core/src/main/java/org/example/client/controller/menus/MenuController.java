package org.example.client.controller.menus;

import com.badlogic.gdx.audio.Sound;
import org.example.client.model.GameAssetManager;
import org.example.common.models.Result;

public abstract class MenuController {

    protected final Sound clickSound = GameAssetManager.getGameAssetManager().getClickSound();

    public abstract Result enterMenu(String menuName);

    public abstract Result exitMenu();

    protected void playClickSound() {

        if (true) {                    ///  TODO: age sfx on bood play beshe
            clickSound.play(1.0f);
        }

    }

}
