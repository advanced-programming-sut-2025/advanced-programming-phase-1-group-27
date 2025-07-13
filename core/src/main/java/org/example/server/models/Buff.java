package org.example.server.models;

import org.example.server.models.enums.AbilityType;

public class Buff {
    private final AbilityType ability;
    private int remainingTime;

    public Buff(AbilityType ability, int remainingTime) {
        this.ability = ability;
        this.remainingTime = remainingTime;
    }

    public Buff(Buff buff) {
        this.ability = buff.ability;
        this.remainingTime = buff.remainingTime;
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
