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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.OtherPlayerController;
import org.example.client.controller.OutsideWorldController;
import org.example.client.controller.ToolGraphicalController;
import org.example.client.model.ClientApp;
import org.example.client.controller.OutsidePlayerController;
import org.example.common.models.Direction;
import org.example.server.models.Cell;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Position;
import org.example.server.models.enums.SFX;

import java.util.ArrayList;
import java.util.Scanner;

public class OutsideView extends AppMenu {


    private final HUDView hudView;
    private final Stage stage;

    private final OutsidePlayerController playerController = new OutsidePlayerController(this);
    private final OutsideWorldController worldController = new OutsideWorldController(this);
    private final ToolGraphicalController toolController = new ToolGraphicalController(this);
    private Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

    public static Position getGraphicalPositionInNPCMap(int i, int j) {
        int k = 15;
        return new Position(j * 40 + 20, (k - i) * 40 + 30);
    }

    public static Position getGraphicalPositionInFarmMap(int i, int j) {
        int k = 54;
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

    /// ---> Receives the x,y and gives the corresponding cells indices in a NPCMap
    public static Position getIndicesInNPCMap(float x, float y) {
        int k = 15;
        return new Position(k - (int) Math.floor(y / 40), (int) Math.floor(x / 40));
    }

    /// ---> Receives the x,y and gives the corresponding cells indices in a FarmMap
    public static Position getIndicesInFarmMap(float x, float y) {
        int k = 54;
        return new Position(k - (int) Math.floor(y / 40), (int) Math.floor(x / 40));
    }

    public void displayThorAnimation(int i, int j){

        SFX.THOR.play();
        worldController.setThor(1f,i,j);

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        camera.position.set(getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getX(),
                getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getY(), 0);

        playerController.setCamera(camera);
        toolController.setCamera(camera);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap().print(tileSize);

        playerController.update();
        toolController.update();
        toolController.render();
        ClientApp.getCurrentGame().updateOtherPlayers();
        if (ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap)
            ClientApp.getCurrentGame().renderOtherPlayers();
        camera.update();
        worldController.update(delta);

        hudView.displayHUD(delta);
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();



        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            System.out.println("World coordinates: " + touchPos.x + ", " + touchPos.y);

            int i = OutsideView.getIndices(touchPos.x, touchPos.y).getX(),
                    j = OutsideView.getIndices(touchPos.x, touchPos.y).getY();
            Cell cell = ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap().getCell(i, j);
            if (cell != null && cell.getObject() instanceof NPC npc) {
                System.out.println(npc.getType().getName() + " WAS CLICKED"); // TODO parsa inja click shode ro in NPC
            }
        }
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
