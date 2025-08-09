package org.example.client.controller.InteractionsWithOthers;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.InteractionMenus.GiftPlayerMenuView;

public class GiftController {

    public void openGiftMenu(String targetUsername) {

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GiftPlayerMenuView(ClientApp.getLoggedInUser().getUsername(),
                targetUsername));


    }

}
