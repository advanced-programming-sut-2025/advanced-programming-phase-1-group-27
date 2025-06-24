package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.enums.items.products.AnimalProduct;

import java.util.ArrayList;
import java.util.List;

public enum AnimalType implements Item {
    Chicken(new ArrayList<>(List.of(
            AnimalProduct.Egg,
            AnimalProduct.LargeEgg
    )), 800, 1, new ArrayList<>(List.of(
            BuildingType.Coop,
            BuildingType.BigCoop,
            BuildingType.DeluxeCoop
    ))),
    Duck(new ArrayList<>(List.of(
            AnimalProduct.DuckEgg,
            AnimalProduct.DuckFeather
    )), 1200, 2, new ArrayList<>(List.of(
            BuildingType.BigCoop,
            BuildingType.DeluxeCoop
    ))),
    Rabbit(new ArrayList<>(List.of(
            AnimalProduct.Wool,
            AnimalProduct.RabbitLeg
    )), 8000, 4, new ArrayList<>(List.of(
            BuildingType.DeluxeCoop
    ))),
    Dinosaur(new ArrayList<>(List.of(
            AnimalProduct.DinosaurEgg
    )), 14000, 7, new ArrayList<>(List.of(
            BuildingType.BigCoop
    ))),
    Cow(new ArrayList<>(List.of(
            AnimalProduct.CowMilk,
            AnimalProduct.LargeCowMilk
    )), 1500, 1, new ArrayList<>(List.of(
            BuildingType.Barn,
            BuildingType.BigBarn,
            BuildingType.DeluxeBarn
    ))),
    Goat(new ArrayList<>(List.of(
            AnimalProduct.GoatMilk,
            AnimalProduct.LargeGoatMilk
    )), 4000, 2, new ArrayList<>(List.of(
            BuildingType.BigBarn,
            BuildingType.DeluxeBarn
    ))),
    Sheep(new ArrayList<>(List.of(
            AnimalProduct.Wool
    )), 8000, 3, new ArrayList<>(List.of(
            BuildingType.DeluxeBarn
    ))),
    Pig(new ArrayList<>(List.of(
            AnimalProduct.Truffle
    )), 16000, 0, new ArrayList<>(List.of(
            BuildingType.DeluxeBarn
    )));

    private final ArrayList<AnimalProduct> products;
    private final int price;
    private final Integer yieldRate;
    private final ArrayList<BuildingType> appropriateFarmType;

    AnimalType(ArrayList<AnimalProduct> products, int price, Integer yieldRate, ArrayList<BuildingType> appropriateFarmType) {
        this.products = products;
        this.price = price;
        this.yieldRate = yieldRate;
        this.appropriateFarmType = appropriateFarmType;
    }

    public static AnimalType getItem(String itemName) {
        for (AnimalType animalType : AnimalType.values()) {
            if (animalType.getName().equalsIgnoreCase(itemName)) {
                return animalType;
            }
        }
        return null;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public ArrayList<AnimalProduct> getProducts() {
        return products;
    }

    public Integer getYieldRate() {
        return yieldRate;
    }

    public ArrayList<BuildingType> getAppropriateFarmType() {
        return appropriateFarmType;
    }
}
