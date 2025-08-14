package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.model.GameAssetManager;
import org.example.client.view.AppMenu;
import org.example.common.models.Message;

import java.util.HashMap;
import java.util.Scanner;

public class AvatarMenuView extends AppMenu {

    private final Stage stage;
    private final Table avatarGrid;
    private final Image currentAvatar;
    private final TextButton backButton;

    public AvatarMenuView() {
        avatarGrid = new Table();
        stage = new Stage(new ScreenViewport());
        currentAvatar = new Image(
                GameAssetManager.getGameAssetManager().getAvatarTexture(ClientApp.getLoggedInUser().getAvatarId())
        );
        backButton = new TextButton("Back", skin);

        setListeners();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);
        Label menuTitle = new Label("Avatar Menu", skin);
        menuTitle.setFontScale(3f);
        menuTitle.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);

        Table table = new Table();
        table.setFillParent(true);
        currentAvatar.setScaling(Scaling.fit);

        menuTitle.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);

        Table previewAvatar = new Table();
        Label label = new Label("Your avatar:", skin);
        label.setFontScale(1.5f);
        previewAvatar.add(label).padBottom(15).row();
        previewAvatar.add(currentAvatar).size(300);
        Table options = new Table();
        label = new Label("Choose an avatar:", skin);
        label.setFontScale(1.5f);
        options.add(label).padBottom(15).row();
        options.add(avatarGrid).width(400);
        Table contentTable = new Table();
        contentTable.add(options).padRight(100);
        contentTable.add(previewAvatar).padLeft(100);

        table.add(contentTable).colspan(2).padTop(200).expand().fill().row();
        table.add(backButton).colspan(2).pad(30).row();

        stage.addActor(menuTitle);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
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
        // avatar grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Texture avatarTexture = GameAssetManager.getGameAssetManager().getAvatarTexture(i * 3 + j);
                ImageButton avatarImage = new ImageButton(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
                int finalI = i;
                int finalJ = j;
                avatarImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playClickSound();
                        ClientApp.getLoggedInUser().setAvatarId(finalI * 3 + finalJ);
                        ClientApp.updateFile(ClientApp.getLoggedInUser());
                        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                            put("avatarId", finalI * 3 + finalJ);
                        }}, Message.Type.update_avatar));
                        currentAvatar.setDrawable(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
                    }
                });
                avatarGrid.add(avatarImage).size(200, 200);
            }
            avatarGrid.row();
        }
        // Text Buttons
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                Main.getMain().getScreen().dispose();
                ClientApp.setCurrentMenu(new ProfileMenuView());
                Main.getMain().setScreen(ClientApp.getCurrentMenu());
            }
        });
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }
}
