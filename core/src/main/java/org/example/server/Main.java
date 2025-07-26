package org.example.server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.common.database.DataBaseHelper;
import org.example.server.models.ServerApp;
import org.example.server.models.connections.ListenerThread;
import org.example.server.models.enums.items.ToolType;

import java.util.Scanner;

public class Main extends Game {

    private static SpriteBatch batch;
    private static Main main;

    public Main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar YourServer.jar <port>");
            Gdx.app.exit();
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            DataBaseHelper.createDatabase();
            ServerApp.setListenerThread(new ListenerThread(port));
            ServerApp.startListening();

        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
            Gdx.app.exit();
        }
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        ToolType type = ToolType.BambooPole;
//        while (!ServerApp.hasEnded()) {
//            // maybe cli commands
//        }
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