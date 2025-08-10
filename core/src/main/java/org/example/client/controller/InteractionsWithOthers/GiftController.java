package org.example.client.controller.InteractionsWithOthers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.InteractionMenus.GiftPlayerMenuView;
import org.example.client.view.OutsideView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.Item;
import org.example.server.models.Player;
import org.example.server.models.Relations.Gift;
import org.example.server.models.Relations.Relation;
import org.example.server.models.Relations.Trade;
import org.example.server.models.Result;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

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

    public ArrayList<Gift> getGiftHistory(String username) {
        Message message = new Message(new HashMap<>() {{
            put("lobbyId" ,  ClientApp.getCurrentGame().getLobbyId());
            put("starter", ClientApp.getLoggedInUser().getUsername());
            put("other", username);
        }} , Message.Type.get_gift_history);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message ,TIMEOUT_MILLIS);
        if(response == null || response.getType() != Message.Type.response) {
            System.out.println("Gift history response is null");
            return new ArrayList<>();
        }
        ArrayList<Gift> gifts = new ArrayList<>();
        for(LinkedTreeMap<String ,Object> ti : response.<ArrayList<LinkedTreeMap<String,Object>>>getFromBody("gifts")){
            gifts.add(new Gift(ti));
        }
        return gifts;
    }

    public GraphicalResult rateGift(Gift gift , int rate) {
        int id = gift.getId();
        int giftsRate = gift.getRate();
        if(giftsRate != -1){
            return new GraphicalResult("You can't rate this gift twice!!");
        }
        if(rate < 1 || rate > 5){
            return new GraphicalResult("Rate must be between 0 and 5");
        }
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>(){{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("giftId", id);
            put("rate", rate);
        }} , Message.Type.rate_gift));
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
        return new GraphicalResult("");
    }

    public void exit(){
        Main.getMain().getScreen().dispose();
        OutsideView newOutsideView = new OutsideView();
        ClientApp.setNonMainMenu(newOutsideView);
        Main.getMain().setScreen(newOutsideView);
    }
}
