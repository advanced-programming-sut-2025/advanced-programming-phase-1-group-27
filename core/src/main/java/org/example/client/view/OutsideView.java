package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.example.client.Main;
import org.example.server.models.App;
import org.example.server.models.Cell;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Map.GreenHouse;
import org.example.server.models.enums.CellType;

import java.util.Scanner;

public class OutsideView extends AppMenu {
    @Override
    public void executeCommands(Scanner scanner) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Main.getBatch().begin();
        Cell[][] map = App.getCurrentGame().getCurrentPlayer().getFarmMap().getCells();

        int i = 0, j = 0;
        for (int x = 0; x < Gdx.graphics.getWidth(); x += 40, i++) {
            for (int y = Gdx.graphics.getHeight(); y >= 0; y -= 40, j++) {
                Texture texture = map[i][j].getTexture();
                if (map[i][j].getType() == CellType.Building &&
                        i < map.length && map[i + 1][j].getType() != CellType.Building &&
                        j < map[i].length && map[i][j + 1].getType() != CellType.Building) {
                    System.out.println("lklk");
                    if (map[i][j].getBuilding() instanceof GreenHouse) {
                        Main.getBatch().draw(GameAssetManager.getGameAssetManager().getGreenHouseTexture(),
                        x - 240, y, 280, 320);
                    }
                }
                if (texture == null) continue;
                Main.getBatch().draw(texture, x, y, 40, 40);
            }
            j = 0;
        }

        Main.getBatch().end();

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
