package org.example.client.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.GameAssetManager;
import org.example.common.models.Message;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Player;
import org.example.server.models.Position;
import org.example.server.models.User;
import org.example.server.models.enums.AbilityType;

import java.awt.*;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class MiniPlayer extends User {

    private Position position;
    private int mapIndex;
    private int numberOfQuestsCompleted;
    private int money;
    private int totalAbility;

    private final Label usernameLabel;
    private final Label numberOfQuestsLabel;
    private final Label earningsLabel;
    private final Label skillLabel;

    public MiniPlayer(User user, int mapIndex) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
        this.mapIndex = mapIndex;
        position = new Position(8, 70);
        usernameLabel = new Label(user.getUsername(),GameAssetManager.getGameAssetManager().getSkin());
        numberOfQuestsLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        earningsLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        skillLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        usernameLabel.setColor(Color.BLACK);
        numberOfQuestsLabel.setColor(Color.BLACK);
        earningsLabel.setColor(Color.BLACK);
        skillLabel.setColor(Color.BLACK);
    }

    public void updateMiniPlayer() {
        if (ClientApp.getLoggedInUser().getUsername().equals(getUsername())) {
            Player player = ClientApp.getCurrentGame().getCurrentPlayer();
            position = player.getCurrentCell().getPosition();
            mapIndex = player.getCurrentMap() instanceof NPCMap? 4 : ClientApp.getCurrentGame().getPlayerMapIndex(getUsername());
            numberOfQuestsCompleted = 0; // TODO : Rassa, dorostesh kon
            money = player.getMoney();
            totalAbility = player.getAbility(AbilityType.Farming).getLevel() +
                           player.getAbility(AbilityType.Mining).getLevel() +
                           player.getAbility(AbilityType.Foraging).getLevel() +
                           player.getAbility(AbilityType.Fishing).getLevel();
            numberOfQuestsLabel.setText(numberOfQuestsCompleted);
            earningsLabel.setText(money);
            skillLabel.setText(totalAbility);
        }
        else{
            ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                put("mode", "ask");
                put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                put("starter", ClientApp.getLoggedInUser().getUsername());
                put("other", getUsername());
                put("self", ClientApp.getLoggedInUser().getUsername());
            }}, Message.Type.update_mini_player));
        }
    }

    public void update(Message message) {
        position = new Position(message.<LinkedTreeMap<String, Object>>getFromBody("position"));
        mapIndex = message.getIntFromBody("mapIndex");
        numberOfQuestsCompleted = message.getIntFromBody("numberOfQuestsCompleted");
        money = message.getIntFromBody("money");
        totalAbility = message.getIntFromBody("totalAbility");

        numberOfQuestsLabel.setText(numberOfQuestsCompleted);
        earningsLabel.setText(money);
        skillLabel.setText(totalAbility);
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

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Label getNumberOfQuestsLabel() {
        return numberOfQuestsLabel;
    }

    public Label getEarningsLabel() {
        return earningsLabel;
    }

    public Label getSkillLabel() {
        return skillLabel;
    }
}
