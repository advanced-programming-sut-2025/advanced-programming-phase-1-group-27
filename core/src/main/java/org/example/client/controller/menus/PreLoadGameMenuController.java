package org.example.client.controller.menus;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.LoadGameMenuView;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.menu.MainMenuView;
import org.example.client.view.menu.PreLoadGameMenuView;
import org.example.common.models.Message;
import org.example.server.models.Lobby;
import org.example.server.models.Result;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class PreLoadGameMenuController extends MenuController{
    private PreLoadGameMenuView view;

    public PreLoadGameMenuController(PreLoadGameMenuView view) {
        this.view = view;
    }

    public ArrayList<Lobby> getLobbiesToJoin() {
        Message message = new Message(new HashMap<>()
                , Message.Type.get_lobbies_list);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new ArrayList<>();
        }
        ArrayList<LinkedTreeMap<String, Object>> lobbiesInfo = response.getFromBody("lobbiesInfo");
        ArrayList<Lobby> result = new ArrayList<>();
        for (LinkedTreeMap<String, Object> lobbyInfo : lobbiesInfo) {
            Lobby lobby = new Lobby(lobbyInfo);
            if (lobby.hasUser(ClientApp.getLoggedInUser().getUsername())) {
                result.add(lobby);
            }
        }
        return result;
    }

    public void loadGame(int lobbyId) {
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new LoadGameMenuView(lobbyId));
        Main.getMain().setScreen(ClientApp.getCurrentMenu());

        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "join");
            put("lobbyId", lobbyId);
        }}, Message.Type.load_game));

    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new MainMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
        return null;
    }
}
