package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.models.GameAssetManager;

import java.util.Scanner;

public abstract class AppMenu implements Screen {


    protected final Skin skin = GameAssetManager.getGameAssetManager().getSkin();
    private final Texture backgroundTexture = GameAssetManager.getGameAssetManager().getMenuBackground();
    protected final Image menuBackground = new Image(backgroundTexture);
    protected final Image stardewValleyText = GameAssetManager.getGameAssetManager().getStardewValleyText();
    protected final Sound clickSound = GameAssetManager.getGameAssetManager().getClickSound();



    public AppMenu() {

        menuBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }


    public abstract void executeCommands(Scanner scanner);

    public void printString(String string) {
        System.out.println(string);
    }

    public Skin getSkin() {
        return skin;
    }

    protected void playClickSound(){

        if ( true ){                    ///  TODO: age sfx on bood play beshe
            clickSound.play();
        }

    }

}
