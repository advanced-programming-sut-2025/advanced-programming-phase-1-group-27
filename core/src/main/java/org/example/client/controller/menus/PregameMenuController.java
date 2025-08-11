package org.example.client.controller.menus;

import com.badlogic.gdx.Gdx;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.model.ClientGame;
import org.example.client.model.MiniPlayer;
import org.example.client.view.HomeView;
import org.example.client.view.menu.LobbyMenuView;

import org.example.client.view.menu.PregameMenuView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class PregameMenuController extends MenuController {
    private final PregameMenuView view;


    public PregameMenuController(PregameMenuView view) {
        this.view = view;
    }

    public GraphicalResult chooseMap(int mapId) {
        Message message = new Message(new HashMap<>(){{
            put("mapId", mapId);
            put("username" , ClientApp.getLoggedInUser().getUsername());
            put("lobbyId" , view.getLobby().getId());
        }} , Message.Type.choose_map);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if(response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to choose!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        return new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
    }

    public GraphicalResult createGame() {
        if (!ClientApp.getLoggedInUser().getUsername().equals(view.getLobby().getAdmin().getUsername()))
            return new GraphicalResult("Only the host can start the game");
        if (view.getLobby().getUsers().size() < 2)
            return new GraphicalResult("There should be at least two players to start the game");
        for (User user : view.getLobby().getUsers()) {
            if (!view.getLobby().getUsernameToMap().containsKey(user.getUsername()) ||
                    view.getLobby().getUsernameToMap().get(user.getUsername()) == -1)
                return new GraphicalResult("All players should choose their map to start the game!");
        }

        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyInfo", view.getLobby().getInfo());
        }}, Message.Type.create_game));

        return new GraphicalResult(
                "Game created successfully",
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public void startGame(Message message) {
        ArrayList<MiniPlayer> miniPlayers = new ArrayList<>();
        for (User user : view.getLobby().getUsers()) {
            miniPlayers.add(new MiniPlayer(user, view.getLobby().getUsernameToMap().get(user.getUsername())));
        }
        ClientGame clientGame;
        Player currentPlayer = new Player(ClientApp.getLoggedInUser());
        ClientApp.setCurrentGame(clientGame = new ClientGame(
                view.getLobby(),
                currentPlayer,
                miniPlayers
        ));
        clientGame.init(
                message.getFromBody("farmInfo"),
                view.getLobby().getUsernameToMap().get(currentPlayer.getUsername())
        );
        System.out.println("FARM ID: " + view.getLobby().getUsernameToMap().get(currentPlayer.getUsername()));

        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            ClientApp.setCurrentMenu(new HomeView());
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        });
    }

    public Lobby getLobby(int id){
        Message message = new Message(new HashMap<>(){{
            put("id", id);
        }} , Message.Type.find_lobby);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if(response == null || response.getType() != Message.Type.response) {
            return null;
        }
        boolean found = response.getFromBody("found?");
        if(!found){
            return new Lobby();
        }
        return new Lobby(response.getFromBody("lobbyInfo"));
    }

    public void leave() {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("username" , ClientApp.getLoggedInUser().getUsername());
            put("lobbyId" , view.getLobby().getId());
        }} , Message.Type.leave_lobby));
        ClientApp.setCurrentMenu(new LobbyMenuView());
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    public void goToLobbyMenu() {
        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            ClientApp.setCurrentMenu(new LobbyMenuView());
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        });
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
    }
}
