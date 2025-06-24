package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.MainMenuController;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.models.Result;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenuView extends AppMenu {

    private final MainMenuController controller;
    private Stage stage;

    public MainMenuView() {
        controller = new MainMenuController(this);
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(new Label("MAIN",skin));

    }

    @Override
    public void render(float v) {

        ScreenUtils.clear(0,0,0,1);

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleMainMenuButtons();

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

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if ((matcher = MainMenuCommands.NewGame.getMatcher(input)) != null) {
            System.out.println(controller.createNewGame(
                    matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3"),
                    matcher.group("overflow"),
                    scanner
            ));
        }
        else if (MainMenuCommands.LoadGame.getMatcher(input) != null) {
            System.out.println(controller.loadGame());
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else if (MainMenuCommands.Logout.getMatcher(input) != null) {
            System.out.println(controller.logout());
        }
        else {
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
