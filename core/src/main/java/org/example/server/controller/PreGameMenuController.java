package org.example.server.controller;

import org.example.client.Main;
import org.example.client.controller.MenuController;
import org.example.common.models.GraphicalResult;
import org.example.server.models.*;
import org.example.server.models.enums.Menu;
import org.example.client.view.HomeView;
import org.example.client.view.menu.MainMenuView;
import org.example.client.view.menu.PreGameMenuView;

import java.util.ArrayList;
import java.util.Random;

public class PreGameMenuController extends MenuController {

    private final PreGameMenuView view;


    public PreGameMenuController(PreGameMenuView view) {
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

        if (view.getUsersAndChosenMaps().containsKey(addedUser))
            return new GraphicalResult(
                    "This is user has already been added",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        if (addedUser.getCurrentGame() != null)
            return new GraphicalResult(
                    "This user is already in a game",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

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

    public GraphicalResult createGame(){
        ArrayList<Player> players = new ArrayList<>();

        for (User user : view.getUsersAndChosenMaps().keySet()) {
            players.add(new Player(user));
        }

        if (players.size() < 2)
            return new GraphicalResult(
                    "There should be at least two players to start the game",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        Game game;
        App.setCurrentGame(game = new Game(players));
        game.init();
        for (User user : view.getUsersAndChosenMaps().keySet()) {
            user.setCurrentGame(game);
        }

        for ( Player player : players ){

            int mapId = view.getUsersAndChosenMaps().get(App.getUserByUsername(player.getUsername())) - 1;
            //   -1 kardam chon map haton zero base boodan vali man 1 base zadam

            player.setFarmMap(game.getFarmMap(mapId));
            player.setCurrentCell(game.getFarmMap(mapId).getCell(8, 70));
            game.getFarmMap(mapId).getHut().setOwner(player);

        }

        App.setCurrentMenu(Menu.Home);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new HomeView());

        return new GraphicalResult(
                "Game created successfully",
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public boolean isNotCurrentSelectorsMap(int number){
        if ( view.getUsersAndChosenMaps().get(view.getCurrentMapSelector()) == number ){
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

        for ( User user: view.getUsersAndChosenMaps().keySet() ){

            if ( view.getUsersAndChosenMaps().get(user).equals(number) ){
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
