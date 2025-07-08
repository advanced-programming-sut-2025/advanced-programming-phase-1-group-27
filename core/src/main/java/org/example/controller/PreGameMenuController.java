package org.example.controller;

import org.example.models.Result;
import org.example.models.User;
import org.example.view.menu.PreGameMenuView;

import java.util.Random;

public class PreGameMenuController extends MenuController {

    private final PreGameMenuView view;


    public PreGameMenuController(PreGameMenuView view) {
        this.view = view;
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
        return null;
    }
}
