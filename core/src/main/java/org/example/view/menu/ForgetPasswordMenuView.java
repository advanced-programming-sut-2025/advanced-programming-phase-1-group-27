package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.ForgetPasswordMenuController;
import org.example.controller.SecurityQuestionMenuController;
import org.example.view.AppMenu;

import javax.swing.text.View;
import java.util.Scanner;

public class ForgetPasswordMenuView extends AppMenu {

    private final ForgetPasswordMenuController controller;
    private Stage stage;

    private final Label menuTitleLabel;
    private final Label usernameLabel;
    private final Label answerLabel;
    private final Label newPasswordLabel;
    private final Label errorLabel;
    private final Label securityQuestionLabel;

    private final TextButton backButton;
    private final TextButton submitButton;
    private final TextButton randomPasswordButton;

    private final TextField usernameField;
    private final TextField answerField;
    private final TextField newPasswordField;

    private float fadeInTimer = 0f;
    private float errorTimer = 5f;

    private boolean usernameSubmitted = false;

    public ForgetPasswordMenuView() {

        this.controller = new ForgetPasswordMenuController(this);

        menuTitleLabel = new Label("Recover Password Menu", skin);
        usernameLabel = new Label("Username:", skin);
        answerLabel = new Label("Answer:", skin);
        errorLabel = new Label("", skin);
        newPasswordLabel = new Label("New Password:", skin);
        securityQuestionLabel = new Label("", skin);

        backButton = new TextButton("Back", skin);
        submitButton = new TextButton("Submit", skin);
        randomPasswordButton = new TextButton("???", skin);

        usernameField = new TextField("", skin);
        answerField = new TextField("", skin);
        newPasswordField = new TextField("", skin);

        usernameLabel.setVisible(true);
        answerLabel.setVisible(false);
        newPasswordLabel.setVisible(false);
        securityQuestionLabel.setVisible(false);
        randomPasswordButton.setVisible(false);
        answerField.setVisible(false);
        newPasswordField.setVisible(false);

        setListeners();

    }

    private void showMenuTitle(){

        menuTitleLabel.setFontScale(3f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth()/8f, 5*Gdx.graphics.getHeight()/6f);
        stage.addActor(menuTitleLabel);

    }

    private void showErrorMessage(){

        errorLabel.setPosition(Gdx.graphics.getWidth()/8f, 6 * Gdx.graphics.getHeight()/7f - Gdx.graphics.getHeight()/9f + 20);
        errorLabel.setColor(1,0.31f,0,errorTimer/5);
        stage.addActor(errorLabel);

    }

    private void showLabels(){

        usernameLabel.setVisible(!usernameSubmitted);
        answerLabel.setVisible(usernameSubmitted);
        newPasswordLabel.setVisible(usernameSubmitted);

        usernameLabel.setPosition( Gdx.graphics.getWidth()/4f * fadeInTimer,  4 * Gdx.graphics.getHeight()/6f );
        answerLabel.setPosition( Gdx.graphics.getWidth()/4f * fadeInTimer,  3 * Gdx.graphics.getHeight()/6f );
        newPasswordLabel.setPosition( Gdx.graphics.getWidth()/4f * fadeInTimer,  2 * Gdx.graphics.getHeight()/6f );

        stage.addActor(usernameLabel);
        stage.addActor(answerLabel);
        stage.addActor(newPasswordLabel);

    }

    private void showSecurityQuestion(){

        if ( usernameSubmitted ){

            securityQuestionLabel.setVisible(true);
            securityQuestionLabel.setText(controller.getAttemptingUser().getRecoveryQuestion().getQuestion());

        }

        securityQuestionLabel.setFontScale(2f);
        securityQuestionLabel.setPosition( Gdx.graphics.getWidth()/4f,  4 * Gdx.graphics.getHeight()/6f );


        stage.addActor(securityQuestionLabel);


    }

    private void showFields(){

        usernameField.setVisible(!usernameSubmitted);
        answerField.setVisible(usernameSubmitted);
        newPasswordField.setVisible(usernameSubmitted);

        usernameField.setWidth(Gdx.graphics.getWidth()/4f * fadeInTimer);
        answerField.setWidth(Gdx.graphics.getWidth()/4f * fadeInTimer);
        newPasswordField.setWidth(Gdx.graphics.getWidth()/4f * fadeInTimer);


        usernameField.setPosition( 3 * Gdx.graphics.getWidth()/8f,  4 * Gdx.graphics.getHeight()/6f - 20);
        answerField.setPosition( 3 * Gdx.graphics.getWidth()/8f,  3 * Gdx.graphics.getHeight()/6f - 20);
        newPasswordField.setPosition( 3 * Gdx.graphics.getWidth()/8f,  2 * Gdx.graphics.getHeight()/6f - 20);


        stage.addActor(usernameField);
        stage.addActor(answerField);
        stage.addActor(newPasswordField);

    }

    private void showButtons(){

        submitButton.setWidth(3*Gdx.graphics.getWidth()/16f-20);
        backButton.setWidth(3*Gdx.graphics.getWidth()/16f-20);
        randomPasswordButton.setSize(120, newPasswordField.getHeight());


        submitButton.setColor(submitButton.getColor().r,submitButton.getColor().g,submitButton.getColor().b,fadeInTimer);
        backButton.setColor(backButton.getColor().r,backButton.getColor().g,backButton.getColor().b,fadeInTimer);
        randomPasswordButton.setColor(randomPasswordButton.getColor().r,randomPasswordButton.getColor().g,randomPasswordButton.getColor().b,fadeInTimer);


        backButton.setPosition(

                Gdx.graphics.getWidth()/4f,
                Gdx.graphics.getHeight()/6f - 20

        );

        submitButton.setPosition(

                7 * Gdx.graphics.getWidth()/16f + 40,
                Gdx.graphics.getHeight()/6f - 20

        );

        randomPasswordButton.setPosition(Gdx.graphics.getWidth()/4f + 3 * Gdx.graphics.getWidth()/8f + 30,  2 * Gdx.graphics.getHeight()/6f - 20);


        stage.addActor(submitButton);
        stage.addActor(backButton);
        stage.addActor(randomPasswordButton);

    }



    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);


    }

    @Override
    public void render(float delta) {

        if ( !errorLabel.getText().isEmpty()){

            errorTimer -= delta;

            if ( errorTimer <= 0 ){
                errorLabel.setText("");
                errorTimer = 5;
            }

        }

        if ( fadeInTimer < 1f ){
            fadeInTimer += delta;
        }
        else{
            fadeInTimer = 1f;
        }

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


        showMenuTitle();
        showLabels();
        showFields();
        showErrorMessage();
        showButtons();
        showSecurityQuestion();



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

        submitButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();

                if ( !usernameSubmitted ){
                    controller.submitUsername();
                }
                else{
                    controller.attemptToChangePassword();
                }

            }

        });

        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.exitMenu();

            }

        });

        randomPasswordButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.setRandomPassword();

            }

        });

    }

    public void setErrorLabel(String message) {
        this.errorLabel.setText(message);
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getAnswerField() {
        return answerField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public void setUsernameSubmitted(boolean usernameSubmitted) {
        this.usernameSubmitted = usernameSubmitted;
    }

    public void setErrorTimer(float errorTimer) {
        this.errorTimer = errorTimer;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }


}
