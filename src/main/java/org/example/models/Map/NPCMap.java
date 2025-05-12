package org.example.models.Map;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.enums.CellType;
import org.example.models.enums.NPCType;

public class NPCMap extends Map{

    public NPCMap() {
        super(16, 28);
        build();
    }

    private void buildStore(StoreBuilding storeBuilding, Cell topLeftCell) {
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
        int x = topLeftCell.getPosition().getX(), y = topLeftCell.getPosition().getY();
        for (int i = x; i < x + 4; i++) {
            for (int j = y; j < y + 4; j++) {
                cells[i][j].setType(CellType.Building);
                cells[i][j].setBuilding(npcHouse);
            }
        }
        cells[x + 3][y + 1].setType(CellType.Door);
        npcHouse.setDoor(cells[x + 3][y + 1]);
        npcHouse.getNpc().setStandingCell(cells[x + 4][y + 1]);
    }

    public void setPassageToFarmMap(int i, int j, FarmMap farmMap, int x, int y) {
        cells[i][j].setType(CellType.MapLink);
        cells[i][j].setObject(farmMap.getCell(x, y));
    }

    private void build() {
        buildNPCHouse(new NPCHouse(NPCType.Abigail, cells[9][1]));
        buildNPCHouse(new NPCHouse(NPCType.Sebastian, cells[9][6]));
        buildNPCHouse(new NPCHouse(NPCType.Harvey, cells[9][12]));
        buildNPCHouse(new NPCHouse(NPCType.Lia, cells[9][18]));
        buildNPCHouse(new NPCHouse(NPCType.Robbin, cells[9][23]));



    }
}
