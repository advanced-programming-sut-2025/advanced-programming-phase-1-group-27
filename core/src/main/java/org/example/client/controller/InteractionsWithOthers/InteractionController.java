package org.example.client.controller.InteractionsWithOthers;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.model.MiniPlayer;
import org.example.client.view.InteractionMenus.Gift.PreGiftMenuView;
import org.example.client.view.InteractionMenus.Trade.PreTradeMenuView;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Player;

import java.util.Vector;

public class InteractionController {

    public boolean checkIfPlayersAreClose(String username){
        ///  TODO PARSA
        if(!(ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap)){
            return false;
        }
        Player currentPLayer = ClientApp.getCurrentGame().getCurrentPlayer();
        MiniPlayer otherPlayer = null;
        for(MiniPlayer miniPlayer : ClientApp.getCurrentGame().getPlayers()){
            if(miniPlayer.getUsername().equals(username)){
                otherPlayer = miniPlayer;
            }
        }
        float deltaX = Math.abs(otherPlayer.getPosition().getX() - currentPLayer.getPosition().getX());
        float deltaY = Math.abs(otherPlayer.getPosition().getY() - currentPLayer.getPosition().getY());
        float distance = deltaX*deltaX + deltaY*deltaY;
        return distance < 5;
    }

    public void openTradeMenu(String targetUser){

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreTradeMenuView(targetUser));

    }

    public void openGiftMenu(String targetUser){

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGiftMenuView(targetUser));

    }

}
