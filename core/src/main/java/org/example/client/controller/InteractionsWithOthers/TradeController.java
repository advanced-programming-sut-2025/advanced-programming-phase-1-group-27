package org.example.client.controller.InteractionsWithOthers;

import org.example.client.model.ClientApp;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class TradeController {
    public GraphicalResult startTrade(String username){
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message message = new Message(new HashMap<>(){{
            put("mode" , "start");
            put("lobbyId" ,  lobbyId);
            put("sender" , ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("receiver" , username);
        }} , Message.Type.InteractionP2P);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult("Failed to start trade!");
        }
        String answer = response.getFromBody("answer");
        if(answer.equals("yes")){
            //TODO : Go to TradeView
            return null;
        }else{
            return new GraphicalResult(username + "has declined your trade request!");
        }
    }


}
