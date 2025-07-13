package org.example.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.client.view.AppView;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {


    private static SpriteBatch batch;
    private static Main main;

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();

        //  TODO: Yeki az ina comment bayad beshe. Terminal baraye test phase 1

//        (new AppView()).runViaTerminal();
        (new AppView()).runViaGraphics();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
