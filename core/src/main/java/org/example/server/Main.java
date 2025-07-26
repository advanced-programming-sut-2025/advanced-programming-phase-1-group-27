package org.example.server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.common.database.DataBaseHelper;
import org.example.server.models.ServerApp;
import org.example.server.models.connections.ListenerThread;

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

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        while (!ServerApp.hasEnded()) {
            // maybe cli commands
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}