package org.example.server.models.enums;

import org.example.server.models.Item;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;
import java.util.List;

public enum ArtisanTypes {
    BeeHouse(new ArrayList<>(List.of(ProcessedProductType.Honey))),
    CheesePress(new ArrayList<>(List.of(
            ProcessedProductType.Cheese,
            ProcessedProductType.LargeCheese,
            ProcessedProductType.GoatCheese,
            ProcessedProductType.LargeGoatCheese
    ))),
    Keg(new ArrayList<>(List.of(
            ProcessedProductType.Beer,
            ProcessedProductType.Vinegar,
            ProcessedProductType.Coffee,
            ProcessedProductType.Juice,
            ProcessedProductType.Mead,
            ProcessedProductType.PaleAle,
            ProcessedProductType.Wine
    ))),
    Dehydrator(new ArrayList<>(List.of(
            ProcessedProductType.DriedMushroom,
            ProcessedProductType.DriedFruit,
            ProcessedProductType.Raisins
    ))),
    CharcoalKiln(new ArrayList<>(List.of(
            ProcessedProductType.Coal
    ))),
    Loom(new ArrayList<>(List.of(
            ProcessedProductType.Cloth
    ))),
    MayonnaiseMachine(new ArrayList<>(List.of(
            ProcessedProductType.Mayonnaise,
            ProcessedProductType.DuckMayonnaise,
            ProcessedProductType.DinosaurMayonnaise
    ))),
    OilMaker(new ArrayList<>(List.of(
            ProcessedProductType.TruffleOil,
            ProcessedProductType.Oil
    ))),
    PreservesJar(new ArrayList<>(List.of(
            ProcessedProductType.Pickle,
            ProcessedProductType.Jelly
    ))),
    FishSmoker(new ArrayList<>(List.of(
            ProcessedProductType.SmokedFish
    ))),
    Furnace(new ArrayList<>(List.of(
            ProcessedProductType.CopperMetalBar,
            ProcessedProductType.IronMetalBar,
            ProcessedProductType.GoldMetalBar,
            ProcessedProductType.IridiumMetalBar
    )));

    private final ArrayList<ProcessedProductType> products;

    ArtisanTypes(ArrayList<ProcessedProductType> products) {
        this.products = products;
    }

    public static ArtisanTypes getArtisan(Item item) {
        if (item == null)
            return null;
        for (ArtisanTypes artisan : ArtisanTypes.values()) {
            if (artisan.toString().equalsIgnoreCase(item.getName()))
                return artisan;
        }
        return null;
    }

    public static ArtisanTypes getArtisan(String artisanName) {
        for (ArtisanTypes artisan : ArtisanTypes.values()) {
            if (artisan.toString().equalsIgnoreCase(artisanName))
                return artisan;
        }
        return null;
    }

    public ArrayList<ProcessedProductType> getProducts() {
        return products;
    }

    public Recipe getRecipe(String[] itemsList) {
        for (ProcessedProductType product : products) {
            if (product.getRecipe() == null)
                System.out.println(product.getName());
            if (product.getRecipe().isEqual(itemsList))
                return product.getRecipe();
        }
        return null;
    }
}
