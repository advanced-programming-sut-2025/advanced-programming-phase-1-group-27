package org.example.client.controller;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.model.ClientGame;
import org.example.client.model.MiniPlayer;
import org.example.client.view.HomeView;
import org.example.client.view.menu.MainMenuView;
import org.example.client.view.menu.PregameMenuView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.enums.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PregameMenuController extends MenuController {
    private final PregameMenuView view;


    public PregameMenuController(PregameMenuView view) {
        this.view = view;
    }

    public GraphicalResult addUser(){

        if (view.getUsernameField().getText().isEmpty())
            return new GraphicalResult(
                    "Please fill in the field",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        User addedUser = App.getUserByUsername(view.getUsernameField().getText());

        if (addedUser == null)
            return new GraphicalResult(
                    "User not found",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        if (view.getUsernameToMap().containsKey(addedUser))
            return new GraphicalResult(
                    "This is user has already been added",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

//        if (addedUser.getCurrentGame() != null)
//            return new GraphicalResult(
//                    "This user is already in a game",
//                    GameAssetManager.getGameAssetManager().getErrorColor()
//            );

        if ( view.getUser1Label().getText().isEmpty() ){
            view.getUser1Label().setText("#    " + addedUser.getUsername());
        }
        else if ( view.getUser2Label().getText().isEmpty() ){
            view.getUser2Label().setText("#    " + addedUser.getUsername());
        }
        else if ( view.getUser3Label().getText().isEmpty() ){
            view.getUser3Label().setText("#    " + addedUser.getUsername());
            view.setGameFull(true);
        }
        view.setCurrentMapSelector(addedUser);
        view.getUsernameField().setText("");
        view.updateUsersAndChosenMaps(addedUser, assignRandomMap());

        return new GraphicalResult(
                "User added successfully" + (view.isGameFull()? " . Capacity is full" : ""),
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public GraphicalResult chooseMap(int mapId) {
        if (alreadyChosen(mapId))
            return new GraphicalResult(
                    "This map has already been selected",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        view.updateUsersAndChosenMaps(view.getCurrentMapSelector(), mapId);
        return new GraphicalResult(
                "Map selected successfully",
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public GraphicalResult createGame() {
        if (!ClientApp.getLoggedInUser().getUsername().equals(view.getLobby().getAdmin().getUsername()))
            return new GraphicalResult("Only the host can start the game");
        if (view.getUsernameToMap().size() < 2)
            return new GraphicalResult(
                    "There should be at least two players to start the game",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyInfo", view.getLobby().getInfo());
            put("usernameToMap", view.getUsernameToMap());
        }}, Message.Type.create_game));

        return new GraphicalResult(
                "Game created successfully",
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public void startGame() {
        ArrayList<MiniPlayer> miniPlayers = new ArrayList<>();
        for (User user : view.getLobby().getUsers()) {
            miniPlayers.add(new MiniPlayer(user));
        }
        ClientGame clientGame;
        Player currentPlayer = new Player(ClientApp.getLoggedInUser());
        ClientApp.setCurrentGame(clientGame = new ClientGame(
                view.getLobby(),
                currentPlayer,
                miniPlayers
        ));
        clientGame.init();
        currentPlayer.setFarmMap(clientGame.getFarmMap(view.getUsernameToMap().get(currentPlayer.getUsername())));

        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new HomeView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    public boolean isNotCurrentSelectorsMap(int number){
        if ( view.getUsernameToMap().get(view.getCurrentMapSelector()) == number ){
            return false;
        }
        return true;
    }

    public int assignRandomMap(){

        int randomMap;

        do{
            randomMap = (new Random().nextInt(1,5));
        }while( alreadyChosen(randomMap) );


        return randomMap;

    }

    private boolean alreadyChosen(int number){

        for ( String username : view.getUsernameToMap().keySet() ){

            if ( view.getUsernameToMap().get(username).equals(number) ){
                return true;
            }

        }

        return false;
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        App.setCurrentMenu(Menu.MainMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
        return new Result(true, "Redirecting to main menu ...");
    }
}
