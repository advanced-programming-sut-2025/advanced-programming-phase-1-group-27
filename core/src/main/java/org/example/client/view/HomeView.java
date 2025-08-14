package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.HomeGraphicalController;
import org.example.client.controller.HomePlayerController;
import org.example.client.controller.PopUpController;
import org.example.client.controller.ResultController;
import org.example.client.model.ClientApp;
import org.example.client.model.GameAssetManager;
import org.example.common.models.Player;

import java.util.Scanner;

public class HomeView extends AppMenu {

    private final HUDView hudView;

    private final HomeGraphicalController homeGraphicalController;
    private final HomePlayerController playerController;

    private final Stage stage;

    private final TextButton advanceTimeButton;


    private final Sprite homeSprite = new Sprite(GameAssetManager.getGameAssetManager().getHomeTexture());
    private final Sprite fridgeSprite = new Sprite(GameAssetManager.getGameAssetManager().getFridgeTexture());


    public HomeView() {

        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);
        homeGraphicalController = new HomeGraphicalController(this);
        playerController = new HomePlayerController(this);
        advanceTimeButton = new TextButton("advance", skin);

    }

    private void preProcess() {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        player.setCurrentMap(player.getFarmMap());
        player.setCurrentCell(player.getFarmMap().getCell(8, 71));
    }

    @Override
    public void show() {
        Main.getBatch().setProjectionMatrix(
                new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        );
        preProcess();

        System.out.println("SALAM");

        Gdx.input.setInputProcessor(stage);

//        stage.addActor(advanceTimeButton);

        setListeners();

        homeSprite.setCenter(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        homeSprite.setScale(0.2f);

        fridgeSprite.setScale(0.7f);
        fridgeSprite.setPosition(Gdx.graphics.getWidth() / 2f + 75, Gdx.graphics.getHeight() / 2f + 20);


    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);


        Main.getBatch().begin();
        homeGraphicalController.update();

        homeSprite.draw(Main.getBatch());
        playerController.update();
        hudView.displayHUD(delta);
        ResultController.render();
        PopUpController.renderPopUps();
        PopUpController.renderInfoWindows();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        ClientApp.getCurrentGame().updateOtherPlayers();

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

    public HUDView getHudView() {
        return hudView;
    }

    private void setListeners() {

        advanceTimeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                ClientApp.getCurrentGame().getTime().cheatAdvanceTime(1);
            }
        });

    }


    public void executeCommands(Scanner scanner) {
//
    }

    public HomePlayerController getPlayerController() {
        return playerController;
    }
}
