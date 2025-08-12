package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.enums.AbilityType;

import java.util.HashMap;

public class Buff {
    private AbilityType ability;
    private int remainingTime;

    public Buff(AbilityType ability, int remainingTime) {
        this.ability = ability;
        this.remainingTime = remainingTime;
    }

    public Buff(Buff buff) {
        this.ability = buff.ability;
        this.remainingTime = buff.remainingTime;
    }

    public Buff(LinkedTreeMap<String, Object> info) {
        this.ability = AbilityType.getAbilityType((String) info.get("ability"));
        this.remainingTime = ((Number) info.get("remainingTime")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("remainingTime", remainingTime);
        info.put("ability", ability.name());
        return info;
    }

    public void set(Buff buff) {
        ability = buff.ability;
        remainingTime = buff.remainingTime;
    }

    public AbilityType getAbility() {
        return ability;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void reduceRemainingTime() {
        remainingTime--;
    }
}
