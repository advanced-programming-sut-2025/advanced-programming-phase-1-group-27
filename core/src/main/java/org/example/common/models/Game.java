package org.example.common.models;

import org.example.server.models.Map.NPCMap;

public interface Game {
    void passAnHour();
    void newDay();
    void newSeason();
    NPCMap getNpcMap();
}
