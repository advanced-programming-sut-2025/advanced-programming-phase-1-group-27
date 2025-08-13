package org.example.common.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;

public class Position {
    private final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(LinkedTreeMap<String, Object> info) {
        this.x = ((Number) info.get("x")).intValue();
        this.y = ((Number) info.get("y")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("x", x);
        info.put("y", y);
        return info;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
