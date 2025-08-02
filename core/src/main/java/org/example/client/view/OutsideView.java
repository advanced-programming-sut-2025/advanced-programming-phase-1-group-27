package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.OtherPlayerController;
import org.example.client.model.ClientApp;
import org.example.client.controller.OutsidePlayerController;
import org.example.common.models.Direction;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Position;

import java.util.ArrayList;
import java.util.Scanner;

public class OutsideView extends AppMenu {


    private final HUDView hudView;
    private final Stage stage;

    private final OutsidePlayerController playerController = new OutsidePlayerController(this);
    private Camera camera;

    private final int tileSize = 40;

    public OutsideView() {

        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);

    }

    /// ---> Gives the Player Position <---
    public static Position getGraphicalPosition(Position pos) {
        return getGraphicalPosition(pos.getX(), pos.getY());
    }

    /// ---> Gives the Player Position <---
    public static Position getGraphicalPosition(int i, int j) {
        int k = ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap? 15: 54;
        return new Position(j * 40 + 20, (k - i) * 40 + 30);
    }

//    /// ---> Gets the Player Position and gives his cell <---
//    public static Position getIndices(Position pos) {
//        return getIndices(pos.getX(), pos.getY());
//    }

    /// ---> Gets the Player Position and gives his cell <---
    public static Position getIndices(float x, float y) {
        int k = ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap? 15: 54;
        return new Position(k - (int) Math.floor(y / 40), (int) Math.floor(x / 40));
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);


        camera = new OrthographicCamera(1920, 1080);

        camera.position.set(getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getX(),
                getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getY(), 0);

        playerController.setCamera(camera);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();


        ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap().print(tileSize);

        playerController.update();
        ClientApp.getCurrentGame().updateOtherPlayers();
        if (ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap)
            ClientApp.getCurrentGame().renderOtherPlayers();
        camera.update();
        hudView.displayHUD(delta);
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

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

    public HUDView getHudView() {
        return hudView;
    }

}
