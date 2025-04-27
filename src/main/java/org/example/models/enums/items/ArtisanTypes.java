package org.example.models.enums.items;

import org.example.models.enums.items.products.ProcessedProduct;

import java.util.ArrayList;
import java.util.List;

public enum ArtisanTypes implements Item {
    BeeHouse(new ArrayList<>(List.of(ProcessedProduct.Honey))),
    CheesePress(new ArrayList<>(List.of(
            ProcessedProduct.Cheese,
            ProcessedProduct.LargeCheese,
            ProcessedProduct.GoatCheese,
            ProcessedProduct.LargeGoatCheese
    ))),
    Keg(new ArrayList<>(List.of(
            ProcessedProduct.Beer,
            ProcessedProduct.Vinegar,
            ProcessedProduct.Coffee,
            ProcessedProduct.Juice, // TODO: add different juice type
            ProcessedProduct.Mead,
            ProcessedProduct.PaleAle,
            ProcessedProduct.Wine // TODO: add different type of wine
    ))),
    Dehydrator(new ArrayList<>(List.of(
            ProcessedProduct.DriedMushrooms, // TODO: add different type of dried mushroom
            ProcessedProduct.DriedFruit, // TODO: add different type of dried fruit
            ProcessedProduct.Raisins
    ))),
    CharcoalKiln(new ArrayList<>(List.of(
            ProcessedProduct.Coal
    ))),
    Loom(new ArrayList<>(List.of(
            ProcessedProduct.Cloth
    ))),
    MayonnaiseMachine(new ArrayList<>(List.of(
            ProcessedProduct.Mayonnaise,
            ProcessedProduct.DuckMayonnaise,
            ProcessedProduct.DinosaurMayonnaise
    ))),
    OilMaker(new ArrayList<>(List.of(
            ProcessedProduct.TruffleOil,
            ProcessedProduct.CornOil,
            ProcessedProduct.SunflowerSeedOil,
            ProcessedProduct.SunflowerOil
    ))),
    PreservesJar(new ArrayList<>(List.of(
            ProcessedProduct.Pickles, // TODO: add different type of pickles
            ProcessedProduct.Jelly // TODO: add different type of jelly
    ))),
    FishSmoker(new ArrayList<>(List.of(
            ProcessedProduct.SmokedFish // TODO: add different type of smoked fish
    ))),
    Furnace(new ArrayList<>(List.of(
            ProcessedProduct.MetalBar // TODO: add different type of metal bar
    )));

    private final ArrayList<ProcessedProduct> products;

    ArtisanTypes(ArrayList<ProcessedProduct> products) {
        this.products = products;
    }
}
