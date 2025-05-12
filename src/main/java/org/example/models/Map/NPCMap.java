package org.example.models.Map;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.NPCs.NPC;
import org.example.models.enums.CellType;
import org.example.models.enums.NPCType;

public class NPCMap extends Map{

    public NPCMap() {
        super(16, 28);
        build();
    }

    private void buildStore(StoreBuilding storeBuilding) {
        Cell topLeftCell = storeBuilding.getTopLeftCell();
        int x = topLeftCell.getPosition().getX(), y = topLeftCell.getPosition().getY();
        for (int i = x; i < x + 4; i++) {
            for (int j = y; j < y + 4; j++) {
                cells[i][j].setType(CellType.Building);
                cells[i][j].setBuilding(storeBuilding);
            }
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
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getAbigail(), cells[9][1]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getSebastian(), cells[9][6]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getHarvey(), cells[9][12]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getLia(), cells[9][18]));
        buildNPCHouse(new NPCHouse(App.getCurrentGame().getRobbin(), cells[9][23]));

        buildStore(new StoreBuilding(App.getCurrentGame().getBlacksmith(), cells[0][0]));
        buildStore(new StoreBuilding(App.getCurrentGame().getJojaMart(), cells[0][4]));
        buildStore(new StoreBuilding(App.getCurrentGame().getPierreGeneralStore(), cells[0][8]));
        buildStore(new StoreBuilding(App.getCurrentGame().getCarpenterShop(), cells[0][12]));
        buildStore(new StoreBuilding(App.getCurrentGame().getFishShop(), cells[0][16]));
        buildStore(new StoreBuilding(App.getCurrentGame().getMarnieRanch(), cells[0][20]));
        buildStore(new StoreBuilding(App.getCurrentGame().getStardropSaloon(), cells[0][24]));


    }
}
