package org.example.server.models.Relations;

import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.Stacks;

import java.util.HashMap;

public class Gift {
    private String from;
    private String to;
    private Stacks stack;
    private int rate;

    public Gift (LinkedTreeMap<String, Object> info) {
        this.from = (String) info.get("from");
        this.to = (String) info.get("to");
        this.stack = new Stacks((LinkedTreeMap<String, Object>) info.get("stack"));
        this.rate = ((Number) info.get("rate")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("from", from);
        info.put("to", to);
        info.put("stack", stack.getInfo());
        info.put("rate", rate);
        return info;
    }
}
