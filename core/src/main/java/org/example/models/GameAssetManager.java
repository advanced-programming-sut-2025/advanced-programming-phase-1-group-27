package org.example.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {


    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private final Texture background = new Texture(Gdx.files.internal("Images/menu_background.png"));
    private final Texture stardewVallleyText = new Texture(Gdx.files.internal("Images/stardew_valley_text.png"));

    public static GameAssetManager getGameAssetManager() {

        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;

    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getMenuBackground() {
        return background;
    }

    public Image getStardewVallleyText() {
        return new Image(stardewVallleyText);
    }



}
