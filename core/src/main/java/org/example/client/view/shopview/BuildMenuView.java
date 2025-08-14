package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.client.Main;
import org.example.client.controller.WorldController;
import org.example.client.model.ClientApp;
import org.example.client.model.GameAssetManager;
import org.example.client.view.AppMenu;
import org.example.client.view.OutsideView;
import org.example.common.models.AnimalProperty.Barn;
import org.example.common.models.AnimalProperty.Coop;
import org.example.common.models.*;
import org.example.common.models.Map.FarmMap;
import org.example.common.models.items.BuildingType;
import org.example.common.models.items.products.ProcessedProductType;

import java.util.Scanner;

import static java.lang.Math.floor;

public class BuildMenuView extends AppMenu {
    private final BuildingType buildingType;
    private final Artisan artisan;
    private final Player player = ClientApp.getCurrentGame().getCurrentPlayer();


    public BuildMenuView(BuildingType buildingType) {
        this.buildingType = buildingType;
        artisan = null;
    }    private final float tileSize = 18f,
            constantX = (Gdx.graphics.getWidth() - 75 * tileSize) / 2f,
            constantY = (Gdx.graphics.getHeight() - 55 * tileSize) / 2f;


    public BuildMenuView(Artisan artisan) {
        this.artisan = artisan;
        buildingType = BuildingType.ShippingBin;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    @Override
    public void show() {
        Main.getBatch().setProjectionMatrix(
                new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        );
    }

    private void renderMap() {
        FarmMap map = player.getFarmMap();
        Cell[][] cells = map.getCells();

        float x, y;
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                x = j * tileSize + constantX;
                y = Gdx.graphics.getHeight() - (i + 1) * tileSize - constantY;
                if (cells[i][j].getType() == CellType.Free && cells[i][j].getObject() == null)
                    Main.getBatch().draw(GameAssetManager.getGameAssetManager().getGreenTileCellTexture(),
                            x, y, tileSize, tileSize);
                else
                    Main.getBatch().draw(GameAssetManager.getGameAssetManager().getRedTileCellTexture(),
                            x, y, tileSize, tileSize);
            }
        }
    }

    public void handleSelection() {

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX(), y = Gdx.input.getY();
            int j = (int) floor((x - constantX) / tileSize),
                    i = (int) floor((y - constantY) / tileSize);
            if (player.getFarmMap().freeRectangle(
                    i, j, buildingType.getHeight(), buildingType.getWidth()
            )) {
                if (artisan != null) {
                    Cell cell = player.getFarmMap().getCell(i, j);
                    cell.setObject(artisan);
                    if (artisan.getType() == ArtisanTypes.BeeHouse) {
                        artisan.setFinalProduct(ProcessedProductType.Honey);
                        artisan.setTimeLeft(ProcessedProductType.Honey.getProcessingTime());
                    }
                } else if (buildingType.isBarn())
                    player.getFarmMap().placeBarn(i, j, new Barn(buildingType, player.getFarmMap().getCell(i, j)));
                else if (buildingType.isCoop())
                    player.getFarmMap().placeCoop(i, j, new Coop(buildingType, player.getFarmMap().getCell(i, j)));
                else if (buildingType == BuildingType.ShippingBin) {
                    Cell cell = player.getFarmMap().getCell(i, j);
                    ShippingBin shippingBin = new ShippingBin(cell);
                    cell.setBuilding(shippingBin);
                    player.getFarmMap().addShippingBin(shippingBin);
                    cell.setType(CellType.Building);
                }
                exit();
            }
        }
    }

    public void renderPreview() {
        float x = Gdx.input.getX(), y = Gdx.input.getY();
        int j = (int) floor((x - constantX) / tileSize),
                i = (int) floor((y - constantY) / tileSize);
        x = j * tileSize + constantX;
        y = Gdx.graphics.getHeight() - (i + 1) * tileSize - constantY + tileSize - (buildingType.getHeight() * tileSize);

        TextureRegion textureRegion = WorldController.createColoredTexture(Color.BROWN);

        Main.getBatch().draw(textureRegion, x, y, tileSize * buildingType.getWidth(),
                tileSize * buildingType.getHeight());

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);

        Main.getBatch().begin();
        Main.getBatch().draw(GameAssetManager.getGameAssetManager().getPlayerSocialBackGroundTexture(),
                0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderMap();
        renderPreview();
        Main.getBatch().end();

        handleSelection();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            exit();
        }
    }

    private void exit() {
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new OutsideView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
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


}
