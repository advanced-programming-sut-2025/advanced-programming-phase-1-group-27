package org.example.client.model;

import org.example.client.controller.ServerConnectionController;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ServerConnectionThread extends ConnectionThread {
    private final ServerConnectionController controller = new ServerConnectionController();

    protected ServerConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        try {
            socket.setSoTimeout(TIMEOUT_MILLIS);

            dataInputStream.readUTF();
            sendMessage(controller.getAddress());

            socket.setSoTimeout(0);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        return true;
    }
}
