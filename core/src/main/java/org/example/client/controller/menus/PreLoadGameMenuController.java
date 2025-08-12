package org.example.client.controller.menus;

import org.example.client.model.ClientApp;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.menu.PreLoadGameMenuView;
import org.example.server.models.Lobby;
import org.example.server.models.Result;

import java.util.ArrayList;

public class PreLoadGameMenuController extends MenuController{
    private PreLoadGameMenuView view;
    private LobbyMenuController lobbyMenuController;

    public PreLoadGameMenuController(PreLoadGameMenuView view) {
        this.view = view;
        this.lobbyMenuController = new LobbyMenuController(new LobbyMenuView());
    }

    public ArrayList<Lobby> getLobbiesForHost() {
        ArrayList<Lobby> lobbies = lobbyMenuController.getLobbies();
        ArrayList<Lobby> selectedLobbies = new ArrayList<>();
        for(Lobby lobby : lobbies){
            if(lobby.getGame() == null){
                continue;
            }
            if(!lobby.getAdmin().getUsername().equals(ClientApp.getLoggedInUser().getUsername())){
                continue;
            }
            if(lobby.getGame().isGameRunning()){
                continue;
            }
            selectedLobbies.add(lobby);
        }
        return selectedLobbies;
    }

    public ArrayList<Lobby> getLobbiesForJoin() {
        ArrayList<Lobby> lobbies = lobbyMenuController.getLobbies();
        ArrayList<Lobby> selectedLobbies = new ArrayList<>();
        for(Lobby lobby : lobbies) {
            if (lobby.getGame() == null) {
                continue;
            }
            if (lobby.getGame().isGameRunning()) {
                continue;
            }
            selectedLobbies.add(lobby);
        }
        return selectedLobbies;
    }

    public void restore(){

    }

    public void join(){

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
