package org.example.common.models;

import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;

import java.util.ArrayList;

public interface Game {
    ArrayList<NPC> getNPCs();
    void passAnHour();
    void newDay();
    void newSeason();
    NPCMap getNpcMap();
}
