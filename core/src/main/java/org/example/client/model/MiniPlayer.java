package org.example.client.model;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Message;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Player;
import org.example.server.models.Position;
import org.example.server.models.User;
import org.example.server.models.enums.AbilityType;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class MiniPlayer extends User {
    private Position position;
    private int mapIndex;
    private int numberOfQuestsCompleted;
    private int money;
    private int totalAbility;

    public MiniPlayer(User user, int mapIndex) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
        this.mapIndex = mapIndex;
        position = new Position(8, 70);
    }

    public void updateMiniPlayer() {
        if (ClientApp.getLoggedInUser().getUsername().equals(getUsername())) {
            Player player = ClientApp.getCurrentGame().getCurrentPlayer();
            position = player.getCurrentCell().getPosition();
            mapIndex = player.getCurrentMap() instanceof NPCMap? 4 : ClientApp.getCurrentGame().getPlayerMapIndex(getUsername());
            numberOfQuestsCompleted = 0; // TODO : Rassa, dorostesh kon
            totalAbility = player.getAbility(AbilityType.Farming).getLevel() +
                           player.getAbility(AbilityType.Mining).getLevel() +
                           player.getAbility(AbilityType.Foraging).getLevel() +
                           player.getAbility(AbilityType.Fishing).getLevel();
            return;
        }
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("username", getUsername());
        }}, Message.Type.update_mini_player), TIMEOUT_MILLIS);
        position = new Position(response.<LinkedTreeMap<String, Object>>getFromBody("position"));
        mapIndex = response.getIntFromBody("mapIndex");
        numberOfQuestsCompleted = response.getIntFromBody("numberOfQuestsCompleted");
        money = response.getIntFromBody("money");
        totalAbility = response.getIntFromBody("totalAbility");
    }

    public float getXRatio(){

        return ( float )( position.getY() /
                ( (mapIndex == 4) ? 34f : 75f ) );

    }

    public float getYRatio(){

        return 1 - ( float )( position.getX() /
                ( (mapIndex == 4) ? 16f : 55f ) );

    }

    public Position getPosition() {
        return position;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public int getNumberOfQuestsCompleted() {
        return numberOfQuestsCompleted;
    }

    public int getMoney() {
        return money;
    }

    public int getTotalAbility() {
        return totalAbility;
    }
}
