package org.example.controller;

import com.badlogic.gdx.audio.Sound;
import org.example.models.App;
import org.example.models.GameAssetManager;
import org.example.models.Result;

public abstract class MenuController {

    protected final Sound clickSound = GameAssetManager.getGameAssetManager().getClickSound();

    public abstract Result enterMenu(String menuName);

    public abstract Result exitMenu();

    public Result showCurrentMenu() {
        return new Result(true, App.getCurrentMenu().toString());
    }

    protected void playClickSound() {

        if (true) {                    ///  TODO: age sfx on bood play beshe
            clickSound.play(1.0f);
        }

    }

}
