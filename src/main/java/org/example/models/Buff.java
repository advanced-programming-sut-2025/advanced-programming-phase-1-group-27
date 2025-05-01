package org.example.models;

import org.example.models.enums.AbilityType;

public class Buff {
    private final AbilityType ability;
    private final int duration;

    public Buff(AbilityType ability, int duration) {
        this.ability = ability;
        this.duration = duration;
    }

    public AbilityType getAbility() {
        return ability;
    }

    public int getDuration() {
        return duration;
    }
}
