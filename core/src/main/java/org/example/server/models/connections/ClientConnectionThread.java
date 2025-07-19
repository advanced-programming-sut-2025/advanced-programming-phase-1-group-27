package org.example.server.models.connections;

import org.example.server.controller.*;
import org.example.common.models.ConnectionThread;
import org.example.common.models.Message;
import org.example.server.models.ServerApp;
import org.example.server.models.User;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ClientConnectionThread extends ConnectionThread {
    private User user = null;

    public ClientConnectionThread(Socket socket) throws IOException {
        super(socket);
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
            sendMessage(LoginMenuController.login(message, this));
            return true;
        }
        else if (message.getType() == Message.Type.add_user) {
            RegisterMenuController.addUser(message);
            return true;
        }
        else if (message.getType() == Message.Type.set_online_user) {
            this.setUser(new User(message.getFromBody("userInfo")));
            return true;
        }
        else if (message.getType() == Message.Type.get_user) {
            sendMessage(ServerApp.getUserByUsername(message));
            return true;
        }
        else if (message.getType() == Message.Type.set_password) {
            ForgetPasswordMenuController.setPassword(message);
            return true;
        }
        else if (message.getType() == Message.Type.change_profile) {
            sendMessage(ProfileMenuController.change(message,this));
            return true;
        }
        else if (message.getType() == Message.Type.create_lobby) {
            sendMessage(LobbyController.createLobby(message));
            return true;
        }
        else if (message.getType() == Message.Type.join_lobby) {
            sendMessage(LobbyController.joinLobby(message));
            return true;
        }
        else if (message.getType() == Message.Type.get_lobbies_list) {
            sendMessage(LobbyController.getLobbiesList(message));
            return true;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
