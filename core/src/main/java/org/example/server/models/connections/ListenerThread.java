package org.example.server.models.connections;

import org.example.server.models.ServerApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThread extends Thread {
    private final ServerSocket serverSocket;

    public ListenerThread(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    private void createConnection(Socket socket) {
        if (socket == null)
            return;
        try {
            new ClientConnectionThread(socket).start();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void run() {
        while (!ServerApp.hasEnded()) {
            try {
                Socket socket = serverSocket.accept();
                createConnection(socket);
            } catch (Exception e) {
                break;
            }
        }

        try {
            serverSocket.close();
        } catch (Exception ignored) {
        }
    }
}
