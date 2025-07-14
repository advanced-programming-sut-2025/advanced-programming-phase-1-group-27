package org.example.client.model;

import org.example.client.controller.ServerConnectionController;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ServerConnectionThread extends ConnectionThread {

    protected ServerConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        try {
            socket.setSoTimeout(TIMEOUT_MILLIS);

            dataInputStream.readUTF();
            sendMessage(ServerConnectionController.getAddress());

            socket.setSoTimeout(0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        return false;
    }
}
