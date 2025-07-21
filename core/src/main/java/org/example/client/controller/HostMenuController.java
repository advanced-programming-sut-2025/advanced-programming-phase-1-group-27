package org.example.client.controller;

import org.example.client.Main;
import org.example.client.view.menu.HostMenuView;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.menu.PasswordMenuView;
import org.example.common.models.GraphicalResult;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Result;

public class HostMenuController extends MenuController{
    private HostMenuView view;

    public HostMenuController(HostMenuView view) {
        this.view = view;
    }

    public GraphicalResult createGraphicalResult(){
        boolean isPublic = view.getIsPublicCheckBox().isChecked();
        boolean isVisible = view.getIsVisibleCheckBox().isChecked();
        if(view.getNameTextField().getText().isEmpty()){
            return new GraphicalResult(
                    "No name entered!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        if(!isPublic && view.getPasswordField().getText().isEmpty()){
            return new GraphicalResult(
                    "Private lobbies should have password!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        String name = view.getNameTextField().getText();
        String password = view.getPasswordField().getText();
        //TODO: payam

        return new GraphicalResult();
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LobbyMenuView());
        return new Result(true, "Redirecting to Lobby Menu ...");    }
}
