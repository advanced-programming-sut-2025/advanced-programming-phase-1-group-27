package org.example.common.models;

public enum AbilityType {
    MaxEnergyUltimate,
    MaxEnergyCommunity,
    Farming,
    Foraging,
    Fishing,
    Mining;

    public static AbilityType getAbilityType(String abilityName) {
        for (AbilityType abilityType : values()) {
            if (abilityType.name().equalsIgnoreCase(abilityName))
                return abilityType;
        }
        return null;
    }
}
