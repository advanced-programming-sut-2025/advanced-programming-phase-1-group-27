package org.example.client.controller.InteractionsWithOthers;

import org.example.client.Main;
import org.example.client.view.InteractionMenus.PreTradeMenuView;

public class InteractionController {

    public void openTradeMenu2(String targetUser){

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreTradeMenuView(targetUser));


    }

}
