package org.example.common.models;

import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Shops.BlackSmith;
import org.example.server.models.Shops.Shop;

import java.util.ArrayList;

public interface Game {
    void passAnHour();
    void newDay();
    void newSeason();
    NPCMap getNpcMap();
    ArrayList<NPC> getNPCs();
    NPC getAbigail();
    NPC getSebastian();
    NPC getHarvey();
    NPC getLia();
    NPC getRobbin();
    BlackSmith getBlacksmith();
    Shop getJojaMart();
    Shop getPierreGeneralStore();
    Shop getCarpenterShop();
    Shop getFishShop();
    Shop getMarnieRanch();
    Shop getStardropSaloon();
}
