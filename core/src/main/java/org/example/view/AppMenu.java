package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.models.GameAssetManager;

import java.util.Scanner;

public abstract class AppMenu implements Screen {


    protected final Skin skin = GameAssetManager.getGameAssetManager().getSkin();
    private final Texture backgroundTexture = GameAssetManager.getGameAssetManager().getMenuBackground();
    protected final Image menuBackground = new Image(backgroundTexture);


    public AppMenu(){

        menuBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }


    public abstract void executeCommands(Scanner scanner);

    public void printString(String string) {
        System.out.println(string);
    }

}
