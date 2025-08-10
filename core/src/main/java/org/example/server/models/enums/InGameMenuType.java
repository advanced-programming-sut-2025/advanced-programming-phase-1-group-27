package org.example.server.models.enums;

public enum InGameMenuType {

    NONE(null),
    INVENTORY(0),
    SKILL(1),
    SOCIAL(2),
    MAP(3),
    CRAFTING(4),
    EXIT(5),
    COOKING(6),
    SHOP(7),
    PLAYER_SOCIAL(8),
    RADIO(9),
    ANIMAL_ENCLOSURE(10),
    ANIMAL(11),
    ARTISAN_MINI(12),
    ARTISAN(13),
    REACTION(14),
    LEADERBOARD(15);


    private final Integer menuIndex;


    InGameMenuType(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

}

