package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.WelcomeMenuController;
import org.example.models.GameAssetManager;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class WelcomeMenuView extends AppMenu {

    private final WelcomeMenuController controller;
    private final Table table;
    private Stage stage;
    private float textScale;
    private final TextButton registerButton;
    private final TextButton loginButton;
    private final TextButton exitButton;
    private float buttonTransparency;

    public WelcomeMenuView() {

        controller = new WelcomeMenuController(this);

        table = new Table();

        registerButton = new TextButton("Register", GameAssetManager.getGameAssetManager().getSkin());
        loginButton = new TextButton("Login", GameAssetManager.getGameAssetManager().getSkin());
        exitButton = new TextButton("Exit", GameAssetManager.getGameAssetManager().getSkin());

        setListeners();


    }


    private void placeStardewValleyText(float delta) {


        if ( textScale < 1.6f ) {

            stardewValleyText.setScale(textScale);
            textScale += delta;

        }

        stardewValleyText.setPosition((Gdx.graphics.getWidth() - stardewValleyText.getWidth()*textScale)/2,
                (Gdx.graphics.getHeight() - stardewValleyText.getHeight()*textScale)/2 + Gdx.graphics.getHeight()/4f
        );

        table.add(stardewValleyText);

    }

    private void placeButtons(float delta){

        if ( !(textScale < 1.6f) && buttonTransparency < 1.5f ) {

            buttonTransparency += delta;

        }


        registerButton.setSize(Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/10f);
        registerButton.setPosition((Gdx.graphics.getWidth() - registerButton.getWidth())/2,
                (Gdx.graphics.getHeight() - registerButton.getHeight())/2
        );



        loginButton.setSize(Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/10f);
        loginButton.setPosition((Gdx.graphics.getWidth() - loginButton.getWidth())/2,
                (Gdx.graphics.getHeight() - loginButton.getHeight())/2 - Gdx.graphics.getHeight()/8f
        );

        exitButton.setSize(Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/10f);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2,
                (Gdx.graphics.getHeight() - exitButton.getHeight())/2 - 2 * Gdx.graphics.getHeight()/8f
        );

//        registerButton.setColor(0.988f, 0.82f, 0.086f,Math.min(1.0f, buttonTransparency));
//        loginButton.setColor(0.988f, 0.82f, 0.086f,Math.min(1.0f, buttonTransparency));
//        exitButton.setColor(0.988f, 0.82f, 0.086f,Math.min(1.0f, buttonTransparency));


        registerButton.setColor(registerButton.getColor().r, registerButton.getColor().g, registerButton.getColor().b,Math.min(1.0f, buttonTransparency));
        loginButton.setColor(loginButton.getColor().r, loginButton.getColor().g, loginButton.getColor().b,Math.min(1.0f, buttonTransparency));
        exitButton.setColor(exitButton.getColor().r, exitButton.getColor().g, exitButton.getColor().b,Math.min(1.0f, buttonTransparency));


        stage.addActor(registerButton);
        stage.addActor(loginButton);
        stage.addActor(exitButton);


    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        stage.addActor(menuBackground);

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

//        controller.handleWelcomeMenuButtons();

        placeStardewValleyText(delta);
        placeButtons(delta);



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

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public float getButtonTransparency() {
        return buttonTransparency;
    }

    private void setListeners(){

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("registerButton clicked");
                controller.goToRegisterMenu();
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("loooggin");
                controller.goToLoginMenu();

            }
        });


        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getClickSound().play(1.0f);
                controller.exitMenu();
                System.exit(0);

            }
        });


    }


    @Override
    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            controller.exitMenu();
        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }


}
