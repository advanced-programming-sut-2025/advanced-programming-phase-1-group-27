package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.HostMenuController;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;
import org.example.server.models.GameAssetManager;

import java.util.Scanner;

public class HostMenuView extends AppMenu{
    private final HostMenuController controller;
    private Stage stage;
    private final Label menuTitleLabel;

    private final TextField nameTextField;
    private final TextField passwordField;

    private final CheckBox isPublicCheckBox;
    private final CheckBox isVisibleCheckBox;

    private final TextButton createButton;
    private final TextButton backButton;

    private final GraphicalResult errorLabel;

    public HostMenuView() {
        this.controller = new HostMenuController(this);

        this.menuTitleLabel = new  Label("Host Menu View", skin);
        this.nameTextField = new  TextField("", skin);
        nameTextField.setMessageText("Name");
        this.passwordField = new  TextField("", skin);
        passwordField.setMessageText("Password");
        this.isPublicCheckBox = new CheckBox("Public", skin);
        this.isVisibleCheckBox = new CheckBox("Visible", skin);
        this.createButton = new TextButton("Create", skin);
        this.backButton = new TextButton("Back", skin);

        this.errorLabel = new GraphicalResult();

        setListeners();
    }

    private void showErrorMessage() {
        errorLabel.setPosition(Gdx.graphics.getWidth() / 9f, 6 * Gdx.graphics.getHeight() / 7f - Gdx.graphics.getHeight() / 9f);
        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
        stage.addActor(errorLabel.getMessage());
    }

    private void showMenuTitle() {
        menuTitleLabel.setFontScale(3f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitleLabel);
    }

    private void showInputFields(){
        passwordField.setWidth(Gdx.graphics.getWidth()/2f);
        passwordField.setPosition(Gdx.graphics.getWidth()/2f - 7 * Gdx.graphics.getWidth()/20f,7*Gdx.graphics.getHeight()/15f-20);
        stage.addActor(passwordField);

        nameTextField.setWidth(Gdx.graphics.getWidth()/2f);
        nameTextField.setPosition(Gdx.graphics.getWidth()/2f - 7 * Gdx.graphics.getWidth()/20f,8*Gdx.graphics.getHeight()/15f-20);
        stage.addActor(nameTextField);
    }

    private void showCheckBoxes(){
        Table checkBoxes = new Table();
        isVisibleCheckBox.setChecked(true);
        isPublicCheckBox.setChecked(true);

        isVisibleCheckBox.setWidth(Gdx.graphics.getWidth()/2f);
        isPublicCheckBox.setWidth(Gdx.graphics.getWidth()/2f);

        isVisibleCheckBox.setPosition(Gdx.graphics.getWidth()/2f - 2 * Gdx.graphics.getWidth()/20f,8*Gdx.graphics.getHeight()/15f-20);
        isPublicCheckBox.setPosition(Gdx.graphics.getWidth()/2f - 2 * Gdx.graphics.getWidth()/20f,7*Gdx.graphics.getHeight()/15f-20);

        checkBoxes.add(isPublicCheckBox);
        checkBoxes.add(isVisibleCheckBox);

        stage.addActor(checkBoxes);
    }

    private void showButtons(){
        backButton.setWidth(Gdx.graphics.getWidth()/4f);
        createButton.setWidth(Gdx.graphics.getWidth()/4f);

        backButton.setPosition(5 * Gdx.graphics.getWidth()/24f,
                2*Gdx.graphics.getHeight()/12f);
        createButton.setPosition(13 * Gdx.graphics.getWidth()/24f,
                2*Gdx.graphics.getHeight()/12f);

        stage.addActor(backButton);
        stage.addActor(createButton);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);
        showErrorMessage();
    }

    @Override
    public void render(float delta) {
        errorLabel.update(delta);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuTitle();
        showInputFields();
        showCheckBoxes();
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

    @Override
    public void executeCommands(Scanner scanner) {

    }

    private void setListeners() {
        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
//                errorLabel.set();
            }
        });


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exitMenu();
            }
        });
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public CheckBox getIsPublicCheckBox() {
        return isPublicCheckBox;
    }

    public CheckBox getIsVisibleCheckBox() {
        return isVisibleCheckBox;
    }
}
