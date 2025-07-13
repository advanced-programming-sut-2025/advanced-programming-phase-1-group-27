package org.example.server.models.connections;

import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;
import org.example.server.models.ServerApp;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionThread extends ConnectionThread {
    // TODO: nemidoonam

    public ClientConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        ServerApp.addConnection(this);
        // TODO: maybeee
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        // TODO: true if message was handled
        return true;
    }

    @Override
    public void run() {
        super.run();
        ServerApp.removeConnection(this);
        // TODO: hazf kon bebin chi mishe
        this.end();
    }
}
