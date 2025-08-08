package org.example.client.model;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Message;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Position;
import org.example.server.models.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class MiniPlayer extends User {
    private Position position;
    private int mapIndex;

    public MiniPlayer(User user, int mapIndex) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
        this.mapIndex = mapIndex;
        position = new Position(8, 70);
    }

    public void updatePosition() {
        if (ClientApp.getLoggedInUser().getUsername().equals(getUsername())) {
            position = ClientApp.getCurrentGame().getCurrentPlayer().getPosition();
            mapIndex = ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap? 4 : ClientApp.getCurrentGame().getPlayerMapIndex(getUsername());
            return;
        }
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("username", getUsername());
        }}, Message.Type.get_player_position), TIMEOUT_MILLIS);
        position = new Position(response.<LinkedTreeMap<String, Object>>getFromBody("position"));
        mapIndex = response.getIntFromBody("mapIndex");
    }

    public Position getPosition() {
        return position;
    }

    public int getMapIndex() {
        return mapIndex;
    }
}
