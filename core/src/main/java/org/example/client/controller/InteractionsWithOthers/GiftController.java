package org.example.client.controller.InteractionsWithOthers;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.InteractionMenus.GiftPlayerMenuView;
import org.example.client.view.OutsideView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.Item;
import org.example.server.models.Player;
import org.example.server.models.Relations.Relation;

import java.util.HashMap;

public class GiftController {

    public GraphicalResult openGiftMenu(String targetUsername) {
        Relation relation = InteractionsWithUserController.getRelation(targetUsername);
        if (relation.getLevel() == 0) {
            return new GraphicalResult("Relation level is 0");
        }
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GiftPlayerMenuView(ClientApp.getLoggedInUser().getUsername(),
                targetUsername));
        return null;
    }

    public void gift(String username, String stringItem, int amount) {
        Item item = ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItemWithName(stringItem);
        Player player =  ClientApp.getCurrentGame().getCurrentPlayer();
        player.getBackpack().reduceItems(item, amount);
        // TODO : Rassa in bayad bere be dast other berese
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("self", ClientApp.getLoggedInUser().getUsername());
            put("other", username);
            put("item", stringItem);
            put("amount", amount);
            put("level" , ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getStackLevel(item));
        }} , Message.Type.gift_to_player));
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
    }
}
