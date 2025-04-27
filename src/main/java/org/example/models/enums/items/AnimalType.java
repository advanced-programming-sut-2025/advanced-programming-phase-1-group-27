package org.example.models.enums.items;

import org.example.models.enums.items.products.AnimalProduct;

import java.util.ArrayList;
import java.util.List;

public enum AnimalType implements Item {
    Chicken(new ArrayList<>(List.of(
            AnimalProduct.Egg,
            AnimalProduct.LargeEgg
    )), 800, 1, new ArrayList<>(List.of(
            FarmBuildingType.Coop,
            FarmBuildingType.BigCoop,
            FarmBuildingType.DeluxeCoop
    ))),
    Duck(new ArrayList<>(List.of(
            AnimalProduct.DuckEgg,
            AnimalProduct.DuckFeather
    )), 1200, 2, new ArrayList<>(List.of(
            FarmBuildingType.BigCoop,
            FarmBuildingType.DeluxeCoop
    ))),
    Rabbit(new ArrayList<>(List.of(
            AnimalProduct.Wool,
            AnimalProduct.RabbitLeg
    )), 8000, 4, new ArrayList<>(List.of(
            FarmBuildingType.DeluxeCoop
    ))),
    Dinosaur(new ArrayList<>(List.of(
            AnimalProduct.DinosaurEgg
    )), 14000, 7, new ArrayList<>(List.of(
            FarmBuildingType.BigCoop
    ))),
    Cow(new ArrayList<>(List.of(
            AnimalProduct.CowMilk,
            AnimalProduct.LargeCowMilk
    )), 1500, 1, new ArrayList<>(List.of(
            FarmBuildingType.Barn,
            FarmBuildingType.BigBarn,
            FarmBuildingType.DeluxeBarn
    ))),
    Goat(new ArrayList<>(List.of(
            AnimalProduct.GoatMilk,
            AnimalProduct.LargeGoatMilk
    )), 4000, 2, new ArrayList<>(List.of(
            FarmBuildingType.BigBarn,
            FarmBuildingType.DeluxeBarn
    ))),
    Sheep(new ArrayList<>(List.of(
            AnimalProduct.Wool
    )), 8000, 3, new ArrayList<>(List.of(
            FarmBuildingType.DeluxeBarn
    ))),
    Pig(new ArrayList<>(List.of(
            AnimalProduct.Truffle
    )), 16000, null, new ArrayList<>(List.of(
            FarmBuildingType.DeluxeBarn
    )));

    private final ArrayList<AnimalProduct> products;
    private final int price;
    private final Integer yieldRate;
    private final ArrayList<FarmBuildingType> appropriateFarmType;

    AnimalType(ArrayList<AnimalProduct> products, int price, Integer yieldRate, ArrayList<FarmBuildingType> appropriateFarmType) {
        this.products = products;
        this.price = price;
        this.yieldRate = yieldRate;
        this.appropriateFarmType = appropriateFarmType;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
