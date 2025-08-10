package org.example.client.controller.InteractionsWithOthers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.App;
import org.example.server.models.Player;
import org.example.server.models.Relations.Dialogue;
import org.example.server.models.Relations.Relation;
import org.example.server.models.Result;
import org.example.server.models.enums.DialogueType;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.tools.Backpack;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class MarriageController {
    public GraphicalResult askMarriage(String username) {
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
        Backpack backpack = currentPlayer.getBackpack();
        if (currentPlayer.getGender() == Gender.Female) {
            return new GraphicalResult("You are not male!!",
                    GameAssetManager.getGameAssetManager().getErrorColor());
        }
        if (!backpack.hasEnoughItem(ShopItems.WeddingRing, 2)) {
            return new GraphicalResult("You don't have 2 wedding rings!",
                    GameAssetManager.getGameAssetManager().getErrorColor());
        }
        Message canMarriedMessage = new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("self", currentPlayer.getUsername());
            put("other", username);
        }}, Message.Type.can_marry);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(canMarriedMessage, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to check",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        GraphicalResult result = new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
        if (result.hasError()) {
            return new GraphicalResult(
                    result.getMessage().toString(),
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("self", currentPlayer.getUsername());
            put("other", username);
        }}, Message.Type.marriage_request));

        return null;
    }

    public Result showCouple() {
        if (App.getCurrentGame().getCurrentPlayer().getSpouse() == null) {
            return new Result(false, "You are not married");
        } else {
            return new Result(true, "You are married with " +
                    App.getCurrentGame().getCurrentPlayer().getSpouse().getUsername());
        }
    }

    public void accept(String proposer) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("proposer", proposer);
            put("answer", true);
        }}, Message.Type.marriage_response));
        ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().addItems(ShopItems.WeddingRing, StackLevel.Basic, 1);
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
    }

    public void decline(String proposer) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("proposer", proposer);
            put("answer", false);
        }}, Message.Type.marriage_response));
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
    }

}
