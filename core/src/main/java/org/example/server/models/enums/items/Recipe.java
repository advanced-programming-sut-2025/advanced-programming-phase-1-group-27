package org.example.server.models.enums.items;

import org.example.server.models.Ingredient;
import org.example.server.models.Item;
import org.example.server.models.enums.Plants.FruitType;
import org.example.server.models.enums.Plants.SaplingType;
import org.example.server.models.enums.Plants.SeedType;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Recipe implements Item {
    HoneyRecipe(0, ProcessedProductType.Honey, new ArrayList<>(List.of())),
    CheeseRecipe(0, ProcessedProductType.Cheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.CowMilk, 1)
    ))),
    LargeCheeseRecipe(0, ProcessedProductType.LargeCheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.LargeCowMilk, 1)
    ))),
    GoatCheeseRecipe(0, ProcessedProductType.GoatCheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.GoatMilk, 1)
    ))),
    LargeGoatCheeseRecipe(0, ProcessedProductType.LargeGoatCheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.LargeGoatMilk, 1)
    ))),
    BeerRecipe(0, ProcessedProductType.Beer, new ArrayList<>(List.of(
            new Ingredient(FruitType.Wheat, 1)
    ))),
    VinegarRecipe(0, ProcessedProductType.Vinegar, new ArrayList<>(List.of(
            new Ingredient(ShopItems.Rice, 1)
    ))),
    CoffeeRecipe(0, ProcessedProductType.Coffee, new ArrayList<>(List.of(
            new Ingredient(FruitType.CoffeeBean, 5)
    ))),
    JuiceRecipe(0, ProcessedProductType.Juice, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FruitType.Amaranth,
                    FruitType.Artichoke,
                    FruitType.Beet,
                    FruitType.BokChoy,
                    FruitType.Broccoli,
                    FruitType.Carrot,
                    FruitType.Cauliflower,
                    FruitType.Corn,
                    FruitType.Eggplant,
                    FruitType.FiddleheadFern,
                    FruitType.Garlic,
                    FruitType.GreenBean,
                    FruitType.Hops,
                    FruitType.Kale,
                    FruitType.Parsnip,
                    FruitType.Potato,
                    FruitType.Pumpkin,
                    FruitType.Radish,
                    FruitType.RedCabbage,
                    FruitType.SummerSquash,
                    FruitType.Tomato,
                    FruitType.UnmilledRice,
                    FruitType.Wheat,
                    FruitType.Yam
            )), 1)
    ))),
    MeadRecipe(0, ProcessedProductType.Mead, new ArrayList<>(List.of(
            new Ingredient(ProcessedProductType.Honey, 1)
    ))),
    PaleAleRecipe(0, ProcessedProductType.PaleAle, new ArrayList<>(List.of(
            new Ingredient(FruitType.Hops, 1)
    ))),
    WineRecipe(0, ProcessedProductType.Wine, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FruitType.AncientFruit,
                    FruitType.Apple,
                    FruitType.Apricot,
                    FruitType.Banana,
                    FruitType.Blackberry,
                    FruitType.Blueberry,
                    FruitType.Cherry,
                    FruitType.Cranberry,
                    FruitType.CrystalFruit,
                    FruitType.Grape,
                    FruitType.HotPepper,
                    FruitType.Mango,
                    FruitType.Melon,
                    FruitType.Orange,
                    FruitType.Peach,
                    FruitType.Pomegranate,
                    FruitType.PowderMelon,
                    FruitType.Rhubarb,
                    FruitType.SalmonBerry,
                    FruitType.SpiceBerry,
                    FruitType.Starfruit,
                    FruitType.Strawberry,
                    FruitType.WildPlum
            )), 1)
    ))),
    DriedMushroomRecipe(0, ProcessedProductType.DriedMushroom, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FruitType.Chanterelle,
                    FruitType.CommonMushroom,
                    FruitType.Morel,
                    FruitType.PurpleMushroom,
                    FruitType.RedMushroom
            )), 1)
    ))),
    DriedFruitRecipe(0, ProcessedProductType.DriedFruit, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FruitType.AncientFruit,
                    FruitType.Apple,
                    FruitType.Apricot,
                    FruitType.Banana,
                    FruitType.Blackberry,
                    FruitType.Blueberry,
                    FruitType.Cherry,
                    FruitType.Cranberry,
                    FruitType.CrystalFruit,
                    FruitType.HotPepper,
                    FruitType.Mango,
                    FruitType.Melon,
                    FruitType.Orange,
                    FruitType.Peach,
                    FruitType.Pomegranate,
                    FruitType.PowderMelon,
                    FruitType.Rhubarb,
                    FruitType.SalmonBerry,
                    FruitType.SpiceBerry,
                    FruitType.Starfruit,
                    FruitType.Strawberry,
                    FruitType.WildPlum
            )), 1)
    ))),
    RaisinsRecipe(0, ProcessedProductType.Raisins, new ArrayList<>(List.of(
            new Ingredient(FruitType.Grape, 5)
    ))),
    CoalRecipe(0, ProcessedProductType.Coal, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 10)
    ))),
    ClothRecipe(0, ProcessedProductType.Cloth, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.Wool, 1)
    ))),
    MayonnaiseRecipe(0, ProcessedProductType.Mayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.Egg, 1)
    ))),
    LargeMayonnaiseRecipe(0, ProcessedProductType.LargeMayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.LargeEgg, 1)
    ))),
    DuckMayonnaiseRecipe(0, ProcessedProductType.DuckMayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.DuckEgg, 1)
    ))),
    DinosaurMayonnaiseRecipe(0, ProcessedProductType.DinosaurMayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.DinosaurEgg, 1)
    ))),
    TruffleOilRecipe(0, ProcessedProductType.TruffleOil, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.Truffle, 1)
    ))),
    OilRecipe(0, ProcessedProductType.Oil, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FruitType.Corn,
                    FruitType.Sunflower,
                    SeedType.SunflowerSeed
            )), 1)
    ))),
    PickleRecipe(0, ProcessedProductType.Pickle, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FruitType.Amaranth,
                    FruitType.Artichoke,
                    FruitType.Beet,
                    FruitType.BokChoy,
                    FruitType.Broccoli,
                    FruitType.Carrot,
                    FruitType.Cauliflower,
                    FruitType.Corn,
                    FruitType.Eggplant,
                    FruitType.FiddleheadFern,
                    FruitType.Garlic,
                    FruitType.GreenBean,
                    FruitType.Hops,
                    FruitType.Kale,
                    FruitType.Parsnip,
                    FruitType.Potato,
                    FruitType.Pumpkin,
                    FruitType.Radish,
                    FruitType.RedCabbage,
                    FruitType.SummerSquash,
                    FruitType.Tomato,
                    FruitType.UnmilledRice,
                    FruitType.Wheat,
                    FruitType.Yam
            )), 1)
    ))),
    JellyRecipe(0, ProcessedProductType.Jelly, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FruitType.AncientFruit,
                    FruitType.Apple,
                    FruitType.Apricot,
                    FruitType.Banana,
                    FruitType.Blackberry,
                    FruitType.Blueberry,
                    FruitType.Cherry,
                    FruitType.Cranberry,
                    FruitType.CrystalFruit,
                    FruitType.Grape,
                    FruitType.HotPepper,
                    FruitType.Mango,
                    FruitType.Melon,
                    FruitType.Orange,
                    FruitType.Peach,
                    FruitType.Pomegranate,
                    FruitType.PowderMelon,
                    FruitType.Rhubarb,
                    FruitType.SalmonBerry,
                    FruitType.SpiceBerry,
                    FruitType.Starfruit,
                    FruitType.Strawberry,
                    FruitType.WildPlum
            )), 1)
    ))),
    SmokedFishRecipe(0, ProcessedProductType.SmokedFish, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    FishType.values()
            )), 1),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    CopperMetalBarRecipe(0, ProcessedProductType.CopperMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.CopperOre, 5),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    IronMetalBarRecipe(0, ProcessedProductType.IronMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.IronOre, 5),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    GoldMetalBarRecipe(0, ProcessedProductType.GoldMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.GoldOre, 5),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    IridiumMetalBarRecipe(0, ProcessedProductType.IridiumMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.IridiumOre, 5),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    CherryBomobRecipe(0, CraftingProduct.CherryBomb, new ArrayList<>(List.of(
            new Ingredient(MineralType.CopperOre, 4),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    BombRecipe(0, CraftingProduct.Bomb, new ArrayList<>(List.of(
            new Ingredient(MineralType.IronOre, 4),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    MegaBombRecipe(0, CraftingProduct.MegaBomb, new ArrayList<>(List.of(
            new Ingredient(MineralType.GoldOre, 4),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1)
    ))),
    SprinklerRecipe(0, CraftingProduct.Sprinkler, new ArrayList<>(List.of(
            new Ingredient(ProcessedProductType.CopperMetalBar, 1),
            new Ingredient(ProcessedProductType.IronMetalBar, 1)
    ))),
    QualitySprinkler(0, CraftingProduct.QualitySprinkler, new ArrayList<>(List.of(
            new Ingredient(ProcessedProductType.IronMetalBar, 1),
            new Ingredient(ProcessedProductType.GoldMetalBar, 1)
    ))),
    IridiumSprinkler(0, CraftingProduct.IridiumSprinkler, new ArrayList<>(List.of(
            new Ingredient(ProcessedProductType.GoldMetalBar, 1),
            new Ingredient(ProcessedProductType.IridiumMetalBar, 1)
    ))),
    CharcoalKlinRecipe(0, CraftingProduct.CharcoalKlin, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 20),
            new Ingredient(ProcessedProductType.CopperMetalBar, 2)
    ))),
    FurnaceRecipe(0, CraftingProduct.Furnace, new ArrayList<>(List.of(
            new Ingredient(MineralType.CopperOre, 20),
            new Ingredient(MineralType.Stone, 25)
    ))),
    ScarecrowRecipe(0, CraftingProduct.Scarecrow, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 50),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1),
            new Ingredient(FruitType.Fiber, 20)
    ))),
    DeluxeScarecrowRecipe(0, CraftingProduct.DeluxeScarecrow, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 50),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 1),
            new Ingredient(FruitType.Fiber, 20),
            new Ingredient(MineralType.IridiumOre, 1)
    ))),
    BeeHouseRecipe(0, CraftingProduct.BeeHouse, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 40),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 8),
            new Ingredient(ProcessedProductType.IronMetalBar, 1)
    ))),
    CheesePressRecipe(0, CraftingProduct.CheesePress, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 45),
            new Ingredient(MineralType.Stone, 45),
            new Ingredient(ProcessedProductType.CopperMetalBar, 1)
    ))),
    KegRecipe(0, CraftingProduct.Keg, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 30),
            new Ingredient(ProcessedProductType.CopperMetalBar, 1),
            new Ingredient(ProcessedProductType.IronMetalBar, 1)
    ))),
    LoomRecipe(0, CraftingProduct.Loom, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 60),
            new Ingredient(FruitType.Fiber, 30)
    ))),
    MayonnaiseMachineRecipe(0, CraftingProduct.MayonnaiseMachine, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 15),
            new Ingredient(MineralType.Stone, 15),
            new Ingredient(ProcessedProductType.CopperMetalBar, 1)
    ))),
    OilMakerRecipe(0, CraftingProduct.OilMaker, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 100),
            new Ingredient(ProcessedProductType.GoldMetalBar, 1),
            new Ingredient(ProcessedProductType.IronMetalBar, 1)
    ))),
    PreservesJar(0, CraftingProduct.PreservesJar, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 50),
            new Ingredient(MineralType.Stone, 40),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 8)
    ))),
    DehydratorRecipe(10000, CraftingProduct.Dehydrator, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 30),
            new Ingredient(MineralType.Stone, 20),
            new Ingredient(FruitType.Fiber, 30)
    ))),
    GrassStarterRecipe(1000, CraftingProduct.GrassStarter, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 1),
            new Ingredient(FruitType.Fiber, 1)
    ))),
    FishSmokerRecipe(10000, CraftingProduct.FishSmoker, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 50),
            new Ingredient(ProcessedProductType.IronMetalBar, 3),
            new Ingredient(new ArrayList<>(List.of(
                    MineralType.Coal,
                    ProcessedProductType.Coal
            )), 10)
    ))),
    MysticTreeSeed(0, CraftingProduct.MysticTreeSeed, new ArrayList<>(List.of(
            new Ingredient(SaplingType.Acorn, 5),
            new Ingredient(SeedType.MapleSeed, 5),
            new Ingredient(SaplingType.PineCone, 5),
            new Ingredient(SeedType.MahoganySeed, 5)
    ))),
    FriedEggRecipe(0, CookingProduct.FriedEgg, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    AnimalProduct.Egg,
                    AnimalProduct.DuckEgg,
                    AnimalProduct.DinosaurEgg
            )), 1)
    ))),
    BakedFishRecipe(0, CookingProduct.BakedFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Sardine, 1),
            new Ingredient(FishType.Salmon, 1),
            new Ingredient(FruitType.Wheat, 1)
    ))),
    SaladRecipe(0, CookingProduct.Salad, new ArrayList<>(List.of(
            new Ingredient(FruitType.Leek, 1),
            new Ingredient(FruitType.Dandelion, 1)
    ))),
    OmeletteRecipe(100, CookingProduct.Omelette, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(List.of(
                    AnimalProduct.Egg,
                    AnimalProduct.DuckEgg,
                    AnimalProduct.DinosaurEgg
            )), 1),
            new Ingredient(new ArrayList<>(List.of(
                    AnimalProduct.CowMilk,
                    AnimalProduct.GoatMilk
            )), 1)
    ))),
    PumpkinPieRecipe(0, CookingProduct.PumpkinPie, new ArrayList<>(List.of(
            new Ingredient(FruitType.Pumpkin, 1),
            new Ingredient(ShopItems.WheatFlour, 1),
            new Ingredient(new ArrayList<>(List.of(
                    AnimalProduct.CowMilk,
                    AnimalProduct.GoatMilk
            )), 1),
            new Ingredient(ShopItems.Sugar, 1)
    ))),
    SpaghettiRecipe(0, CookingProduct.Spaghetti, new ArrayList<>(List.of(
            new Ingredient(ShopItems.WheatFlour, 1),
            new Ingredient(FruitType.Tomato, 1)
    ))),
    PizzaRecipe(150, CookingProduct.Pizza, new ArrayList<>(List.of(
            new Ingredient(ShopItems.WheatFlour, 1),
            new Ingredient(FruitType.Tomato, 1),
            new Ingredient(ProcessedProductType.Cheese, 1)
    ))),
    TortillaRecipe(100, CookingProduct.Tortilla, new ArrayList<>(List.of(
            new Ingredient(FruitType.Corn, 1)
    ))),
    MakiRollRecipe(300, CookingProduct.MakiRoll, new ArrayList<>(List.of(
            new Ingredient(new ArrayList<>(Arrays.asList(
                    FishType.values()
            )), 1),
            new Ingredient(ShopItems.Rice, 1)
    ))),
    TripleShotEspressoRecipe(5000, CookingProduct.TripleShotEspresso, new ArrayList<>(List.of(
            new Ingredient(ProcessedProductType.Coffee, 3)
    ))),
    CookieRecipe(300, CookingProduct.Cookie, new ArrayList<>(List.of(
            new Ingredient(ShopItems.WheatFlour, 1),
            new Ingredient(ShopItems.Sugar, 1),
            new Ingredient(new ArrayList<>(List.of(
                    AnimalProduct.Egg,
                    AnimalProduct.DuckEgg,
                    AnimalProduct.DinosaurEgg
            )), 1)
    ))),
    HashbrownsRecipe(50, CookingProduct.HashBrowns, new ArrayList<>(List.of(
            new Ingredient(FruitType.Potato, 1),
            new Ingredient(ProcessedProductType.Oil, 1)
    ))),
    PancakeRecipe(100, CookingProduct.Pancakes, new ArrayList<>(List.of(
            new Ingredient(ShopItems.WheatFlour, 1),
            new Ingredient(new ArrayList<>(List.of(
                    AnimalProduct.Egg,
                    AnimalProduct.DuckEgg,
                    AnimalProduct.DinosaurEgg
            )), 1)
    ))),
    FruitSaladRecipe(0, CookingProduct.FruitSalad, new ArrayList<>(List.of(
            new Ingredient(FruitType.Blueberry, 1),
            new Ingredient(FruitType.Melon, 1),
            new Ingredient(FruitType.Apricot, 1)
    ))),
    RedPlateRecipe(0, CookingProduct.RedPlate, new ArrayList<>(List.of(
            new Ingredient(FruitType.RedCabbage, 1),
            new Ingredient(FruitType.Radish, 1)
    ))),
    BreadRecipe(100, CookingProduct.Bread, new ArrayList<>(List.of(
            new Ingredient(ShopItems.WheatFlour, 1)
    ))),
    SalmonDinnerRecipe(0, CookingProduct.SalmonDinner, new ArrayList<>(List.of(
            new Ingredient(FishType.Salmon, 1),
            new Ingredient(FruitType.Amaranth, 1),
            new Ingredient(FruitType.Kale, 1)
    ))),
    VegetableMedleyRecipe(0, CookingProduct.VegetableMedley, new ArrayList<>(List.of(
            new Ingredient(FruitType.Tomato, 1),
            new Ingredient(FruitType.Beet, 1)
    ))),
    FarmerLunchRecipe(0, CookingProduct.FarmersLunch, new ArrayList<>(List.of(
            new Ingredient(CookingProduct.Omelette, 1),
            new Ingredient(FruitType.Parsnip, 1)
    ))),
    SurvivalBurgerRecipe(0, CookingProduct.SurvivalBurger, new ArrayList<>(List.of(
            new Ingredient(CookingProduct.Bread, 1),
            new Ingredient(FruitType.Carrot, 1),
            new Ingredient(FruitType.Eggplant, 1)
    ))),
    DishOfTheSeaRecipe(0, CookingProduct.DishOfTheSea, new ArrayList<>(List.of(
            new Ingredient(FishType.Sardine, 2),
            new Ingredient(CookingProduct.HashBrowns, 1)
    ))),
    SeaformPuddingRecipe(0, CookingProduct.SeaformPudding, new ArrayList<>(List.of(
            new Ingredient(FishType.Flounder, 1),
            new Ingredient(FishType.MidnightCarp, 1)
    ))),
    MinerTreatRecipe(0, CookingProduct.MinersTreat, new ArrayList<>(List.of(
            new Ingredient(FruitType.Carrot, 2),
            new Ingredient(ShopItems.Sugar, 1),
            new Ingredient(new ArrayList<>(List.of(
                    AnimalProduct.CowMilk,
                    AnimalProduct.GoatMilk
            )), 1)
    ))),
    BarnRecipe(0, BuildingType.Barn, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 350),
            new Ingredient(MineralType.Stone, 150)
    ))),
    BigBarnRecipe(0, BuildingType.BigBarn, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 450),
            new Ingredient(MineralType.Stone, 200)
    ))),
    DeluxeBarnRecipe(0, BuildingType.DeluxeBarn, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 550),
            new Ingredient(MineralType.Stone, 300)
    ))),
    CoopRecipe(0, BuildingType.Coop, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 300),
            new Ingredient(MineralType.Stone, 100)
    ))),
    BigCoopRecipe(0, BuildingType.BigCoop, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 400),
            new Ingredient(MineralType.Stone, 150)
    ))),
    DeluxeCoopRecipe(0, BuildingType.DeluxeCoop, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 500),
            new Ingredient(MineralType.Stone, 200)
    ))),
    WellRecipe(0, BuildingType.Well, new ArrayList<>(List.of(
            new Ingredient(MineralType.Stone, 75)
    ))),
    ShippingBinRecipe(0, BuildingType.ShippingBin, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 150)
    )));

    private final int price;
    private final Item finalProduct;
    private final ArrayList<Ingredient> ingredients;

    Recipe(int price, Item finalProduct, ArrayList<Ingredient> ingredients) {
        this.price = price;
        this.finalProduct = finalProduct;
        this.ingredients = ingredients;
    }

    public static Recipe getRecipe(Item item) {
        for (Recipe recipe : Recipe.values()) {
            if (recipe.finalProduct == item)
                return recipe;
        }
        return null;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public Item getFinalProduct() {
        return finalProduct;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String description() {
        StringBuilder result = new StringBuilder();
        result.append("Final Product: ").append(finalProduct.getName()).append("\n");
        result.append("Ingredients:\n");
        for (Ingredient ingredient : ingredients) {
            result.append(ingredient.toString()).append("\n");
        }
        return result.toString();
    }

    public boolean isEqual(String[] itemsList) {
        if (itemsList.length != ingredients.size())
            return false;
        for (Ingredient ingredient : ingredients) {
            boolean ingredientIsAvailable = false;
            for (Item item : ingredient.getPossibleIngredients()) {
                for (String itemName : itemsList) {
                    if (item.getName().equalsIgnoreCase(itemName)) {
                        ingredientIsAvailable = true;
                    }
                }
            }
            if (!ingredientIsAvailable)
                return false;
        }
        return true;
    }
}
