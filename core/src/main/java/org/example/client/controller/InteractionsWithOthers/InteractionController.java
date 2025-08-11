package org.example.client.controller.InteractionsWithOthers;

import org.example.client.Main;
import org.example.client.view.InteractionMenus.Gift.PreGiftMenuView;
import org.example.client.view.InteractionMenus.Trade.PreTradeMenuView;

public class InteractionController {

    public boolean checkIfPlayersAreClose(){

        ///  TODO PARSA
        return false;

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
