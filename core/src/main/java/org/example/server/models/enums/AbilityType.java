package org.example.server.models.enums;

public enum AbilityType {
    MaxEnergyUltimate,
    MaxEnergyCommunity,
    Farming,
    Foraging,
    Fishing,
    Mining;

    public static AbilityType getAbilityType(String abilityName) {
        return switch (abilityName) {
            case "farming" -> Farming;
            case "foraging" -> Foraging;
            case "fishing" -> Fishing;
            case "mining" -> Mining;
            default -> null;
        };
    }
}
