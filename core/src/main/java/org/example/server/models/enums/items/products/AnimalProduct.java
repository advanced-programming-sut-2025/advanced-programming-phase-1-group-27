package org.example.server.models.enums.items.products;

import org.example.server.models.Item;

public enum AnimalProduct implements Item {
    Egg(50),
    LargeEgg(95),
    DuckEgg(95),
    DuckFeather(250),
    Wool(340),
    RabbitLeg(565),
    DinosaurEgg(350),
    CowMilk(125),
    LargeCowMilk(190),
    GoatMilk(225),
    LargeGoatMilk(345),
    Truffle(625); // haram

    private final int price;

    AnimalProduct(int price) {
        this.price = price;
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

    public boolean isFood() {
        return this != DuckFeather && this != Wool;
    }
}
