package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.SecurityQuestionMenuController;
import org.example.models.App;
import org.example.models.User;
import org.example.models.enums.Questions;
import org.example.view.AppMenu;
import org.w3c.dom.Text;

import java.util.Scanner;

public class SecurityQuestionMenuView extends AppMenu {

    private final SecurityQuestionMenuController controller;
    private Stage stage;

    private final User user;

    private final TextButton submitButton;
    private final TextButton cancelButton;

    private final Label descriptionLabel;

    private final TextField answerTextField;

    private final SelectBox<String> securityQuestionsBox;

    private float fadeInTimer = 0f;



    public SecurityQuestionMenuView(User currentUser) {

        controller = new SecurityQuestionMenuController(this);
        user = currentUser;

        submitButton = new TextButton("Submit", skin);
        cancelButton = new TextButton("Cancel", skin);
        descriptionLabel = new Label("Choose one of the questions \n and type down your answer", skin);

        answerTextField = new TextField("", skin);

        securityQuestionsBox = new SelectBox<>(skin);
        Array<String> questions = new Array<>();
        for (Questions question : Questions.values()) {
            questions.add(question.getQuestion());
        }
        securityQuestionsBox.setItems(questions);


        setListeners();

    }

    private void showMenuDescription(){

        descriptionLabel.setFontScale(2f);
        descriptionLabel.setPosition(Gdx.graphics.getWidth()/8f * fadeInTimer, 5*Gdx.graphics.getHeight()/6f);
        stage.addActor(descriptionLabel);

    }

    private void showSecurityQuestionsBox(){

        securityQuestionsBox.setPosition(
                ( Gdx.graphics.getWidth() - securityQuestionsBox.getWidth() ) / 2,
                Gdx.graphics.getHeight()/2f
        );

        stage.addActor(securityQuestionsBox);

        answerTextField.setPosition(100,800);
        stage.addActor(answerTextField);

    }

    private void showButtons(){
        submitButton.setPosition(0,0);
        cancelButton.setPosition(0,Gdx.graphics.getHeight()/2f);
        stage.addActor(submitButton);
        stage.addActor(cancelButton);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);


    }

    @Override
    public void render(float delta) {

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

        showMenuDescription();
        showSecurityQuestionsBox();
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

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.submitSecurityQuestion();

            }
        });

    }

    public SelectBox<String> getSecurityQuestionsBox() {
        return securityQuestionsBox;
    }

    public TextField getAnswerTextField() {
        return answerTextField;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }


}
