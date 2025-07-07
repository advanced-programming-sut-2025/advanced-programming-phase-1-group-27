package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.PreGameMenuController;
import org.example.models.App;
import org.example.models.GameAssetManager;
import org.example.models.GraphicalResult;
import org.example.models.User;
import org.example.view.AppMenu;

import java.util.Scanner;

public class PreGameMenuView extends AppMenu {


    private final PreGameMenuController controller;
    private final Label menuTitle;
    private final Label user0Label;
    private final Label user1Label;
    private final Label user2Label;
    private final Label user3Label;
    private final Label mapSelectionLabel;
    private final Label usernameLabel;
    private final TextButton addUserButton;
    private final TextButton backButton;
    private final ImageButton map1Button;
    private final ImageButton map2Button;
    private final ImageButton map3Button;
    private final ImageButton map4Button;
    private final GraphicalResult error;
    private Stage stage;
    private User currentMapSelector;

    public PreGameMenuView() {

        this.controller = new PreGameMenuController(this);

        currentMapSelector = App.getLoggedInUser();

        menuTitle = new Label("Pre Game Menu", skin);
        user0Label = new Label("", skin);
        user1Label = new Label("", skin);
        user2Label = new Label("", skin);
        user3Label = new Label("", skin);
        mapSelectionLabel = new Label("Dear " + currentMapSelector.getUsername() + " please choose your map ^-^", skin);
        usernameLabel = new Label("Player Username:", skin);

        addUserButton = new TextButton("Add User", skin);
        backButton = new TextButton("Back", skin);

        map1Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap1()));
        map2Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap2()));
        map3Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap3()));
        map4Button = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getMap4()));

        error = new GraphicalResult();


        setListeners();

    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        stage.addActor(new Label("pregame", skin));

        stage.addActor(map1Button);


    }

    @Override
    public void render(float v) {

        Main.getBatch().begin();
        Main.getBatch().end();

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

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

}
