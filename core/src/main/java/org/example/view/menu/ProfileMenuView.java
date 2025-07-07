package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.ProfileMenuController;
import org.example.models.Result;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.models.enums.commands.ProfileMenuCommands;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenuView extends AppMenu {

    private final ProfileMenuController controller;
    private Stage stage;

    public ProfileMenuView() {

        controller = new ProfileMenuController(this);

        setListeners();

    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        stage.addActor(new Label("PROFILE", skin));

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

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null) {
            System.out.println(controller.changeUsername(
                    matcher.group("username").trim()
            ));
        } else if ((matcher = ProfileMenuCommands.ChangeNickname.getMatcher(input)) != null) {
            System.out.println(controller.changeNickname(
                    matcher.group("nickname").trim()
            ));
        } else if ((matcher = ProfileMenuCommands.ChangeEmail.getMatcher(input)) != null) {
            System.out.println(controller.changeEmail(
                    matcher.group("email").trim()
            ));
        } else if ((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null) {
            System.out.println(controller.changePassword(
                    matcher.group("newPassword").trim(),
                    matcher.group("oldPassword").trim()
            ));
        } else if (ProfileMenuCommands.UserInfo.getMatcher(input) != null) {
            System.out.println(controller.showInfo());
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
