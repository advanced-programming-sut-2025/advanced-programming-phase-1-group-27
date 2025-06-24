package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.LoginMenuController;
import org.example.models.enums.commands.LoginMenuCommands;
import org.example.models.Result;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenuView extends AppMenu {

    private final LoginMenuController controller;
    private Stage stage;

    public LoginMenuView() {
        controller = new LoginMenuController(this);
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        stage.addActor(new Label("LOGIN",skin));


    }

    @Override
    public void render(float v) {


        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleLoginMenuButtons();

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
        if ((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            System.out.println(controller.login(
                    matcher.group("username").trim(),
                    matcher.group("password").trim(),
                    matcher.group("stay") != null
            ));
        }
        else if ((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null) {
            System.out.println(controller.forgetPassword(
                    matcher.group("username").trim(),
                    scanner
            ));
        }
        else if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public Matcher getAnswer(Scanner scanner) {
        String input = scanner.nextLine().trim();
        return LoginMenuCommands.AnswerQuestion.getMatcher(input);
    }
}
