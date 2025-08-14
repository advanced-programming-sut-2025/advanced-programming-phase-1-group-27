package org.example.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.client.model.ClientApp;
import org.example.client.view.AppView;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {


    private static SpriteBatch batch;
    private static Main main;

    public Main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java peer.PeerMain <self-address:port> <tracker-address:ip>");
            System.exit(0);
        }
        try {
            ClientApp.initFromArgs(args);
            ClientApp.connectToServer();
        } catch (Exception e) {
            System.err.println("Error initializing peer: " + e.getMessage());
        }

    }

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
