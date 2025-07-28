package org.example.server.models.enums;

public enum InGameMenuType {

    NONE(null),
    INVENTORY(0),
    SKILL(1),
    SOCIAL(2),
    MAP(3),
    CRAFTING(4),
    EXIT(5);


    private final Integer menuIndex;


    InGameMenuType(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

}

