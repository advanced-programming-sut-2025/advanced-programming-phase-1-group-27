package org.example.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.models.GameAssetManager;

import java.util.Scanner;

public abstract class AppMenu implements Screen {


    protected final Skin skin = GameAssetManager.getGameAssetManager().getSkin();

    public abstract void executeCommands(Scanner scanner);

    public void printString(String string) {
        System.out.println(string);
    }

}
