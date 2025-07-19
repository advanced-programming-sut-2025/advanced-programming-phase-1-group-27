package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.server.controller.GameMenuController;
import org.example.server.controller.OutsidePlayerController;
import org.example.server.models.App;
import org.example.server.models.Cell;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.GreenHouse;
import org.example.server.models.Position;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.Plants.Crop;
import org.example.server.models.enums.Plants.CropType;
import org.example.server.models.enums.items.MineralType;

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
    public static Position getGraphicalPosition(int i, int j) { // GIVES THE LOW_LEFT CORNER
        return new Position(j * 40 + 20, (54 - i) * 40 + 30);
    }

//    /// ---> Gets the Player Position and gives his cell <---
//    public static Position getIndices(Position pos) {
//        return getIndices(pos.getX(), pos.getY());
//    }

    /// ---> Gets the Player Position and gives his cell <---
    public static Position getIndices(float x, float y) {
        return new Position(54 - (int) (y / 40), (int) (x / 40));
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);


        camera = new OrthographicCamera(1920, 1080);

        camera.position.set(getGraphicalPosition(App.getCurrentGame().getCurrentPlayer().getPosition()).getX(),
                getGraphicalPosition(App.getCurrentGame().getCurrentPlayer().getPosition()).getY(), 0);

        playerController.setCamera(camera);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        Main.getBatch().begin();
        Main.getBatch().setProjectionMatrix(camera.combined);


        Cell[][] map = App.getCurrentGame().getCurrentPlayer().getCurrentMap().getCells();

        for (int i = map.length - 1; i >= 0; i--) {
            for (int j = 0; j < map[i].length; j++) {
                int y = (map.length - 1 - i) * tileSize;
                int x = j * tileSize;
                Texture texture = map[i][j].getTexture();

                if (texture == null) continue;
                Main.getBatch().draw(texture, x, y, tileSize, tileSize);

                if (map[i][j].getObject() instanceof Crop crop) {
                    texture = ((CropType) crop.getType()).getTexture();
                    if (texture != null)
                        Main.getBatch().draw(texture, x + 4, y + 4, 32, 32);
                }
                if (map[i][j].getObject() instanceof MineralType mineral) {
                    texture = mineral.getTexture();
                    if (texture != null)
                        Main.getBatch().draw(texture, x + 4, y + 4, 32, 32);
                }
            }
        }
        if (App.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof FarmMap farmMap) {
            Position position = farmMap.getGreenHouse().getTopLeftCell().getPosition();
            int x = OutsideView.getGraphicalPosition(position).getX() - 20,
                    y = OutsideView.getGraphicalPosition(position).getY() - 30;
            Main.getBatch().draw(GameAssetManager.getGameAssetManager().getGreenHouseTexture(),
                    x, y - 240, 320, 280);
        }


        playerController.update();
        camera.update();
        Main.getBatch().end();

        hudView.render(delta);

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
