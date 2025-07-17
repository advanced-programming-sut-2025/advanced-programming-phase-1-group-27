package org.example.server.models.connections;

import org.example.server.controller.LoginMenuController;
import org.example.server.controller.RegisterMenuController;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;
import org.example.server.models.ServerApp;
import org.example.server.models.User;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ClientConnectionThread extends ConnectionThread {
    // TODO: nemidoonam
    private User user;

    public ClientConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean initialHandshake() {
        try {
            getClientAddress();
        } catch (Exception e) {
            return false;
        }
        ServerApp.addConnection(this);
        return true;
    }

    private void getClientAddress() {
        Message message = new Message(new HashMap<>() {{
            put("command", "get_client_address");
        }}, Message.Type.command);
        Message response = sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response)
            return;
        setOtherSideIP(response.getFromBody("ip"));
        setOtherSidePort(response.<Number>getFromBody("port").intValue());
    }

    @Override
    protected boolean handleMessage(Message message) {
        if (message.getType() == Message.Type.register_request) {
            sendMessage(RegisterMenuController.register(message));
            return true;
        }
        else if(message.getType() == Message.Type.login_request) {
            sendMessage(LoginMenuController.login(message , this));
            return true;
        }
        else if (message.getType() == Message.Type.add_user) {
            RegisterMenuController.addUser(message);
        }
        return false;
    }

    @Override
    public void run() {
        super.run();
        ServerApp.removeConnection(this);
        // TODO: hazf kon bebin chi mishe
        this.end();
    }
}
