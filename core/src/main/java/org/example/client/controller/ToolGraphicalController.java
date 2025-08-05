package org.example.client.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.GameView;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.server.models.Cell;
import org.example.server.models.Player;
import org.example.server.models.Result;
import org.example.server.models.enums.Plants.SeedType;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Tool;

import java.util.ArrayList;
import java.util.Spliterator;

public class ToolGraphicalController {
    private float x, y;
    private final OutsideView view;
    private Camera camera;

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
                System.out.println("you clicked the cell " + i + " " + j);
                if (cell.getAdjacentCells().contains(player.getCurrentCell())) {
                    Result res = toolType.getTheFuckingTool().use(cell);

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

            if (player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem() instanceof SeedType seedType) {
                Result res = new GameMenuController(new GameView()).plant(seedType, cell);
                handleError(res);
            }
        }
    }

    public void update() {
        handlePlanting();
        handleToolUse();

    }

    public void render() {
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
