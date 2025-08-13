package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.menus.SecurityQuestionMenuController;
import org.example.common.models.User;
import org.example.common.models.Questions;
import org.example.client.view.AppMenu;

import java.util.Scanner;

public class SecurityQuestionMenuView extends AppMenu {

    private final SecurityQuestionMenuController controller;
    private final User user;
    private final TextButton submitButton;
    private final TextButton cancelButton;
    private final Label descriptionLabel;
    private final Label errorLabel;
    private final TextField answerTextField;
    private final SelectBox<String> securityQuestionsBox;
    private Stage stage;
    private float fadeInTimer = 0f;
    private float errorTimer = 5;

    private boolean errorPhase = false;


    public SecurityQuestionMenuView(User currentUser) {

        controller = new SecurityQuestionMenuController(this);
        user = currentUser;

        submitButton = new TextButton("Submit", skin);
        cancelButton = new TextButton("Cancel", skin);

        descriptionLabel = new Label("Choose one of the questions \n and type down your answer", skin);
        errorLabel = new Label("Please enter a valid answer", skin);

        answerTextField = new TextField("", skin);

        securityQuestionsBox = new SelectBox<>(skin);
        Array<String> questions = new Array<>();
        for (Questions question : Questions.values()) {
            questions.add(question.getQuestion());
        }
        securityQuestionsBox.setItems(questions);


        setListeners();

    }

    private void showMenuDescription() {

        descriptionLabel.setFontScale(2f);
        descriptionLabel.setPosition(Gdx.graphics.getWidth() / 8f * fadeInTimer, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(descriptionLabel);

    }

    private void showSecurityQuestionsBox() {

        securityQuestionsBox.setWidth(3 * Gdx.graphics.getWidth() / 8f * fadeInTimer);
        answerTextField.setWidth(3 * Gdx.graphics.getWidth() / 8f * fadeInTimer);

        securityQuestionsBox.setPosition(
                (Gdx.graphics.getWidth() - securityQuestionsBox.getWidth()) / 2,
                2 * Gdx.graphics.getHeight() / 3f - 100
        );
        answerTextField.setPosition(
                (Gdx.graphics.getWidth() - answerTextField.getWidth()) / 2,
                2 * Gdx.graphics.getHeight() / 3f - securityQuestionsBox.getHeight() - 50 - 100
        );


        stage.addActor(securityQuestionsBox);
        stage.addActor(answerTextField);

    }

    private void showButtons() {

        submitButton.setWidth(Gdx.graphics.getWidth() / 8f);
        cancelButton.setWidth(Gdx.graphics.getWidth() / 8f);

        submitButton.setColor(submitButton.getColor().r, submitButton.getColor().g, submitButton.getColor().b, fadeInTimer);
        cancelButton.setColor(cancelButton.getColor().r, cancelButton.getColor().g, cancelButton.getColor().b, fadeInTimer);


        submitButton.setPosition(
                5 * Gdx.graphics.getWidth() / 16f,
                answerTextField.getY() - submitButton.getHeight() - 50
        );
        cancelButton.setPosition(
                5 * Gdx.graphics.getWidth() / 16f + Gdx.graphics.getWidth() / 4f,
                answerTextField.getY() - submitButton.getHeight() - 50
        );
        stage.addActor(submitButton);
        stage.addActor(cancelButton);

    }

    private void showError() {

        errorLabel.setPosition(

                (Gdx.graphics.getWidth() - errorLabel.getWidth()) / 2f,
                2 * Gdx.graphics.getHeight() / 3f

        );
        errorLabel.setColor(1, 0.31f, 0, Math.min(errorTimer / 5, errorPhase ? 1 : 0));
        stage.addActor(errorLabel);

    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);


    }

    @Override
    public void render(float delta) {

        if (errorPhase) {

            errorTimer -= delta;

            if (errorTimer <= 0) {
                errorPhase = false;
                errorTimer = 5;
            }

        }

        if (fadeInTimer < 1f) {
            fadeInTimer += delta;
        } else {
            fadeInTimer = 1f;
        }

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuDescription();
        showSecurityQuestionsBox();
        showButtons();
        showError();

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
                controller.submitSecurityQuestion();

            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.exitMenu();

            }
        });

    }

    public void setErrorLabel(String message) {
        this.errorLabel.setText(message);
    }

    public SelectBox<String> getSecurityQuestionsBox() {
        return securityQuestionsBox;
    }

    public TextField getAnswerTextField() {
        return answerTextField;
    }

    public boolean isErrorPhase() {
        return errorPhase;
    }

    public void setErrorPhase(boolean errorPhase) {
        this.errorPhase = errorPhase;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }


}
