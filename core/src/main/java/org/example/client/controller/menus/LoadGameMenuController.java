package org.example.client.controller.menus;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.LoadGameMenuView;
import org.example.client.view.menu.PreLoadGameMenuView;
import org.example.common.models.Message;
import org.example.common.models.Result;

import java.util.HashMap;

public class LoadGameMenuController extends MenuController {
    private LoadGameMenuView view;

    public LoadGameMenuController(LoadGameMenuView view) {
        this.view = view;
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "leave");
            put("lobbyId", view.getLobbyId());
        }}, Message.Type.load_game));

        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new PreLoadGameMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
        return null;
    }
}
