package org.example.common.models;

import org.example.common.models.Map.NPCMap;
import org.example.common.models.NPCs.NPC;
import org.example.common.models.Shops.BlackSmith;
import org.example.common.models.Shops.Shop;

import java.util.ArrayList;

public interface Game {
    Time getTime();
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
