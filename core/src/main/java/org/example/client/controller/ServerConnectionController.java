package org.example.client.controller;

import org.example.client.model.ClientApp;
import org.example.common.models.Message;
import org.example.server.models.ServerApp;

import java.util.HashMap;

public class ServerConnectionController {
    public Message getAddress() {
        return new Message(new HashMap<>() {{
            put("ip", ClientApp.getIp());
            put("port", ClientApp.getPort());
        }}, Message.Type.response);
    }
}
