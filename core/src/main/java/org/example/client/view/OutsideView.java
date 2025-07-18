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
import org.example.server.models.Map.GreenHouse;
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

    public OutsideView() {

        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);


        camera = new OrthographicCamera(1920, 1080);

//        int x = App.getCurrentGame().getCurrentPlayer().getPosition().getX(),
//                y = App.getCurrentGame().getCurrentPlayer().getPosition().getY();
        int x = 40, y = 40;
        camera.position.set(40 * x + 20, 40 * y + 30, 0);

        playerController.setCamera(camera);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        Main.getBatch().begin();
        Main.getBatch().setProjectionMatrix(camera.combined);


        Cell[][] map = App.getCurrentGame().getCurrentPlayer().getFarmMap().getCells();

        for (int i = 54; i >= 0; i--) {
            for (int j = 0; j < 75; j++) {
                int y = (54 - i) * 40;
                int x = j * 40;
                Texture texture = map[i][j].getTexture();
                if (map[i][j].getType() == CellType.Building &&
                        i < map.length && map[i + 1][j].getType() != CellType.Building &&
                        j < map[i].length && map[i][j + 1].getType() != CellType.Building) {
                    if (map[i][j].getBuilding() instanceof GreenHouse) {
                        Main.getBatch().draw(GameAssetManager.getGameAssetManager().getGreenHouseTexture(),
                                x - 240, y, 280, 320);
                    }
                }
                if (texture == null) continue;
                Main.getBatch().draw(texture, x, y, 40, 40);

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
