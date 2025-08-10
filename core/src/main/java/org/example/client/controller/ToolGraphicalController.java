package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.model.PopUpTexture;
import org.example.client.view.GameView;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.server.models.*;
import org.example.server.models.enums.Plants.Plant;
import org.example.server.models.enums.Plants.SeedType;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.enums.items.ToolType;

import java.util.ArrayList;

import static java.lang.Math.max;

public class ToolGraphicalController {
    private float x, y;
    private final OutsideView view;
    private Camera camera;
    private boolean turnOff = false;

    private final Sprite toolSprite = new Sprite(GameAssetManager.getGameAssetManager().getItemTexture(
            ToolType.BasicPickaxe
    ));

    private Player player = ClientApp.getCurrentGame().getCurrentPlayer();
    private ArrayList<GraphicalResult> errors = new ArrayList<>();

    public ToolGraphicalController(OutsideView view) {
        this.view = view;
    }

    public void setCamera(Camera camera) {
        x = camera.position.x;
        y = camera.position.y;
        this.camera = camera;
    }

    private void handleError(Result result) {
        if (result.success())
            return;
        GraphicalResult error = new GraphicalResult(result.message(), true);
        Vector3 position = new Vector3(Gdx.graphics.getWidth() / 2f - error.getMessage().getWidth() / 2f,
                50, 0);
        camera.unproject(position);
        error.setPosition(position.x, position.y);
        errors.add(error);
    }


    private void handleToolUse() {
        x = camera.position.x;
        y = camera.position.y;
        if (player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem() instanceof ToolType toolType) {
            toolSprite.setRegion(GameAssetManager.getGameAssetManager().getItemTexture(toolType));
            toolSprite.setScale(0.6f);
            toolSprite.setCenter(x + 15, y - 16);

            if (Gdx.input.justTouched()) {
                Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

                camera.unproject(touchPos);

                int i = OutsideView.getIndices(touchPos.x, touchPos.y).getX(),
                        j = OutsideView.getIndices(touchPos.x, touchPos.y).getY();
                Cell cell = player.getCurrentMap().getCell(i, j);
                if (cell == null)
                    return;
                if (cell.getAdjacentCells().contains(player.getCurrentCell())) {
                    Result res = toolType.getTheFuckingTool().use(cell);
                    if (res != null)
                        handleError(res);
                }
            }
        }
    }

    private void handlePlanting() {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(touchPos);

            int i = OutsideView.getIndices(touchPos.x, touchPos.y).getX(),
                    j = OutsideView.getIndices(touchPos.x, touchPos.y).getY();
            Cell cell = player.getCurrentMap().getCell(i, j);

            if (cell != null &&
                    player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem() instanceof SeedType seedType) {
                Result res = new GameMenuController(new GameView()).plant(seedType, cell);
                handleError(res);
            }
        }
    }

    private void handleFertilization() {
        if (!player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem()
                .equals(ShopItems.DeluxeRetainingSoil) &&
                        !player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem()
                                .equals(ShopItems.SpeedGro)) {
            return;
        }
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(touchPos);

            int i = OutsideView.getIndices(touchPos.x, touchPos.y).getX(),
                    j = OutsideView.getIndices(touchPos.x, touchPos.y).getY();
            Cell cell = player.getCurrentMap().getCell(i, j);

            if (cell == null)
                return;
            if (!(cell.getObject() instanceof Plant)) {
                ResultController.addError("There is No Plant Here!");
                return;
            }

            Plant plant = (Plant) cell.getObject();
            if (player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem().equals(
                    ShopItems.SpeedGro)) {
                plant.setTillNextHarvest(max(plant.getTillNextHarvest() - 1, 0));
                ResultController.addSuccess("The selected plant will bear fruit sooner!");
                plant.setFertilized(true);
            }
            if (player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem().equals(
                    ShopItems.DeluxeRetainingSoil)){
                plant.setAlwaysWatered(true);
                ResultController.addSuccess("The selected plant will never need water!");
            }
        }
    }

    public void handleEating() {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {

            Item item = player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem();
            Result result = new GameMenuController(new GameView()).eatFood(item.getName());
            ResultController.addResult(result);
            if (result.success()) {
                PopUpController.addPopUp(new PopUpTexture(
                        item.getTexture(),
                        view.getPlayerController().getX(), view.getPlayerController().getY() + 40,
                        view.getPlayerController().getX(), view.getPlayerController().getY() + 20,
                        1f
                ));
            }
        }
    }

    public void update() {
        handlePlanting();
        handleToolUse();
        handleFertilization();
        handleEating();
    }

    public void turnOff() {
        turnOff = true;
    }

    public void turnOn() {
        turnOff = false;
    }

    public void render() {
        if (turnOff)
            return;

        if (toolSprite.getTexture() != null &&
                player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem() instanceof ToolType toolType)
            toolSprite.draw(Main.getBatch());
        for (GraphicalResult error : errors) {
            error.update(Gdx.graphics.getDeltaTime());
            Vector3 position = new Vector3(Gdx.graphics.getWidth() / 2f - error.getMessage().getWidth() / 2f,
                    50, 0);
            camera.unproject(position);
            error.setPosition(position.x, position.y);
            error.getMessage().draw(Main.getBatch(), 1);
        }
    }
}
