package org.example.server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

            // Start CLI thread
            new Thread(this::startCLI).start();

        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
            Gdx.app.exit();
        }
    }

    /**
     * CLI Command Processor (Runs in a separate thread)
     */
    private void startCLI() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server CLI started. Type 'help' for commands.");

        while (!ServerApp.hasEnded()) {
            // cli commands
        }
        scanner.close();
    }

    // ... (Rest of your existing methods: getMain(), getBatch(), etc.)

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        Gdx.app.exit();
        // No need to call Gdx.app.exit() here since we handle it via CLI
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}