package org.example.common.models.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.client.Main;
import org.example.client.view.OutsideView;
import org.example.common.models.Game;
import org.example.client.model.GameAssetManager;
import org.example.client.model.BuildingTexture;
import org.example.common.models.Cell;
import org.example.common.models.NPCs.NPC;
import org.example.common.models.CellType;

public class NPCMap extends Map {

    private final BuildingTexture[] buildingTextures = new BuildingTexture[] {
            new BuildingTexture(2, 9, 2),
            new BuildingTexture(5, 9, 8),
            new BuildingTexture(2, 9, 15),
            new BuildingTexture(5, 9, 22),
            new BuildingTexture(2, 9, 28),

            new BuildingTexture(0, 0, 0),
            new BuildingTexture(1, 0, 5),
            new BuildingTexture(2, 0, 10),
            new BuildingTexture(3, 0, 15),
            new BuildingTexture(4, 0, 20),
            new BuildingTexture(5, 0, 25),
            new BuildingTexture(6, 0, 30)
    };



    public NPCMap(Game game) {
        super(16, 34);
        build(game);
    }

    private void buildStore(StoreBuilding storeBuilding, String storeName) {
        Cell topLeftCell = storeBuilding.getTopLeftCell();
        int x = topLeftCell.getPosition().getX(), y = topLeftCell.getPosition().getY();
        for (int i = x; i < x + 4; i++) {
            for (int j = y; j < y + 4; j++) {
                cells[i][j].setType(CellType.Building);
                cells[i][j].setBuilding(storeBuilding);
            }
        }
        for (int j = y; j < y + 4; j++) {
            int i = x;
            cells[i][j].setString("\u001B[48;2;59;33;1m" + "\u001B[38;2;138;106;67m" + new String(new char[]{
                    (storeName.length() > (j - y) * 2 ? storeName.charAt((j - y) * 2) : ' '),
                    (storeName.length() > (j - y) * 2 + 1 ? storeName.charAt((j - y) * 2 + 1) : ' ')})
                    + "\u001B[0m");
        }
        cells[x + 3][y + 1].setType(CellType.Door);
        storeBuilding.setDoor(cells[x + 3][y + 1]);
    }

    private void buildNPCHouse(NPCHouse npcHouse) {
        Cell topLeftCell = npcHouse.getTopLeftCell();
        NPC npc = npcHouse.getNpc();
        int x = topLeftCell.getPosition().getX(), y = topLeftCell.getPosition().getY();
        for (int i = x; i < x + 4; i++) {
            for (int j = y; j < y + 4; j++) {
                cells[i][j].setType(CellType.Building);
                cells[i][j].setBuilding(npcHouse);
            }
        }
        cells[x + 3][y + 1].setType(CellType.Door);
        npcHouse.setDoor(cells[x + 3][y + 1]);

//        npc.setCurrentCell(cells[x + 4][y + 1]);
        npc.setHome(npcHouse);
//        cells[x + 4][y + 1].setObject(npc);

    }

    public void setPassageToFarmMap(int i, int j, FarmMap farmMap, int x, int y) {
        cells[i][j].setType(CellType.MapLink);
        cells[i][j].setObject(farmMap.getCell(x, y));
    }

    private void build(Game game) {
        buildNPCHouse(new NPCHouse(game.getAbigail(), cells[9][2]));
        buildNPCHouse(new NPCHouse(game.getSebastian(), cells[9][8]));
        buildNPCHouse(new NPCHouse(game.getHarvey(), cells[9][15]));
        buildNPCHouse(new NPCHouse(game.getLia(), cells[9][22]));
        buildNPCHouse(new NPCHouse(game.getRobbin(), cells[9][28]));

        buildStore(new StoreBuilding(game.getBlacksmith(), cells[0][0]), "BlckSmth");
        buildStore(new StoreBuilding(game.getJojaMart(), cells[0][5]), "JojaMart");
        buildStore(new StoreBuilding(game.getPierreGeneralStore(), cells[0][10]), "Pierre");
        buildStore(new StoreBuilding(game.getCarpenterShop(), cells[0][15]), "Carpentr");
        buildStore(new StoreBuilding(game.getFishShop(), cells[0][20]), "FishShop");
        buildStore(new StoreBuilding(game.getMarnieRanch(), cells[0][25]), "Marnie");
        buildStore(new StoreBuilding(game.getStardropSaloon(), cells[0][30]), "Stardrop");


    }
    
    public void renderBuilding(int i, int j, int index) {
        int x = OutsideView.getGraphicalPosition(i, j).getX() - 20,
                y = OutsideView.getGraphicalPosition(i, j).getY() - 30;
        TextureRegion textureRegion = GameAssetManager.getGameAssetManager().getCabinTextureRegion(index);
        Main.getBatch().draw(textureRegion, x, y - 160, 164, 224);
    }

    public BuildingTexture[] getBuildingTextures() {
        return buildingTextures;
    }
}

