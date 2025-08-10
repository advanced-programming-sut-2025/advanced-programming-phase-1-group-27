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
import org.example.server.models.Stacks;

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

    public void gift(String username, Stacks slot, int amount) {
        Player player =  ClientApp.getCurrentGame().getCurrentPlayer();
        player.getBackpack().reduceItems(slot.getItem(), amount);
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "gift");
            put("starter", ClientApp.getLoggedInUser().getUsername());
            put("other", username);
            put("self", ClientApp.getLoggedInUser().getUsername());
            put("gift", new Stacks(slot.getItem(), slot.getStackLevel(), amount).getInfo());
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
        }} , Message.Type.interaction_p2p));
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
    }
}
