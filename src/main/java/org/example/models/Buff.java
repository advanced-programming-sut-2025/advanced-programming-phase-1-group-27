package org.example.models;

import org.example.enums.AbilityType;

public class Buff {
    private final AbilityType ability;
    private final int duration;

    public AbilityType getAbility() {
        return ability;
    }

    public int getDuration() {
        return duration;
    }
}
