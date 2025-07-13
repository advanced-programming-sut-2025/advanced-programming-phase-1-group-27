package org.example.client.model;

import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;

import java.io.IOException;
import java.net.Socket;

public class ServerConnectionThread extends ConnectionThread {

    protected ServerConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        // TODO
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        return true;
    }
}
