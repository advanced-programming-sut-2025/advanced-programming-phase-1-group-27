package org.example.common.models.Relations;

import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;

public class Relation {
    private int level;
    private int xp;

    public Relation() {
        this.level = 0;
        this.xp = 0;
    }

    public Relation(LinkedTreeMap<String, Object> info) {
        this.level = ((Number) info.get("level")).intValue();
        this.xp = ((Number) info.get("xp")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("level", this.level);
        info.put("xp", this.xp);
        return info;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
