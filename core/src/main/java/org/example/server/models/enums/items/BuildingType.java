package org.example.server.models.enums.items;

import org.example.server.models.Item;

public enum BuildingType implements Item {
    Barn(6000, 7, 4, 4 , "Items/Buildings/Barn.png"),
    BigBarn(12000, 7, 4, 8 , "Items/Buildings/Big_Barn.png"),
    DeluxeBarn(25000, 7, 4, 12 , "Items/Buildings/Deluxe_Barn.png"),
    Coop(4000, 6, 3, 4 ,  "Items/Buildings/Coop.png"),
    BigCoop(10000, 6, 3, 8 , "Items/Buildings/Big_Coop.png"),
    DeluxeCoop(20000, 6, 3, 12 , "Items/Buildings/Deluxe_Coop.png"),
    Well(1000, 3, 3, null , "Items/Buildings/Well.png"),
    ShippingBin(250, 1, 1, null , "Items/Buildings/Shipping_Bin.png"),;

    private final int price;
    private final int width;
    private final int height;
    private final Integer capacity;
    private Recipe recipe;
    private final String address;

    BuildingType(int price, int width, int height, Integer capacity , String address) {
        this.price = price;
        this.width = width;
        this.height = height;
        this.capacity = capacity;
        this.recipe = null;
        this.address = address;
    }

    public static BuildingType getItem(String itemName) {
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.getName().equalsIgnoreCase(itemName)) {
                return buildingType;
            }
        }
        return null;
    }

    public static BuildingType getEnclosureType(String buildingName) {
        return switch (buildingName) {
            case "Barn" -> Barn;
            case "Big Barn" -> BigBarn;
            case "Deluxe Barn" -> DeluxeBarn;
            case "Coop" -> Coop;
            case "Big Coop" -> BigCoop;
            case "Deluxe Coop" -> DeluxeCoop;
            default -> null;
        };
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Recipe getRecipe() {
        if (recipe == null) {
            for (Recipe recipe : Recipe.values()) {
                if (recipe.getFinalProduct() == this) {
                    return this.recipe = recipe;
                }
            }
        }
        return recipe;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public boolean isBarn() {
        return this == Barn || this == BigBarn || this == DeluxeBarn;
    }

    public boolean isCoop() {
        return this == Coop || this == BigCoop || this == DeluxeCoop;
    }
}
