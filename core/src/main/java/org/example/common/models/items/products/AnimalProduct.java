package org.example.common.models.items.products;

import org.example.common.models.Edible;
import org.example.common.models.Item;

public enum AnimalProduct implements Item, Edible {
    Egg(50 , "Items/Animal_products/Egg.png", true),
    LargeEgg(95 , "Items/Animal_products/Large_Egg.png", true),
    DuckEgg(95 , "Items/Animal_products/Duck_Egg.png", true),
    DuckFeather(250 , "Items/Animal_products/Duck_Feather.png", false),
    Wool(340 , "Items/Animal_products/Wool.png", false),
    RabbitLeg(565 , "Items/Animal_products/Rabbit_Leg.png", true),
    DinosaurEgg(350 ,  "Items/Animal_products/Dinosaur_Egg.png", true),
    CowMilk(125 ,  "Items/Animal_products/Milk.png", true),
    LargeCowMilk(190 , "Items/Animal_products/Large_Milk.png", true),
    GoatMilk(225 , "Items/Animal_products/Goat_Milk.png", true),
    LargeGoatMilk(345 , "Items/Animal_products/Large_Goat_Milk.png", true),
    Truffle(625 , "Items/Animal_products/Truffle.png", true); // haram

    private final int price;
    private final String address;
    private final boolean isEdible;

    AnimalProduct(int price , String address, boolean isEdible) {
        this.price = price;
        this.address = address;
        this.isEdible = isEdible;
    }

    public static AnimalProduct getItem(String itemName) {
        for (AnimalProduct animalProduct : AnimalProduct.values()) {
            if (animalProduct.getName().equalsIgnoreCase(itemName)) {
                return animalProduct;
            }
        }
        return null;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public boolean isFood() {
        return this != DuckFeather && this != Wool;
    }

    @Override
    public boolean isEdible() {
        return isEdible;
    }

    @Override
    public int getEnergy() {
        return 0;
    }
}
