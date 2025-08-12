package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.*;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.model.ClientApp;
import org.example.client.view.shopview.BuildMenuView;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.AnimalProperty.Barn;
import org.example.server.models.Artisan;
import org.example.server.models.Cell;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Position;
import org.example.server.models.enums.ArtisanTypes;
import org.example.server.models.enums.SFX;
import org.example.server.models.enums.items.AnimalType;
import org.example.server.models.enums.items.BuildingType;

import java.util.ArrayList;
import java.util.Scanner;

public class OutsideView extends AppMenu {


    private final HUDView hudView;
    private final Stage stage;

    private final OutsidePlayerController playerController = new OutsidePlayerController(this);
    private final OutsideWorldController outsideWorldController = new OutsideWorldController(this);
    private final ToolGraphicalController toolController = new ToolGraphicalController(this);
    private final WorldController worldController = new WorldController(this);
    private final ArrayList<AnimalController> animalControllers = new ArrayList<>();
    private final ArrayList<NpcController> npcControllers = new ArrayList<>();

    private Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    private final int tileSize = 40;

    public OutsideView() {

        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);

        camera.position.set(getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getX(),
                getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getY(), 0);

        playerController.setCamera(camera);
        toolController.setCamera(camera);
        worldController.setCamera(camera);
        ResultController.setCamera(camera);
        npcControllers.add(new NpcController(ClientApp.getCurrentGame().getAbigail()));
        npcControllers.add(new NpcController(ClientApp.getCurrentGame().getHarvey()));
        npcControllers.add(new NpcController(ClientApp.getCurrentGame().getRobbin()));
        npcControllers.add(new NpcController(ClientApp.getCurrentGame().getSebastian()));
        npcControllers.add(new NpcController(ClientApp.getCurrentGame().getLia()));

        for (NpcController npcController : npcControllers) {
            npcController.setCamera(camera);
        }

        for (Animal animal : ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap().getAnimals())
            animalControllers.add(new AnimalController(animal, this, camera));

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
        outsideWorldController.setThor(1f,i,j);

    }

    public void displayCrowAttack(int i, int j){

        outsideWorldController.setCrowAttack(3.35f,i,j);

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();



        worldController.updateAndRender();
        playerController.update();
        toolController.update();
        toolController.render();
        ResultController.render();
        PopUpController.renderInfoWindows();
        PopUpController.renderPopUps();
        ClientApp.getCurrentGame().updateOtherPlayers();

        if (ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap)
            ClientApp.getCurrentGame().renderOtherPlayers();

        for (AnimalController animalController : animalControllers) {
            animalController.update();
            animalController.render();
        }
        for (NpcController npcController : npcControllers) {
            npcController.update();
            if (ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap)
                npcController.render();
        }
        animalControllers.removeIf(AnimalController::sold);

        camera.update();
        outsideWorldController.update(delta);

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

    public OutsidePlayerController getPlayerController() {
        return playerController;
    }

    public OutsideWorldController getOutsideWorldController() {
        return outsideWorldController;
    }

    public ToolGraphicalController getToolController() {
        return toolController;
    }

    public WorldController getWorldController() {
        return worldController;
    }
}
