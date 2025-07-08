package org.example.controller;

import org.example.Main;
import org.example.models.*;
import org.example.models.enums.Menu;
import org.example.view.HomeView;
import org.example.view.menu.MainMenuView;
import org.example.view.menu.PreGameMenuView;

import java.util.ArrayList;
import java.util.Random;

public class PreGameMenuController extends MenuController {

    private final PreGameMenuView view;


    public PreGameMenuController(PreGameMenuView view) {
        this.view = view;
    }

    public void addUser(){

        if ( view.getUsernameField().getText().isEmpty() ){
            /// TODO: error empty field bede
            return;
        }

        User addedUser = App.getUserByUsername(view.getUsernameField().getText());

        if ( addedUser == null ){
            ///  TODO: error user not found bede
            return;
        }

        if ( view.getUsersAndChosenMaps().containsKey(addedUser) ){
            ///  TODO: error already in current game
            return;
        }

        if ( addedUser.getCurrentGame() != null ){
            ///  TODO: error only in one game
            return;
        }

        if ( view.getUser1Label().getText().isEmpty() ){
            view.getUser1Label().setText("#    " + addedUser.getUsername());
        }
        else if ( view.getUser2Label().getText().isEmpty() ){
            view.getUser2Label().setText("#    " + addedUser.getUsername());
        }
        else if ( view.getUser3Label().getText().isEmpty() ){
            view.getUser3Label().setText("#    " + addedUser.getUsername());
            view.setGameFull(true);
            /// TODO: payam bede game por shode dige
        }

        view.setCurrentMapSelector(addedUser);
        view.getUsernameField().setText("");
        view.updateUsersAndChosenMaps(addedUser,assignRandomMap());

    }

    public void chooseMap1(){

        if ( alreadyChosen(1) ){
            ///  TODO: error map already chosen
            return;
        }

        view.updateUsersAndChosenMaps(view.getCurrentMapSelector(),1);

    }

    public void chooseMap2(){

        if ( alreadyChosen(2) ){
            ///  TODO: error map already chosen
            return;
        }

        view.updateUsersAndChosenMaps(view.getCurrentMapSelector(),2);

    }

    public void chooseMap3(){

        if ( alreadyChosen(3) ){
            ///  TODO: error map already chosen
            return;
        }

        view.updateUsersAndChosenMaps(view.getCurrentMapSelector(),3);

    }

    public void createGame(){

        ArrayList<Player> players = new ArrayList<>();

        for (User user : view.getUsersAndChosenMaps().keySet()) {
            players.add(new Player(user));
        }

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

    }

    public boolean isNotCurrentSelectorsMap(int number){
        if ( view.getUsersAndChosenMaps().get(view.getCurrentMapSelector()) == number ){
            return false;
        }
        return true;
    }

    public void chooseMap4(){

        if ( alreadyChosen(4) ){
            ///  TODO: error map already chosen
            return;
        }

        view.updateUsersAndChosenMaps(view.getCurrentMapSelector(),4);

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
