package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.common.models.GameAssetManager;
import org.example.server.models.User;

import java.util.Scanner;

public class UserInfoView extends AppMenu {
    private final Stage stage;
    private final Table table;
    private final TextButton backButton;
    private final AppMenu lastMenu;

    public UserInfoView(AppMenu lastMenu) {
        stage = new Stage(new ScreenViewport());
        table = new Table();
        backButton = new TextButton("Back", skin);
        this.lastMenu = lastMenu;

        setListeners();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);
        table.padTop(100);
        table.setFillParent(true);
        table.center();
        showMenuTitle();
        addUserInfo();
        table.add(backButton);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void addUserInfo() {
        User user = ClientApp.getLoggedInUser();
        int avatarId = user.getAvatarId();
        Image avatarImage = new Image(GameAssetManager.getGameAssetManager().getAvatarTexture(avatarId));
        avatarImage.setScaling(Scaling.fit);
        table.add(avatarImage).size(300).row();
        Label label = new Label("Username: " + user.getUsername(), skin);
        label.setFontScale(1.2f);
        label.setColor(Color.BLACK);
        table.add(label).pad(10).row();
        label = new Label("Nickname: " + user.getNickname(), skin);
        label.setFontScale(1.2f);
        label.setColor(Color.BLACK);
        table.add(label).pad(10).row();
        label = new Label("Email: " + user.getEmail(), skin);
        label.setFontScale(1.2f);
        label.setColor(Color.BLACK);
        table.add(label).pad(10).row();
        label = new Label("Number of games played: " + user.getNumberOfGamesPlayed(), skin);
        label.setFontScale(1.2f);
        label.setColor(Color.BLACK);
        table.add(label).pad(10).row();
        label = new Label("Max money earned: " + user.getMaxMoneyEarned(), skin);
        label.setFontScale(1.2f);
        label.setColor(Color.BLACK);
        table.add(label).pad(10).row();
    }

    private void showMenuTitle() {
        Label menuTitle = new Label("User Info Menu", skin);
        menuTitle.setFontScale(4f);
        menuTitle.setColor(0.878f, 0.627f, 0f, 1f);
        menuTitle.setPosition(Gdx.graphics.getWidth() / 9f, 6 * Gdx.graphics.getHeight() / 7f);
        stage.addActor(menuTitle);
    }

    private void setListeners() {
        backButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               playClickSound();
               Main.getMain().getScreen().dispose();
               ClientApp.setCurrentMenu(lastMenu);
               Main.getMain().setScreen(ClientApp.getCurrentMenu());
           }
        });
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

    @Override
    public void executeCommands(Scanner scanner) {

    }
}
