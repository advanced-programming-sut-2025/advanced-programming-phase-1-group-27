package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.LobbyMenuController;
import org.example.client.controller.PregameMenuController;
import org.example.client.model.ClientApp;
import org.example.server.models.App;
import org.example.server.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.server.models.Lobby;
import org.example.server.models.User;
import org.example.client.view.AppMenu;

import java.util.HashMap;
import java.util.Scanner;

public class PregameMenuView extends AppMenu {


    private final PregameMenuController controller;
    private Stage stage;

    private final Label menuTitle;
    private final Label mapSelectionLabel;
    private final Label lobbyNameLabel;
//    private final Label usernameLabel;
    private final Label playersLabel;

    private final Label user0Label;
    private final Label user1Label;
    private final Label user2Label;
    private final Label user3Label;

    private final Label userMap0Label;
    private final Label userMap1Label;
    private final Label userMap2Label;
    private final Label userMap3Label;

    private final GraphicalResult errorLabel;

//    private final TextButton addUserButton;
    private final TextButton backButton;
    private final TextButton createGameButton;

//    private final TextField usernameField;

    private final ImageButton map1Button;
    private final ImageButton map2Button;
    private final ImageButton map3Button;
    private final ImageButton map4Button;

    private User currentMapSelector;

    private float blinkerTimer = 0f;

    private boolean gameFull = false;

    private Lobby lobby;


    public PregameMenuView(Lobby lobby) {

        controller = new PregameMenuController(this);

        currentMapSelector = ClientApp.getLoggedInUser();

        this.lobby = lobby;

//        usernameToMap.put(currentMapSelector.getUsername(), controller.assignRandomMap());

        menuTitle = new Label("Pre Game Menu", skin);
        lobbyNameLabel = new Label(lobby.getName(), skin);
        mapSelectionLabel = new Label("Dear " + currentMapSelector.getUsername() + " please choose your map ^-^", skin);
//        usernameLabel = new Label("Player Username:", skin);
        playersLabel = new Label("Players:", skin);
        user0Label = new Label("", skin);
        user1Label = new Label("", skin);
        user2Label = new Label("", skin);
        user3Label = new Label("", skin);

        userMap0Label = new Label("", skin);
        userMap1Label = new Label("", skin);
        userMap2Label = new Label("", skin);
        userMap3Label = new Label("", skin);


        errorLabel = new GraphicalResult();

//        usernameField = new TextField("", skin);

//        addUserButton = new TextButton("Add User", skin);
        backButton = new TextButton("Back", skin);
        createGameButton = new TextButton("Create Game", skin);

        map1Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap1()));
        map2Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap2()));
        map3Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap3()));
        map4Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap4()));

        setListeners();

    }

    private void showMenuTitle() {
        menuTitle.setFontScale(3f);
        menuTitle.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        lobbyNameLabel.setFontScale(2f);
        lobbyNameLabel.setPosition( 5 * Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitle);
    }

    private void showLabels() {

        playersLabel.setPosition(Gdx.graphics.getWidth() / 10f, 8 * Gdx.graphics.getHeight() / 12f);
//        usernameLabel.setPosition(Gdx.graphics.getWidth() / 10f, 2 * Gdx.graphics.getHeight() / 12f);
        mapSelectionLabel.setPosition(Gdx.graphics.getWidth() / 2f, 8 * Gdx.graphics.getHeight() / 12f);

        user0Label.setPosition(Gdx.graphics.getWidth() / 8f, 7 * Gdx.graphics.getHeight() / 12f);
        user1Label.setPosition(Gdx.graphics.getWidth() / 8f, 6 * Gdx.graphics.getHeight() / 12f);
        user2Label.setPosition(Gdx.graphics.getWidth() / 8f, 5 * Gdx.graphics.getHeight() / 12f);
        user3Label.setPosition(Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 12f);

        userMap0Label.setPosition(2 * Gdx.graphics.getWidth() / 8f, 7 * Gdx.graphics.getHeight() / 12f);
        userMap1Label.setPosition(2 *  Gdx.graphics.getWidth() / 8f, 6 * Gdx.graphics.getHeight() / 12f);
        userMap2Label.setPosition(2 * Gdx.graphics.getWidth() / 8f, 5 * Gdx.graphics.getHeight() / 12f);
        userMap3Label.setPosition(2 * Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 12f);

//        usernameLabel.setVisible(!gameFull);

        stage.addActor(user0Label);
        stage.addActor(user1Label);
        stage.addActor(user2Label);
        stage.addActor(user3Label);
        stage.addActor(userMap0Label);
        stage.addActor(userMap1Label);
        stage.addActor(userMap2Label);
        stage.addActor(userMap3Label);
        stage.addActor(playersLabel);
//        stage.addActor(usernameLabel);
        stage.addActor(mapSelectionLabel);

    }

//    private void showFields() {
//
//        usernameField.setWidth(Gdx.graphics.getWidth() / 5f);
//
//        usernameField.setPosition(Gdx.graphics.getWidth() / 10f + usernameLabel.getWidth() + 50, 2 * Gdx.graphics.getHeight() / 12f - 20);
//
//        usernameField.setVisible(!gameFull);
//
//        stage.addActor(usernameField);
//
//    }

    private void showButtons() {

//        addUserButton.setHeight(usernameField.getHeight());
        backButton.setWidth(Gdx.graphics.getWidth() / 4f);
        createGameButton.setWidth(Gdx.graphics.getWidth() / 4f);

//        addUserButton.setPosition(usernameField.getX() + usernameField.getWidth() + 50, usernameField.getY());
        backButton.setPosition(
                9 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f
        );
        createGameButton.setPosition(
                2 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f
        );

//        addUserButton.setVisible(!gameFull);

//        stage.addActor(addUserButton);
        stage.addActor(backButton);
        stage.addActor(createGameButton);
    }

    private void showMapButtons(){

        map1Button.setPosition(Gdx.graphics.getWidth() / 2f + 11 * Gdx.graphics.getWidth() / 90f ,
                6 * Gdx.graphics.getHeight() / 12f);
        map2Button.setPosition(Gdx.graphics.getWidth() / 2f + 22 * Gdx.graphics.getWidth() / 90f + Gdx.graphics.getWidth() / 15f,
                6 * Gdx.graphics.getHeight() / 12f);
        map3Button.setPosition(Gdx.graphics.getWidth() / 2f + 11 * Gdx.graphics.getWidth() / 90f ,
                6 * Gdx.graphics.getHeight() / 12f - 11 * Gdx.graphics.getWidth() / 90f);
        map4Button.setPosition(Gdx.graphics.getWidth() / 2f + 22 * Gdx.graphics.getWidth() / 90f + Gdx.graphics.getWidth() / 15f,
                6 * Gdx.graphics.getHeight() / 12f - 11 * Gdx.graphics.getWidth() / 90f);

//        for (String username : usernameToMap.keySet() ) {
//
//            if ( !username.equals(currentMapSelector.getUsername()) ) {
//                int mapNumber = usernameToMap.get(username);
//                Label mapOwnerLabel = new Label(username, skin);
//                mapOwnerLabel.setPosition(
//                        map1Button.getX() + ((mapNumber+1)%2) * (11 * Gdx.graphics.getWidth() / 90f + Gdx.graphics.getWidth() / 15f) + (map1Button.getWidth()-mapOwnerLabel.getWidth())/2,
//                        6 * Gdx.graphics.getHeight() / 12f - ((int)((mapNumber-1)/2)) * (11 * Gdx.graphics.getWidth() / 90f) + (map1Button.getHeight()-mapOwnerLabel.getHeight())/2
//                );
//
//                stage.addActor(mapOwnerLabel);
//            }
//        }



        stage.addActor(map1Button);
        stage.addActor(map2Button);
        stage.addActor(map3Button);
        stage.addActor(map4Button);
    }

    private void updateLobby(){
        this.lobby = controller.getLobby(lobby.getId());
        User user0 , user1 , user2 , user3;
        int size = lobby.getUsers().size();
        if(size > 3){
            user3 = lobby.getUsers().get(3);
        }else {
            user3 = null;
        }
        if(size > 2){
            user2 = lobby.getUsers().get(2);
        }else {
            user2 = null;
        }
        if(size > 1){
            user1 = lobby.getUsers().get(1);
        }else  {
            user1 = null;
        }
        if(size > 0){
            user0 = lobby.getUsers().get(0);
        }else  {
            user0 = null;
        }
        if (user0 != null) {
            user0Label.setText(user0.getUsername());
            int index = lobby.getIndexMap(user0);
            if(index != -1){
                userMap0Label.setText(index);
            }else {
                userMap0Label.setText("");
            }
        }else {
            user0Label.setText("Empty");
            userMap0Label.setText("");
        }
        if(user1 != null) {
            user1Label.setText(user1.getUsername());
            int index = lobby.getIndexMap(user1);
            if(index != -1){
                userMap1Label.setText(index);
            }else {
                userMap1Label.setText("");
            }
        }else {
            user1Label.setText("Empty");
            userMap1Label.setText("");
        }
        if(user2 != null) {
            user2Label.setText(user2.getUsername());
            int index = lobby.getIndexMap(user2);
            if(index != -1){
                userMap2Label.setText(index);
            }else {
                userMap2Label.setText("");
            }
        }else  {
            user2Label.setText("Empty");
            userMap2Label.setText("");
        }
        if(user3 != null) {
            user3Label.setText(user3.getUsername());
            int index = lobby.getIndexMap(user3);
            if(index != -1){
                userMap3Label.setText(index);
            }else {
                userMap3Label.setText("");
            }
        }else  {
            user3Label.setText("Empty");
            userMap3Label.setText("");
        }
    }


//    private void makeCurrentMapBlinking(){
//
//        int mapNumber = usernameToMap.get(currentMapSelector);
//
//        int alpha = (((blinkerTimer-(int)blinkerTimer) < 0.5f)? 1:0);
//
//        map1Button.setColor(map1Button.getColor().r,map1Button.getColor().g,map1Button.getColor().b,1);
//        map2Button.setColor(map2Button.getColor().r,map2Button.getColor().g,map2Button.getColor().b,1);
//        map3Button.setColor(map3Button.getColor().r,map3Button.getColor().g,map3Button.getColor().b,1);
//        map4Button.setColor(map4Button.getColor().r,map4Button.getColor().g,map4Button.getColor().b,1);
//
//
//        if ( mapNumber == 1 ){
//            map1Button.setColor(map1Button.getColor().r,map1Button.getColor().g,map1Button.getColor().b,alpha);
//        }
//        else if ( mapNumber == 2 ){
//            map2Button.setColor(map2Button.getColor().r,map2Button.getColor().g,map2Button.getColor().b,alpha);
//        }
//        else if ( mapNumber == 3 ){
//            map3Button.setColor(map3Button.getColor().r,map3Button.getColor().g,map3Button.getColor().b,alpha);
//        }
//        else if ( mapNumber == 4 ){
//            map4Button.setColor(map4Button.getColor().r,map4Button.getColor().g,map4Button.getColor().b,alpha);
//        }
//
//    }
//
//    private void makeChosenMapsHalfTransparent(){
//
//        if ( usernameToMap.containsValue(1) && controller.isNotCurrentSelectorsMap(1)){
//            map1Button.setColor(map1Button.getColor().r,map1Button.getColor().g,map1Button.getColor().b,0.5f);
//        }
//
//        if ( usernameToMap.containsValue(2) && controller.isNotCurrentSelectorsMap(2)){
//            map2Button.setColor(map2Button.getColor().r,map2Button.getColor().g,map2Button.getColor().b,0.5f);
//        }
//
//        if ( usernameToMap.containsValue(3) && controller.isNotCurrentSelectorsMap(3)){
//            map3Button.setColor(map3Button.getColor().r,map3Button.getColor().g,map3Button.getColor().b,0.5f);
//        }
//
//        if ( usernameToMap.containsValue(4) && controller.isNotCurrentSelectorsMap(4)){
//            map4Button.setColor(map4Button.getColor().r,map4Button.getColor().g,map4Button.getColor().b,0.5f);
//        }
//
//    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);
        errorLabel.setPosition(Gdx.graphics.getWidth() / 10f, 9 * Gdx.graphics.getHeight() / 12f);
        stage.addActor(errorLabel.getMessage());

    }

    @Override
    public void render(float delta) {
        errorLabel.update(delta);
        blinkerTimer += delta;
        mapSelectionLabel.setText("Dear " + currentMapSelector.getUsername() + " please choose your map ^-^");

        Main.getBatch().begin();
        Main.getBatch().end();

        showMenuTitle();
        showLabels();
//        showFields();
        showButtons();
        showMapButtons();
        updateLobby();
//        makeCurrentMapBlinking();
//        makeChosenMapsHalfTransparent();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.leave();
            }

        });

//        addUserButton.addListener(new ClickListener() {
//
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//
//                playClickSound();
//                errorLabel.set(controller.addUser());
//
//            }
//
//        });

        map1Button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.chooseMap(0));
            }

        });

        map2Button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.chooseMap(1));
            }

        });

        map3Button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.chooseMap(2));
            }

        });

        map4Button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.chooseMap(3));
            }

        });

        createGameButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                errorLabel.set(controller.createGame());

            }

        });

    }

    public void setCurrentMapSelector(User currentMapSelector) {
        this.currentMapSelector = currentMapSelector;
    }

//    public void updateUsersAndChosenMaps(User addedUser, Integer chosenMap) {
//        this.usernameToMap.put(addedUser.getUsername(), chosenMap);
//    }

//    public TextField getUsernameField() {
//        return usernameField;
//    }

    public Label getUser1Label() {
        return user1Label;
    }

    public Label getUser2Label() {
        return user2Label;
    }

    public Label getUser3Label() {
        return user3Label;
    }

//    public HashMap<String, Integer> getUsernameToMap() {
//        return usernameToMap;
//    }

    public User getCurrentMapSelector() {
        return currentMapSelector;
    }

    public void setGameFull(boolean gameFull) {
        this.gameFull = gameFull;
    }

    public boolean isGameFull() {
        return gameFull;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public PregameMenuController getController() {
        return controller;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

}
