package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.server.controller.MainMenuController;
import org.example.client.model.GraphicalResult;
import org.example.server.models.Result;
import org.example.server.models.enums.commands.MainMenuCommands;
import org.example.client.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenuView extends AppMenu {

    private final MainMenuController controller;
    private final Label menuTitleLabel;
    private final GraphicalResult errorLabel;
    private final TextButton pregameMenuButton;
    private final TextButton profileMenuButton;
    private final TextButton logoutButton;
    private Stage stage;
    private float fadeInTimer = 0f;

    public MainMenuView() {

        controller = new MainMenuController(this);

        menuTitleLabel = new Label("Main Menu", skin);

        errorLabel = new GraphicalResult();

        pregameMenuButton = new TextButton("Pregame Menu", skin);
        profileMenuButton = new TextButton("Profile Menu", skin);
        logoutButton = new TextButton("Logout", skin);

        setListeners();

    }

    private void showMenuTitle() {

        menuTitleLabel.setFontScale(4f);
        menuTitleLabel.setColor(0.878f, 0.627f, 0f, 1f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 9f, 6 * Gdx.graphics.getHeight() / 7f);
        stage.addActor(menuTitleLabel);

    }

    private void showGameLogo() {

        stardewValleyText.setScale(fadeInTimer * 2);

        stardewValleyText.setRotation(fadeInTimer * 3 * 360);
        stardewValleyText.setColor(stardewValleyText.getColor().r, stardewValleyText.getColor().g, stardewValleyText.getColor().b, fadeInTimer);

        stardewValleyText.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - stardewValleyText.getWidth() * fadeInTimer * 2) / 2,
                (Gdx.graphics.getHeight() - stardewValleyText.getHeight() * fadeInTimer * 2) / 2
        );

        stage.addActor(stardewValleyText);


    }

    private void showButtons() {

        pregameMenuButton.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);
        profileMenuButton.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);
        logoutButton.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);

        pregameMenuButton.setPosition(Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 7f);
        profileMenuButton.setPosition(Gdx.graphics.getWidth() / 8f, 3 * Gdx.graphics.getHeight() / 7f);
        logoutButton.setPosition(Gdx.graphics.getWidth() / 8f, 2 * Gdx.graphics.getHeight() / 7f);

        stage.addActor(pregameMenuButton);
        stage.addActor(profileMenuButton);
        stage.addActor(logoutButton);

    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        errorLabel.setPosition(Gdx.graphics.getWidth() / 8f, 5 * Gdx.graphics.getHeight() / 7f);
        stage.addActor(errorLabel.getMessage());
    }

    @Override
    public void render(float delta) {
        errorLabel.update(delta);

        if (fadeInTimer < 1f) {
            fadeInTimer += delta;
        } else {
            fadeInTimer = 1f;
        }

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuTitle();
        showGameLogo();
        showButtons();
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
        pregameMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.goToPregameMenu());
            }
        });

        profileMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.goToProfileMenu();
            }
        });

        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.logout();
            }
        });


    }


    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if ((matcher = MainMenuCommands.NewGame.getMatcher(input)) != null) {
            System.out.println(controller.createNewGame(
                    matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3"),
                    matcher.group("overflow"),
                    scanner
            ));
        } else if (MainMenuCommands.LoadGame.getMatcher(input) != null) {
            System.out.println(controller.loadGame());
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if (MainMenuCommands.Logout.getMatcher(input) != null) {
            System.out.println(controller.logout());
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public Result inputMap(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.SetMap.getMatcher(input)) != null) {
            return new Result(true, matcher.group("mapNumber"));
        }
        return new Result(false, "invalid command!");
    }
}
