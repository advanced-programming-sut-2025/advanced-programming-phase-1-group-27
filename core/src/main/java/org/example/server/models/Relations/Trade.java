package org.example.server.models.Relations;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Message;
import org.example.server.models.Stacks;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Backpack;

import java.util.ArrayList;
import java.util.HashMap;

public class Trade {

    private static int idCounter = 1;
    private String starter, other;
    private ArrayList<Stacks> starterSelected, othersSelected;
    private int id;

    public Trade(String starter, String other, ArrayList<Stacks> starterSelected, ArrayList<Stacks> othersSelected) {
        this.starter = starter;
        this.other = other;
        this.starterSelected = starterSelected;
        this.othersSelected = othersSelected;
        this.id = idCounter;
        idCounter++;
    }

    public Trade(LinkedTreeMap<String, Object> info) {
        this.starter = (String) info.get("starter");
        this.other = (String) info.get("other");
        this.starterSelected = new Backpack((LinkedTreeMap<String, Object>) info.get("starterSelected")).getItems();
        this.othersSelected = new Backpack((LinkedTreeMap<String, Object>) info.get("otherSelected")).getItems();
        this.id = ((Number) info.get("id")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("starter", starter);
        info.put("other", other);
        info.put("starterSelected", new Backpack(ToolType.BasicBackpack, starterSelected).getInfo());
        info.put("otherSelected", new Backpack(ToolType.BasicBackpack, othersSelected).getInfo());
        info.put("id", id);
        return info;
    }

    public Trade(Message message) {
        this.starter = message.getFromBody("starter");
        this.other = message.getFromBody("other");
        this.starterSelected = new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("starterSelected")).getItems();
        this.othersSelected = new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("otherSelected")).getItems();
        this.id = idCounter;
        idCounter++;
    }
}
