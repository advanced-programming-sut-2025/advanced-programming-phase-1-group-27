package org.example.models.Map;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.NPCs.NPC;
import org.example.models.enums.CellType;
import org.example.models.enums.NPCType;

public class NPCMap extends Map{

    public NPCMap() {
        super(16, 34);
        build();
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
            cells[i][j].setString("\u001B[48;2;59;33;1m" + "\u001B[38;2;138;106;67m" + new String(new char[] {
                    (storeName.length() > (j - y) * 2? storeName.charAt((j - y) * 2): ' '),
                    (storeName.length() > (j - y) * 2 + 1?storeName.charAt((j - y) * 2 + 1): ' ')})
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

        npc.setCurrentPosition(cells[x + 4][y + 1]);
        npc.setHome(npcHouse);
        cells[x + 4][y + 1].setObject(npc);

    }

    public void setPassageToFarmMap(int i, int j, FarmMap farmMap, int x, int y) {
        cells[i][j].setType(CellType.MapLink);
        cells[i][j].setObject(farmMap.getCell(x, y));
    }

    private void build() {
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getAbigail(), cells[9][2]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getSebastian(), cells[9][8]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getHarvey(), cells[9][15]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getLia(), cells[9][22]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getRobbin(), cells[9][28]));

        buildStore(new StoreBuilding(App.getCurrentGame().getBlacksmith(), cells[0][0]), "BlckSmth");
        buildStore(new StoreBuilding(App.getCurrentGame().getJojaMart(), cells[0][5]), "JojaMart");
        buildStore(new StoreBuilding(App.getCurrentGame().getPierreGeneralStore(), cells[0][10]), "Pierre");
        buildStore(new StoreBuilding(App.getCurrentGame().getCarpenterShop(), cells[0][15]), "Carpentr");
        buildStore(new StoreBuilding(App.getCurrentGame().getFishShop(), cells[0][20]), "FishShop");
        buildStore(new StoreBuilding(App.getCurrentGame().getMarnieRanch(), cells[0][25]), "Marnie");
        buildStore(new StoreBuilding(App.getCurrentGame().getStardropSaloon(), cells[0][30]), "Stardrop");


    }
}
