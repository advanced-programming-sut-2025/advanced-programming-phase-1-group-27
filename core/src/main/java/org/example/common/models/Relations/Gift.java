package org.example.common.models.Relations;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Message;
import org.example.common.models.Stacks;

import java.util.HashMap;

public class Gift {
    private static int idCounter = 1;
    private String from;
    private String to;
    private Stacks stack;
    private int rate;
    private int id;

    public Gift (String from, String to, Stacks stack) {
        this.from = from;
        this.to = to;
        this.stack = stack;
        rate = -1;
    }

    public Gift (LinkedTreeMap<String, Object> info) {
        this.from = (String) info.get("from");
        this.to = (String) info.get("to");
        this.stack = new Stacks((LinkedTreeMap<String, Object>) info.get("stack"));
        this.rate = ((Number) info.get("rate")).intValue();
        this.id = ((Number) info.get("id")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("from", from);
        info.put("to", to);
        info.put("stack", stack.getInfo());
        info.put("rate", rate);
        info.put("id", id);
        return info;
    }

    public Gift (Message message) {
        this.from = message.getFromBody("starter");
        this.to = message.getFromBody("other");
        this.stack = new Stacks(message.getFromBody("gift"));
        rate = -1;
        this.id = idCounter;
        idCounter++;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Stacks getStack() {
        return stack;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }
}
