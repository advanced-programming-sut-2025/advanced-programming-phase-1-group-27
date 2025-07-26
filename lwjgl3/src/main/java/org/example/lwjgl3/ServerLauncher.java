package org.example.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.example.server.Main;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class ServerLauncher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        new Lwjgl3Application(new Main(args), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();

        configuration.setWindowedMode(1, 1);  // Smallest possible window
        configuration.setDecorated(false);    // No title bar
        configuration.setInitialVisible(false);

        return configuration;
    }
}