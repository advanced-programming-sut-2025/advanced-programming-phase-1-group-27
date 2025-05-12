package org.example.models.enums;

import org.example.models.enums.Plants.SaplingType;
import org.example.models.enums.Plants.SeedType;
import org.example.models.Stacks;
import org.example.models.enums.items.*;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;

public enum StocksForShops {
    BlackSmithStock(BlacksmithStoreStock()),
    MarnieShopStock(MarnieShopStock()),
    StardropSaloonStock(StardropSaloonStock()),
    CarpenterShopStock(CarpenterShopStock()),
    PermanentJojaMartStock(PermanentJojaMartStock()),
    SpringJojaMartStock(SpringJojaMartStock()),
    SummerJojaMartStock(SummerMartJojaMartStock()),
    FallJojaMartStock(FallMartJojaMartStock()),
    WinterJojaMartStock(WinterMartJojaMartStock()),
    PermanentPierreGeneralStoreStock(PermanentPierreGeneralStoreStock()),
    PierreGeneralStoreBackpack(PierreGeneralStoreBackpack()),
    SpringPierreGeneralStoreStock(SpringPierreGeneralStoreStock()),
    SummerPierreGeneralStoreStock(SummerPierreGeneralStoreStock()),
    FallPierreGeneralStoreStock(FallPierreGeneralStoreStock()),
    FishShopStock(FishShopStock());

    private ArrayList<Stacks> stacks;

    StocksForShops(ArrayList<Stacks> stacks) {
        this.stacks = stacks;
    }

    public ArrayList<Stacks> getStacks() {
        return stacks;
    }

    //Limits are daily and restock every morning
    //-1 means unlimited
    private static ArrayList<Stacks> BlacksmithStoreStock() {
        ArrayList<Stacks> BlacksmithStore = new ArrayList<>();
        BlacksmithStore.add(new Stacks(MineralType.CopperOre, -1));
        BlacksmithStore.add(new Stacks(MineralType.IronOre, -1));
        BlacksmithStore.add(new Stacks(MineralType.GoldOre, -1));
        BlacksmithStore.add(new Stacks(ProcessedProductType.Coal, -1));
        return BlacksmithStore;
    }

    private static ArrayList<Stacks> MarnieShopStock() {
        ArrayList<Stacks> MarnieShop = new ArrayList<>();
        MarnieShop.add(new Stacks(ShopItems.Hay, -1));
        MarnieShop.add(new Stacks(ToolType.MilkPail, ToolType.MilkPail.getLevel(), 1));
        MarnieShop.add(new Stacks(ToolType.Shear, ToolType.Shear.getLevel(), 1));
        MarnieShop.add(new Stacks(AnimalType.Chicken, 2));
        MarnieShop.add(new Stacks(AnimalType.Cow, 2));
        MarnieShop.add(new Stacks(AnimalType.Goat, 2));
        MarnieShop.add(new Stacks(AnimalType.Duck, 2));
        MarnieShop.add(new Stacks(AnimalType.Sheep, 2));
        MarnieShop.add(new Stacks(AnimalType.Rabbit, 2));
        MarnieShop.add(new Stacks(AnimalType.Dinosaur, 2));
        MarnieShop.add(new Stacks(AnimalType.Pig, 2));
        return MarnieShop;
    }

    private static ArrayList<Stacks> StardropSaloonStock() {
        ArrayList<Stacks> StardropSaloon = new ArrayList<>();
        StardropSaloon.add(new Stacks(ProcessedProductType.Beer, -1));
        StardropSaloon.add(new Stacks(CookingProduct.Salad, -1));
        StardropSaloon.add(new Stacks(CookingProduct.Bread, -1));
        StardropSaloon.add(new Stacks(CookingProduct.Spaghetti, -1));
        StardropSaloon.add(new Stacks(CookingProduct.Pizza, -1));
        StardropSaloon.add(new Stacks(ProcessedProductType.Coffee, -1));
        StardropSaloon.add(new Stacks(Recipe.HashbrownsRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.OmeletteRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.PancakeRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.BreadRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.TortillaRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.PizzaRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.MakiRollRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.TripleShotEspressoRecipe, 1));
        StardropSaloon.add(new Stacks(Recipe.CookieRecipe, 1));
        return StardropSaloon;
    }

    private static ArrayList<Stacks> CarpenterShopStock() {
        ArrayList<Stacks> CarpenterShop = new ArrayList<>();
        CarpenterShop.add(new Stacks(MineralType.Wood, -1));
        CarpenterShop.add(new Stacks(MineralType.Stone, -1));
        CarpenterShop.add(new Stacks(BuildingType.Barn, 1));
        CarpenterShop.add(new Stacks(BuildingType.BigBarn, 1));
        CarpenterShop.add(new Stacks(BuildingType.DeluxeBarn, 1));
        CarpenterShop.add(new Stacks(BuildingType.Coop, 1));
        CarpenterShop.add(new Stacks(BuildingType.BigCoop, 1));
        CarpenterShop.add(new Stacks(BuildingType.DeluxeCoop, 1));
        CarpenterShop.add(new Stacks(BuildingType.Well, 1));
        CarpenterShop.add(new Stacks(BuildingType.ShippingBin, -1));
        return CarpenterShop;
    }

    private static ArrayList<Stacks> PermanentJojaMartStock() {
        ArrayList<Stacks> JojaMart = new ArrayList<>();
        JojaMart.add(new Stacks(ShopItems.JojaCola, -1));
        JojaMart.add(new Stacks(SeedType.AncientSeed, 1));
        JojaMart.add(new Stacks(SeedType.GrassStater, -1));
        JojaMart.add(new Stacks(ShopItems.Sugar, -1));
        JojaMart.add(new Stacks(ShopItems.WheatFlour, -1));
        JojaMart.add(new Stacks(ShopItems.Rice, -1));
        return JojaMart;
    }

    private static ArrayList<Stacks> SpringJojaMartStock() {
        ArrayList<Stacks> SpringJojaMart = new ArrayList<>();
        SpringJojaMart.add(new Stacks(SeedType.ParsnipSeed, 5));
        SpringJojaMart.add(new Stacks(SeedType.BeanStarter, 5));
        SpringJojaMart.add(new Stacks(SeedType.CauliflowerSeed, 5));
        SpringJojaMart.add(new Stacks(SeedType.PotatoSeed, 5));
        SpringJojaMart.add(new Stacks(SeedType.StrawberrySeed, 5));
        SpringJojaMart.add(new Stacks(SeedType.TulipBulbSeed, 5));
        SpringJojaMart.add(new Stacks(SeedType.KaleSeed, 5));
        SpringJojaMart.add(new Stacks(SeedType.CoffeeBean, 1));
        SpringJojaMart.add(new Stacks(SeedType.CarrotSeed, 10));
        SpringJojaMart.add(new Stacks(SeedType.RhubarbSeed, 5));
        SpringJojaMart.add(new Stacks(SeedType.JazzSeed, 5));
        return SpringJojaMart;
    }

    private static ArrayList<Stacks> SummerMartJojaMartStock() {
        ArrayList<Stacks> SummerMartJojaMart = new ArrayList<>();
        SummerMartJojaMart.add(new Stacks(SeedType.TomatoSeed, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.PepperSeed, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.WheatSeed, 10));
        SummerMartJojaMart.add(new Stacks(SeedType.SummerSquashSeed, 10));
        SummerMartJojaMart.add(new Stacks(SeedType.RadishSeed, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.MelonSeed, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.HopsStarter, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.PoppySeed, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.SpangleSeed, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.StarfruitSeed, 5));
        SummerMartJojaMart.add(new Stacks(SeedType.CoffeeBean, 1));
        SummerMartJojaMart.add(new Stacks(SeedType.SunflowerSeed, 5));
        return SummerMartJojaMart;
    }

    private static ArrayList<Stacks> FallMartJojaMartStock() {
        ArrayList<Stacks> FallMartJojaMart = new ArrayList<>();
        FallMartJojaMart.add(new Stacks(SeedType.CornSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.EggplantSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.PumpkinSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.BroccoliSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.AmaranthSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.GrapeStarter, 5));
        FallMartJojaMart.add(new Stacks(SeedType.BeetSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.YamSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.BokChoySeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.CranberrySeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.SunflowerSeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.FairySeed, 5));
        FallMartJojaMart.add(new Stacks(SeedType.RareSeed, 1));
        FallMartJojaMart.add(new Stacks(SeedType.WheatSeed, 5));
        return FallMartJojaMart;
    }

    private static ArrayList<Stacks> WinterMartJojaMartStock() {
        ArrayList<Stacks> WinterMartJojaMart = new ArrayList<>();
        WinterMartJojaMart.add(new Stacks(SeedType.PowderMelonSeed, 10));
        return WinterMartJojaMart;
    }

    private static ArrayList<Stacks> PermanentPierreGeneralStoreStock() {
        ArrayList<Stacks> PermanentPierreGeneralStore = new ArrayList<>();
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.Rice, -1));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.WheatFlour, -1));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.Bouquet, 2));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.WeddingRing, 2));
        PermanentPierreGeneralStore.add(new Stacks(Recipe.DehydratorRecipe, 1));
        PermanentPierreGeneralStore.add(new Stacks(Recipe.GrassStarterRecipe, 1));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.Sugar, -1));
        PermanentPierreGeneralStore.add(new Stacks(ProcessedProductType.Oil, -1));
        PermanentPierreGeneralStore.add(new Stacks(ProcessedProductType.Vinegar, -1));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.DeluxeRetainingSoil, -1));
        PermanentPierreGeneralStore.add(new Stacks(SeedType.GrassStater, -1));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.SpeedGro, -1));
        PermanentPierreGeneralStore.add(new Stacks(SaplingType.AppleSapling, -1));
        PermanentPierreGeneralStore.add(new Stacks(SaplingType.ApricotSapling, -1));
        PermanentPierreGeneralStore.add(new Stacks(SaplingType.CherrySapling, -1));
        PermanentPierreGeneralStore.add(new Stacks(SaplingType.OrangeSapling, -1));
        PermanentPierreGeneralStore.add(new Stacks(SaplingType.PeachSapling, -1));
        PermanentPierreGeneralStore.add(new Stacks(SaplingType.PomegranateSapling, -1));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.BasicRetainingSoil, -1));
        PermanentPierreGeneralStore.add(new Stacks(ShopItems.QualityRetainingSoil, -1));
        return PermanentPierreGeneralStore;
    }

    private static ArrayList<Stacks> PierreGeneralStoreBackpack() {
        ArrayList<Stacks> PierreGeneralStoreBackpack = new ArrayList<>();
        PierreGeneralStoreBackpack.add(new Stacks(ToolType.LargeBackpack, ToolType.LargeBackpack.getLevel(), 1));
        PierreGeneralStoreBackpack.add(new Stacks(ToolType.DeluxeBackpack, ToolType.DeluxeBackpack.getLevel(),
                1));
        return PierreGeneralStoreBackpack;
    }

    private static ArrayList<Stacks> SpringPierreGeneralStoreStock() {
        ArrayList<Stacks> SpringPierreGeneralStoreStock = new ArrayList<>();
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.ParsnipSeed, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.BeanStarter, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.CauliflowerSeed, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.PotatoSeed, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.TulipBulbSeed, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.KaleSeed, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.JazzSeed, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.GarlicSeed, 5));
        SpringPierreGeneralStoreStock.add(new Stacks(SeedType.RiceShoot, 5));
        return SpringPierreGeneralStoreStock;
    }

    private static ArrayList<Stacks> SummerPierreGeneralStoreStock() {
        ArrayList<Stacks> SummerPierreGeneralStoreStock = new ArrayList<>();
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.MelonSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.TomatoSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.BlueberrySeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.PepperSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.WheatSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.RadishSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.PoppySeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.SpangleSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.HopsStarter, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.CornSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.SunflowerSeed, 5));
        SummerPierreGeneralStoreStock.add(new Stacks(SeedType.RedCabbageSeed, 5));
        return SummerPierreGeneralStoreStock;
    }


    private static ArrayList<Stacks> FallPierreGeneralStoreStock() {
        ArrayList<Stacks> FallPierreGeneralStoreStock = new ArrayList<>();
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.EggplantSeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.CornSeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.PumpkinSeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.BokChoySeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.YamSeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.CranberrySeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.SunflowerSeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.FairySeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.AmaranthSeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.GrapeStarter, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.WheatSeed, 5));
        FallPierreGeneralStoreStock.add(new Stacks(SeedType.ArtichokeSeed, 5));
        return FallPierreGeneralStoreStock;
    }

    private static ArrayList<Stacks> FishShopStock() {
        ArrayList<Stacks> FishShopStock = new ArrayList<>();
        FishShopStock.add(new Stacks(Recipe.FishSmokerRecipe, 1));
        FishShopStock.add(new Stacks(ShopItems.TroutSoup, 1));
        FishShopStock.add(new Stacks(ToolType.BambooPole, ToolType.BambooPole.getLevel(), 1));
        FishShopStock.add(new Stacks(ToolType.TrainingRod, ToolType.TrainingRod.getLevel(), 1));
        FishShopStock.add(new Stacks(ToolType.FiberglassRod,ToolType.FiberglassRod.getLevel(), 1));
        FishShopStock.add(new Stacks(ToolType.IridiumRod, ToolType.IridiumRod.getLevel(), 1));
        return FishShopStock;
    }
}
