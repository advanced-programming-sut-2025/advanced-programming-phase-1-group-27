package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.WelcomeMenuController;
import org.example.models.GameAssetManager;
import org.example.models.Result;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class WelcomeMenuView extends AppMenu {

    private final WelcomeMenuController controller;
    private Stage stage;
    private final Table table;
    private final Image stardewVallleyText;

    public WelcomeMenuView() {
        controller = new WelcomeMenuController(this);
        stardewVallleyText = GameAssetManager.getGameAssetManager().getStardewVallleyText();
//        stardewVallleyText.scaleBy(1.5f);
        table = new Table();
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        table.add(stardewVallleyText).center();

        stage.addActor(menuBackground);

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleWelcomeMenuButtons();


    }

    @Override
    public void resize(int width, int height) {

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
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            controller.exitMenu();
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }


}
