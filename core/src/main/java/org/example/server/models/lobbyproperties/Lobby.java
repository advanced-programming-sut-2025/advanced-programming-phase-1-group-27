package org.example.server.models.lobbyproperties;

import org.example.server.models.Game;
import org.example.server.models.User;

import java.util.ArrayList;

public class Lobby {
    private User admin;
    private ArrayList<User> users = new ArrayList<>();
    private boolean isPublic;
    private boolean isVisible;
    private String id, name, password;
    private Game game = null;
}
