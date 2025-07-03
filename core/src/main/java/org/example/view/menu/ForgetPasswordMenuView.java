package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.ForgetPasswordMenuController;
import org.example.controller.SecurityQuestionMenuController;
import org.example.view.AppMenu;

import javax.swing.text.View;
import java.awt.*;
import java.util.Scanner;

public class ForgetPasswordMenuView extends AppMenu {

    private final ForgetPasswordMenuController controller;
    private Stage stage;

//    private final Label menuTitleLabel;
//    private final Label usernameLabel;
//    private final Label answerLabel;
//    private final

    public ForgetPasswordMenuView() {

        this.controller = new ForgetPasswordMenuController(this);


        setListeners();

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
