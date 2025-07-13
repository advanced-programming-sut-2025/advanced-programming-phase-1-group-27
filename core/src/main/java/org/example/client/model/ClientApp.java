package org.example.client.model;

import java.io.IOException;
import java.net.Socket;

public class ClientApp {
    private static String ip;
    private static int port;
    private static ServerConnectionThread serverConnectionThread;

    public static void initFromArgs(String[] args) throws IOException {
        ip = args[0].split(":")[0];
        port = Integer.parseInt(args[0].split(":")[1]);

        String serverIp = args[1].split(":")[0];
        int serverPort = Integer.parseInt(args[1].split(":")[1]);

        Socket socket = new Socket(serverIp, serverPort);
        serverConnectionThread = new ServerConnectionThread(socket);
    }

    public static void connectToServer() {
        if (serverConnectionThread == null || serverConnectionThread.isAlive())
            return;
        serverConnectionThread.start();
    }
}
