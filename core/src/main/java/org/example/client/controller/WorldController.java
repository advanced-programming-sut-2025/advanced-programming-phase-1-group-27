package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.server.models.BuildingTexture;
import org.example.server.models.Cell;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.Map;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Player;
import org.example.server.models.Position;
import org.example.server.models.enums.Plants.Crop;
import org.example.server.models.enums.Plants.Tree;
import org.example.server.models.enums.items.MineralType;

import java.util.ArrayList;

public class WorldController {
    private int tileSize = 40;
    private Camera camera;
    private final OutsideView view;
    private ArrayList<Label> dialogueLabels = new ArrayList<>();

    public WorldController(OutsideView view) {
        this.view = view;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private TextureRegion createColoredTexture(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }

    public void renderBuilding(int i, int j, int index) {
        int x = OutsideView.getGraphicalPosition(i, j).getX() - 20,
                y = OutsideView.getGraphicalPosition(i, j).getY() - 30;
        TextureRegion textureRegion = GameAssetManager.getGameAssetManager().getCabinTextureRegion(index);
        Main.getBatch().draw(textureRegion, x, y - 160, 164, 224);
    }

    private void renderFarmMap(FarmMap map) {
        Position position = map.getGreenHouse().getTopLeftCell().getPosition();
        int x = OutsideView.getGraphicalPosition(position).getX() - 20,
                y = OutsideView.getGraphicalPosition(position).getY() - 30;
        Main.getBatch().draw(GameAssetManager.getGameAssetManager().getGreenHouseTexture(),
                x, y - 240, 320, 280);

        position = map.getHut().getTopLeftCell().getPosition();
        x = OutsideView.getGraphicalPosition(position).getX() - 20;
        y = OutsideView.getGraphicalPosition(position).getY() - 30;
        Main.getBatch().draw(GameAssetManager.getGameAssetManager().getHutTexture(),
                x, y - 125, 160, 160);
    }

    private void renderNpcMap(NPCMap npcMap) {
        Cell[][] cells = npcMap.getCells();
        int height = npcMap.getHeight(), width = npcMap.getWidth();
        BuildingTexture[] buildingTextures = npcMap.getBuildingTextures();

        for (BuildingTexture bt : buildingTextures) {
            renderBuilding(bt.i, bt.j, bt.textureIndex);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float y = (cells.length - 1 - i) * tileSize;
                float x = j * tileSize;
                if (cells[i][j].getObject() instanceof NPC npc) {
                    Main.getBatch().draw(GameAssetManager.getGameAssetManager().getNpcTexture(npc.getType()),
                            x + 4, y, 32, 64);
                    if (npc.getDialogue() != null) {

                        Main.getBatch().draw(
                                GameAssetManager.getGameAssetManager().getNPCDialogueSign(),
                                x + 32, y + 64, 32, 32
                        );
                        Rectangle bounds = new Rectangle(x + 32, y + 56, 32, 32);

                        if (Gdx.input.justTouched()) {
                            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                            camera.unproject(touchPos);
                            if (bounds.contains(touchPos.x, touchPos.y)) {
                                System.out.println("ASDF dsaFASDFASDF");
                                Label dialogueLabel = new Label(npc.getDialogue(),
                                        GameAssetManager.getGameAssetManager().getSkin());
                                dialogueLabel.setPosition(x + 32, y + 56);
                                dialogueLabels.add(dialogueLabel);
                                //TODO
                                npc.setDialogue(null);
                            }
                        }
                    }
                }
            }
        }
    }

    private void renderMap(Map map) {
        int height = map.getHeight(), width = map.getWidth();
        Cell[][] cells = map.getCells();
        for (int i = -20; i < height + 20; i++) {
            for (int j = -30; j < width + 30; j++) {
                if (i >= 0 && i < height && j >= 0 && j < width) {
                    float y = (height - 1 - i) * tileSize;
                    float x = j * tileSize;
                    Texture texture = cells[i][j].getTexture();

                    if (texture == null) continue;
                    Main.getBatch().draw(texture, x, y, tileSize, tileSize);
                }
                else {
                    float x = j * tileSize;
                    float y = (height - 1 - i) * tileSize;
                    Texture texture = (i * i + j * j + j - 1) % 2 < 2?
                            GameAssetManager.getGameAssetManager().getDarkGrassCellTexture():
                            GameAssetManager.getGameAssetManager().getDarkGrass2CellTexture();
                    Main.getBatch().draw(texture, x, y, tileSize, tileSize);
                }
            }
        }
        Texture texture;
        TextureRegion textureRegion;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                float y = (cells.length - 1 - i) * tileSize;
                float x = j * tileSize;
                texture = cells[i][j].getTexture();

                if (texture == null) continue;

                if (cells[i][j].getObject() instanceof Crop crop) {
                    texture = crop.getTexture();
                    if (texture != null)
                        Main.getBatch().draw(texture, x + 4, y + 4, 32, 32);
                }
                if (cells[i][j].getObject() instanceof MineralType mineral) {
                    texture = mineral.getTexture();
                    if (texture != null)
                        Main.getBatch().draw(texture, x, y, 40, 40);
                }
                if (cells[i][j].getObject() instanceof Tree tree) {
                    textureRegion = tree.getTexture();
                    if (textureRegion != null)
                        Main.getBatch().draw(textureRegion, x - 10, y, 60, 100);
                }
            }
        }
        if (map instanceof NPCMap) {
            renderNpcMap((NPCMap) map);
        } else{
            renderFarmMap((FarmMap) map);
        }
    }

    private void drawLabel(Label label) {
        Color backGroundColor = new Color(0, 0, 0, label.getColor().a * 0.7f);
        Main.getBatch().setColor(backGroundColor);
        Main.getBatch().draw(createColoredTexture(backGroundColor),
                label.getX(),
                label.getY(),
                label.getWidth(),
                label.getHeight());

        // Draw text
        label.draw(Main.getBatch(), 1);

        Main.getBatch().setColor(Color.WHITE);
    }

    private void renderDialogues() {
        for (Label label: dialogueLabels) {

            drawLabel(label);


            label.draw(Main.getBatch(), 1);
            label.setColor(1, 1, 1, 0.996f * label.getColor().a);
        }
        dialogueLabels.removeIf(label -> label.getColor().a <= 0.3f);
    }

    public void update() {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        renderMap(player.getCurrentMap());
        renderDialogues();
    }
}
