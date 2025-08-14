package org.example.client.controller.InteractionsWithOthers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.PopUpController;
import org.example.client.model.ClientApp;
import org.example.client.model.GameAssetManager;
import org.example.client.model.MiniPlayer;
import org.example.client.model.PopUpTexture;
import org.example.client.view.OutsideView;
import org.example.common.models.*;
import org.example.common.models.items.ShopItems;
import org.example.common.models.tools.Backpack;

import java.util.HashMap;

import static org.example.client.model.ClientApp.TIMEOUT_MILLIS;

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

        return new GraphicalResult("You have sent your request successfully!");
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

        Sprite ringSprite = new Sprite(GameAssetManager.getGameAssetManager().getWeddingRing());
        Sprite heartSprite = new Sprite(GameAssetManager.getGameAssetManager().getHeart1());

        ringSprite.setSize(72, 72);
        heartSprite.setSize(72, 72);

        float proposerX = 0, proposerY = 0;

        for (MiniPlayer miniPlayer : ClientApp.getCurrentGame().getPlayers()) {

            if (miniPlayer.getUsername().equals(proposer)) {
                proposerX = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getX();
                proposerY = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getY();
            }

        }

        PopUpController.addPopUp(new PopUpTexture(heartSprite
                , newOutsideView.getPlayerController().getX(), newOutsideView.getPlayerController().getY() + 80,
                newOutsideView.getPlayerController().getX(), newOutsideView.getPlayerController().getY() + 20, 4
        ));

        PopUpController.addPopUp(new PopUpTexture(heartSprite
                , proposerX, proposerY + 80,
                proposerX, proposerY + 20, 4
        ));

        PopUpController.addPopUp(new PopUpTexture(ringSprite,
                proposerX, proposerY
                , newOutsideView.getPlayerController().getX(), newOutsideView.getPlayerController().getY(), 4
        ));


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

        Sprite noMarriageSprite = new Sprite(GameAssetManager.getGameAssetManager().getNoMarriage());
        Sprite brokenHeartSprite = new Sprite(GameAssetManager.getGameAssetManager().getBrokenHeart());

        noMarriageSprite.setSize(72, 72);
        brokenHeartSprite.setSize(72, 72);

        float proposerX = 0, proposerY = 0;

        for (MiniPlayer miniPlayer : ClientApp.getCurrentGame().getPlayers()) {

            if (miniPlayer.getUsername().equals(proposer)) {
                proposerX = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getX();
                proposerY = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getY();
            }

        }

        PopUpController.addPopUp(new PopUpTexture(brokenHeartSprite
                , newOutsideView.getPlayerController().getX(), newOutsideView.getPlayerController().getY() + 80,
                newOutsideView.getPlayerController().getX(), newOutsideView.getPlayerController().getY() + 20, 4
        ));

        PopUpController.addPopUp(new PopUpTexture(noMarriageSprite
                , proposerX, proposerY + 80,
                proposerX, proposerY + 20, 4
        ));


    }

}
