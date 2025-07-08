package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.PreGameMenuController;
import org.example.models.App;
import org.example.models.GameAssetManager;
import org.example.models.GraphicalResult;
import org.example.models.User;
import org.example.view.AppMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PreGameMenuView extends AppMenu {


    private final PreGameMenuController controller;
    private Stage stage;

    private final Label menuTitle;
    private final Label mapSelectionLabel;
    private final Label usernameLabel;
    private final Label playersLabel;

    private final TextButton addUserButton;
    private final TextButton backButton;

    private final TextField usernameField;

    private final ImageButton map1Button;
    private final ImageButton map2Button;
    private final ImageButton map3Button;
    private final ImageButton map4Button;

    private User currentMapSelector;


    private HashMap<User,Integer> usersAndChosenMaps = new HashMap<>();

    public PreGameMenuView() {

        controller = new PreGameMenuController(this);

        currentMapSelector = App.getLoggedInUser();


        for ( User user: App.getUsers() ){

            usersAndChosenMaps.put(user, controller.assignRandomMap());

        }


        menuTitle = new Label("Pre Game Menu", skin);
        mapSelectionLabel = new Label("Dear " + currentMapSelector.getUsername() + " please choose your map ^-^", skin);
        usernameLabel = new Label("Player Username:", skin);
        playersLabel = new Label("Players:", skin);

        usernameField = new TextField("", skin);

        addUserButton = new TextButton("Add User", skin);
        backButton = new TextButton("Back", skin);

        map1Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap1()));
        map2Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap2()));
        map3Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap3()));
        map4Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap4()));

        setListeners();

    }

    private void showMenuTitle() {

        menuTitle.setFontScale(3f);
        menuTitle.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitle);

    }

    private void showLabels() {


        playersLabel.setPosition(Gdx.graphics.getWidth() / 10f, 8 * Gdx.graphics.getHeight() / 12f);
        usernameLabel.setPosition(Gdx.graphics.getWidth() / 10f, 2 * Gdx.graphics.getHeight() / 12f);

        mapSelectionLabel.setPosition(Gdx.graphics.getWidth() / 2f, 8 * Gdx.graphics.getHeight() / 12f);

        int userIndex = 0;
        for (User user : usersAndChosenMaps.keySet()) {
            Label addedPlayerLabel = new Label("#    " + user.getUsername(), skin);
            addedPlayerLabel.setPosition(Gdx.graphics.getWidth() / 8f, (7-userIndex) * Gdx.graphics.getHeight() / 12f);
            stage.addActor(addedPlayerLabel);
            userIndex ++;
        }


        stage.addActor(playersLabel);
        stage.addActor(usernameLabel);
        stage.addActor(mapSelectionLabel);

    }

    private void showFields() {

        usernameField.setWidth(Gdx.graphics.getWidth() / 5f);

        usernameField.setPosition(Gdx.graphics.getWidth() / 10f + usernameLabel.getWidth() + 50, 2 * Gdx.graphics.getHeight() / 12f - 20);

        stage.addActor(usernameField);

    }

    private void showButtons() {

        addUserButton.setHeight(usernameField.getHeight());
        backButton.setHeight(usernameField.getHeight());

        addUserButton.setPosition( usernameField.getX() + usernameField.getWidth() + 50 , usernameField.getY() );
        backButton.setPosition( usernameField.getX() + usernameField.getWidth()  + addUserButton.getWidth() + 350 , usernameField.getY() );

        stage.addActor(addUserButton);
        stage.addActor(backButton);

    }

    private void showMapButtons(){

        map1Button.setPosition(Gdx.graphics.getWidth() / 2f + 11 * Gdx.graphics.getWidth() / 90f , 6 * Gdx.graphics.getHeight() / 12f);
        map2Button.setPosition(Gdx.graphics.getWidth() / 2f + 22 * Gdx.graphics.getWidth() / 90f + Gdx.graphics.getWidth() / 15f, 6 * Gdx.graphics.getHeight() / 12f);
        map3Button.setPosition(Gdx.graphics.getWidth() / 2f + 11 * Gdx.graphics.getWidth() / 90f , 6 * Gdx.graphics.getHeight() / 12f - 11 * Gdx.graphics.getWidth() / 90f);
        map4Button.setPosition(Gdx.graphics.getWidth() / 2f + 22 * Gdx.graphics.getWidth() / 90f + Gdx.graphics.getWidth() / 15f, 6 * Gdx.graphics.getHeight() / 12f - 11 * Gdx.graphics.getWidth() / 90f);


        stage.addActor(map1Button);
        stage.addActor(map2Button);
        stage.addActor(map3Button);
        stage.addActor(map4Button);

    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);


    }

    @Override
    public void render(float v) {

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuTitle();
        showLabels();
        showFields();
        showButtons();
        showMapButtons();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void setListeners() {

    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public HashMap<User, Integer> getUsersAndChosenMaps() {
        return usersAndChosenMaps;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

}
