package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.menus.HostMenuController;
import org.example.client.model.GameAssetManager;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;

import java.util.Scanner;

public class HostMenuView extends AppMenu {

    private final HostMenuController controller;
    private final Label menuTitleLabel;
    private final Label lobbyNameLabel;
    private final Label lobbyPasswordLabel;
    private final TextField nameTextField;
    private final TextField passwordField;
    private final CheckBox isPublicCheckBox;
    private final CheckBox isVisibleCheckBox;
    private final TextButton createButton;
    private final TextButton backButton;
    private final GraphicalResult errorLabel;
    private Stage stage;

    public HostMenuView() {

        this.controller = new HostMenuController(this);

        this.menuTitleLabel = new Label("Host Menu", skin);
        this.lobbyNameLabel = new Label("Lobby Name:", skin);
        this.lobbyPasswordLabel = new Label("Lobby Password:", skin);
        this.nameTextField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.isPublicCheckBox = new CheckBox("Public", skin);
        this.isVisibleCheckBox = new CheckBox("Visible", skin);
        this.createButton = new TextButton("Create", skin);
        this.backButton = new TextButton("Back", skin);

        this.isVisibleCheckBox.setChecked(true);
        this.isPublicCheckBox.setChecked(false);

        this.errorLabel = new GraphicalResult();

        setListeners();
    }

    private void showErrorMessage() {
        errorLabel.setPosition(Gdx.graphics.getWidth() / 9f, 9 * Gdx.graphics.getHeight() / 12f);
        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
        stage.addActor(errorLabel.getMessage());
    }

    private void showMenuTitle() {

        menuTitleLabel.setFontScale(3f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitleLabel);

    }

    private void showInputFields() {

        passwordField.setWidth(Gdx.graphics.getWidth() / 4f);
        nameTextField.setWidth(Gdx.graphics.getWidth() / 4f);

        nameTextField.setPosition(5 * Gdx.graphics.getWidth() / 16f, 7 * Gdx.graphics.getHeight() / 12f - 20);
        passwordField.setPosition(5 * Gdx.graphics.getWidth() / 16f, 6 * Gdx.graphics.getHeight() / 12f - 20);

        passwordField.setVisible(!isPublicCheckBox.isChecked());

        stage.addActor(nameTextField);
        stage.addActor(passwordField);

    }

    private void showCheckBoxes() {
        isVisibleCheckBox.setPosition(10 * Gdx.graphics.getWidth() / 16f, 7 * Gdx.graphics.getHeight() / 12f - 10);
        isPublicCheckBox.setPosition(10 * Gdx.graphics.getWidth() / 16f, 6 * Gdx.graphics.getHeight() / 12f - 10);

        stage.addActor(isVisibleCheckBox);
        stage.addActor(isPublicCheckBox);
    }

    private void showLabels() {

        lobbyNameLabel.setPosition(Gdx.graphics.getWidth() / 8f, 7 * Gdx.graphics.getHeight() / 12f);
        lobbyPasswordLabel.setPosition(Gdx.graphics.getWidth() / 8f, 6 * Gdx.graphics.getHeight() / 12f);

        lobbyPasswordLabel.setVisible(!isPublicCheckBox.isChecked());

        stage.addActor(lobbyNameLabel);
        stage.addActor(lobbyPasswordLabel);

    }

    private void showButtons() {
        backButton.setWidth(Gdx.graphics.getWidth() / 4f);
        createButton.setWidth(Gdx.graphics.getWidth() / 4f);

        backButton.setPosition(5 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f);
        createButton.setPosition(13 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f);

        stage.addActor(backButton);
        stage.addActor(createButton);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

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
        showLabels();
        showErrorMessage();

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
                errorLabel.set(controller.createGraphicalResult());
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
