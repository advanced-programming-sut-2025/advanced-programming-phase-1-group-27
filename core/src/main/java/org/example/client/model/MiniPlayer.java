package org.example.client.model;

import org.example.server.models.Position;
import org.example.server.models.User;

public class MiniPlayer extends User {
    Position position;

    public MiniPlayer(User user) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
        position = new Position(8, 70);
    }
}
