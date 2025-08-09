package org.example.client.controller.InteractionsWithOthers;

import org.example.client.Main;
import org.example.client.view.InteractionMenus.PreGiftMenuView;
import org.example.client.view.InteractionMenus.PreTradeMenuView;

public class InteractionController {

    public void openTradeMenu(String targetUser){

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreTradeMenuView(targetUser));

    }

    public void openGiftMenu(String targetUser){

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGiftMenuView(targetUser));

    }

}
