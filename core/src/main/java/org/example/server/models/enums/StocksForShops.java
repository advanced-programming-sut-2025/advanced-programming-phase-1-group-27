package org.example.server.models.enums;

import org.example.server.models.Stock;
import org.example.server.models.enums.Plants.SaplingType;
import org.example.server.models.enums.Plants.SeedType;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.items.*;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;

public enum StocksForShops {
    BlackSmithStock(getBlacksmithStoreStock()),
    MarnieRanchStock(getMarnieRanchStock()),
    StardropSaloonStock(getStardropSaloonStock()),
    CarpenterShopStock(getCarpenterShopStock()),
    PermanentJojaMartStock(getPermanentJojaMartStock()),
    SpringJojaMartStock(getSpringJojaMartStock()),
    SummerJojaMartStock(getSummerJojaMartStock()),
    FallJojaMartStock(getFallJojaMartStock()),
    WinterJojaMartStock(getWinterJojaMartStock()),
    PierreGeneralStoreStock(getPermanentPierreGeneralStoreStock()),
    FallPierreGeneralStoreStock(getFallPierreGeneralStoreStock()),
    FishShopStock(getFishShopStock());

    static {
        ArrayList<Stock> permanentJojaMartStock = getPermanentJojaMartStock();
        SpringJojaMartStock.stock.addAll(permanentJojaMartStock);
        SummerJojaMartStock.stock.addAll(permanentJojaMartStock);
        FallJojaMartStock.stock.addAll(permanentJojaMartStock);
        WinterJojaMartStock.stock.addAll(permanentJojaMartStock);

        PierreGeneralStoreStock.stock.addAll(getSpringPierreGeneralStoreStock());
        PierreGeneralStoreStock.stock.addAll(getSummerPierreGeneralStoreStock());
        PierreGeneralStoreStock.stock.addAll(getFallPierreGeneralStoreStock());
    }

    private ArrayList<Stock> stock;

    StocksForShops(ArrayList<Stock> stock) {
        this.stock = stock;
    }

    //Limits are daily and restock every morning
    //-1 means unlimited
    private static ArrayList<Stock> getBlacksmithStoreStock() {
        ArrayList<Stock> blacksmithStore = new ArrayList<>();
        blacksmithStore.add(new Stock(MineralType.CopperOre, -1, 75));
        blacksmithStore.add(new Stock(MineralType.IronOre, -1, 150));
        blacksmithStore.add(new Stock(MineralType.GoldOre, -1, 400));
        blacksmithStore.add(new Stock(ProcessedProductType.Coal, -1, 150));
        return blacksmithStore;
    }

    private static ArrayList<Stock> getMarnieRanchStock() {
        ArrayList<Stock> marnieShop = new ArrayList<>();
        marnieShop.add(new Stock(ShopItems.Hay, -1, 50));
        marnieShop.add(new Stock(ToolType.MilkPail, ToolType.MilkPail.getLevel(), 1, 1000));
        marnieShop.add(new Stock(ToolType.Shear, ToolType.Shear.getLevel(), 1, 1000));
        marnieShop.add(new Stock(AnimalType.Chicken, 2, 800));
        marnieShop.add(new Stock(AnimalType.Cow, 2, 1500));
        marnieShop.add(new Stock(AnimalType.Goat, 2, 4000));
        marnieShop.add(new Stock(AnimalType.Duck, 2, 1200));
        marnieShop.add(new Stock(AnimalType.Sheep, 2, 8000));
        marnieShop.add(new Stock(AnimalType.Rabbit, 2, 8000));
        marnieShop.add(new Stock(AnimalType.Dinosaur, 2, 14000));
        marnieShop.add(new Stock(AnimalType.Pig, 2, 16000));
        return marnieShop;
    }

    private static ArrayList<Stock> getStardropSaloonStock() {
        ArrayList<Stock> stardropSaloon = new ArrayList<>();
        stardropSaloon.add(new Stock(ProcessedProductType.Beer, -1, 400));
        stardropSaloon.add(new Stock(CookingProduct.Salad, -1, 220));
        stardropSaloon.add(new Stock(CookingProduct.Bread, -1, 120));
        stardropSaloon.add(new Stock(CookingProduct.Spaghetti, -1, 240));
        stardropSaloon.add(new Stock(CookingProduct.Pizza, -1, 600));
        stardropSaloon.add(new Stock(ProcessedProductType.Coffee, -1, 300));
        stardropSaloon.add(new Stock(Recipe.HashbrownsRecipe, 1, 50));
        stardropSaloon.add(new Stock(Recipe.OmeletteRecipe, 1, 100));
        stardropSaloon.add(new Stock(Recipe.PancakeRecipe, 1, 100));
        stardropSaloon.add(new Stock(Recipe.BreadRecipe, 1, 100));
        stardropSaloon.add(new Stock(Recipe.TortillaRecipe, 1, 100));
        stardropSaloon.add(new Stock(Recipe.PizzaRecipe, 1, 150));
        stardropSaloon.add(new Stock(Recipe.MakiRollRecipe, 1, 300));
        stardropSaloon.add(new Stock(Recipe.TripleShotEspressoRecipe, 1, 5000));
        stardropSaloon.add(new Stock(Recipe.CookieRecipe, 1, 300));
        return stardropSaloon;
    }

    private static ArrayList<Stock> getCarpenterShopStock() {
        ArrayList<Stock> carpenterShop = new ArrayList<>();
        carpenterShop.add(new Stock(MineralType.Wood, -1, 10));
        carpenterShop.add(new Stock(MineralType.Stone, -1, 20));
        carpenterShop.add(new Stock(BuildingType.Barn, 1, 6000));
        carpenterShop.add(new Stock(BuildingType.BigBarn, 1, 12000));
        carpenterShop.add(new Stock(BuildingType.DeluxeBarn, 1, 25000));
        carpenterShop.add(new Stock(BuildingType.Coop, 1, 4000));
        carpenterShop.add(new Stock(BuildingType.BigCoop, 1, 10000));
        carpenterShop.add(new Stock(BuildingType.DeluxeCoop, 1, 20000));
        carpenterShop.add(new Stock(BuildingType.Well, 1, 1000));
        carpenterShop.add(new Stock(BuildingType.ShippingBin, -1, 250));
        return carpenterShop;
    }

    private static ArrayList<Stock> getPermanentJojaMartStock() {
        ArrayList<Stock> jojaMart = new ArrayList<>();
        jojaMart.add(new Stock(ShopItems.JojaCola, -1, 75));
        jojaMart.add(new Stock(SeedType.AncientSeed, 1, 500));
        jojaMart.add(new Stock(SeedType.GrassStater, -1, 125));
        jojaMart.add(new Stock(ShopItems.Sugar, -1, 125));
        jojaMart.add(new Stock(ShopItems.WheatFlour, -1, 125));
        jojaMart.add(new Stock(ShopItems.Rice, -1, 250));
        return jojaMart;
    }

    private static ArrayList<Stock> getSpringJojaMartStock() {
        ArrayList<Stock> springJojaMart = new ArrayList<>();
        springJojaMart.add(new Stock(SeedType.ParsnipSeed, 5, 25));
        springJojaMart.add(new Stock(SeedType.BeanStarter, 5, 75));
        springJojaMart.add(new Stock(SeedType.CauliflowerSeed, 5, 100));
        springJojaMart.add(new Stock(SeedType.PotatoSeed, 5, 62));
        springJojaMart.add(new Stock(SeedType.StrawberrySeed, 5, 100));
        springJojaMart.add(new Stock(SeedType.TulipBulbSeed, 5, 25));
        springJojaMart.add(new Stock(SeedType.KaleSeed, 5, 87));
        springJojaMart.add(new Stock(SeedType.CoffeeBean, 1, 200));
        springJojaMart.add(new Stock(SeedType.CarrotSeed, 10, 5));
        springJojaMart.add(new Stock(SeedType.RhubarbSeed, 5, 100));
        springJojaMart.add(new Stock(SeedType.JazzSeed, 5, 37));
        return springJojaMart;
    }

    private static ArrayList<Stock> getSummerJojaMartStock() {
        ArrayList<Stock> summerMartJojaMart = new ArrayList<>();
        summerMartJojaMart.add(new Stock(SeedType.TomatoSeed, 5, 62));
        summerMartJojaMart.add(new Stock(SeedType.PepperSeed, 5, 50));
        summerMartJojaMart.add(new Stock(SeedType.WheatSeed, 10, 12));
        summerMartJojaMart.add(new Stock(SeedType.SummerSquashSeed, 10, 10));
        summerMartJojaMart.add(new Stock(SeedType.RadishSeed, 5, 50));
        summerMartJojaMart.add(new Stock(SeedType.MelonSeed, 5, 100));
        summerMartJojaMart.add(new Stock(SeedType.HopsStarter, 5, 75));
        summerMartJojaMart.add(new Stock(SeedType.PoppySeed, 5, 125));
        summerMartJojaMart.add(new Stock(SeedType.SpangleSeed, 5, 62));
        summerMartJojaMart.add(new Stock(SeedType.StarfruitSeed, 5, 400));
        summerMartJojaMart.add(new Stock(SeedType.CoffeeBean, 1, 200));
        summerMartJojaMart.add(new Stock(SeedType.SunflowerSeed, 5, 125));
        return summerMartJojaMart;
    }

    private static ArrayList<Stock> getFallJojaMartStock() {
        ArrayList<Stock> fallMartJojaMart = new ArrayList<>();
        fallMartJojaMart.add(new Stock(SeedType.CornSeed, 5, 187));
        fallMartJojaMart.add(new Stock(SeedType.EggplantSeed, 5, 25));
        fallMartJojaMart.add(new Stock(SeedType.PumpkinSeed, 5, 125));
        fallMartJojaMart.add(new Stock(SeedType.BroccoliSeed, 5, 15));
        fallMartJojaMart.add(new Stock(SeedType.AmaranthSeed, 5, 87));
        fallMartJojaMart.add(new Stock(SeedType.GrapeStarter, 5, 75));
        fallMartJojaMart.add(new Stock(SeedType.BeetSeed, 5, 20));
        fallMartJojaMart.add(new Stock(SeedType.YamSeed, 5, 75));
        fallMartJojaMart.add(new Stock(SeedType.BokChoySeed, 5, 62));
        fallMartJojaMart.add(new Stock(SeedType.CranberrySeed, 5, 300));
        fallMartJojaMart.add(new Stock(SeedType.SunflowerSeed, 5, 125));
        fallMartJojaMart.add(new Stock(SeedType.FairySeed, 5, 250));
        fallMartJojaMart.add(new Stock(SeedType.RareSeed, 1, 1000));
        fallMartJojaMart.add(new Stock(SeedType.WheatSeed, 5, 12));
        return fallMartJojaMart;
    }

    private static ArrayList<Stock> getWinterJojaMartStock() {
        ArrayList<Stock> winterJojaMart = new ArrayList<>();
        winterJojaMart.add(new Stock(SeedType.PowderMelonSeed, 10, 20));
        return winterJojaMart;
    }

    private static ArrayList<Stock> getPermanentPierreGeneralStoreStock() {
        ArrayList<Stock> permanentPierreGeneralStore = new ArrayList<>();
        permanentPierreGeneralStore.add(new Stock(ShopItems.Rice, -1, 200));
        permanentPierreGeneralStore.add(new Stock(ShopItems.WheatFlour, -1, 100));
        permanentPierreGeneralStore.add(new Stock(ShopItems.Bouquet, 2, 1000));
        permanentPierreGeneralStore.add(new Stock(ShopItems.WeddingRing, 2, 10000));
        permanentPierreGeneralStore.add(new Stock(Recipe.DehydratorRecipe, 1, 10000));
        permanentPierreGeneralStore.add(new Stock(Recipe.GrassStarterRecipe, 1, 1000));
        permanentPierreGeneralStore.add(new Stock(ShopItems.Sugar, -1, 100));
        permanentPierreGeneralStore.add(new Stock(ProcessedProductType.Oil, -1, 200));
        permanentPierreGeneralStore.add(new Stock(ProcessedProductType.Vinegar, -1, 200));
        permanentPierreGeneralStore.add(new Stock(ShopItems.DeluxeRetainingSoil, -1, 150));
        permanentPierreGeneralStore.add(new Stock(SeedType.GrassStater, -1, 100));
        permanentPierreGeneralStore.add(new Stock(ShopItems.SpeedGro, -1, 100));
        permanentPierreGeneralStore.add(new Stock(SaplingType.AppleSapling, -1, 4000));
        permanentPierreGeneralStore.add(new Stock(SaplingType.ApricotSapling, -1, 2000));
        permanentPierreGeneralStore.add(new Stock(SaplingType.CherrySapling, -1, 3400));
        permanentPierreGeneralStore.add(new Stock(SaplingType.OrangeSapling, -1, 4000));
        permanentPierreGeneralStore.add(new Stock(SaplingType.PeachSapling, -1, 6000));
        permanentPierreGeneralStore.add(new Stock(SaplingType.PomegranateSapling, -1, 6000));
        permanentPierreGeneralStore.add(new Stock(ShopItems.BasicRetainingSoil, -1, 100));
        permanentPierreGeneralStore.add(new Stock(ShopItems.QualityRetainingSoil, -1, 150));
        permanentPierreGeneralStore.add(new Stock(ToolType.LargeBackpack, ToolType.LargeBackpack.getLevel(),
                1, 2000));
        permanentPierreGeneralStore.add(new Stock(ToolType.DeluxeBackpack, ToolType.DeluxeBackpack.getLevel(),
                1, 10000));
        return permanentPierreGeneralStore;
    }

    private static ArrayList<Stock> getSpringPierreGeneralStoreStock() {
        ArrayList<Stock> springPierreGeneralStoreStock = new ArrayList<>();
        springPierreGeneralStoreStock.add(new Stock(SeedType.ParsnipSeed, 5, 30, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.BeanStarter, 5, 90, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.CauliflowerSeed, 5, 120, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.PotatoSeed, 5, 75, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.TulipBulbSeed, 5, 30, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.KaleSeed, 5, 105, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.JazzSeed, 5, 45, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.GarlicSeed, 5, 60, Season.Spring));
        springPierreGeneralStoreStock.add(new Stock(SeedType.RiceShoot, 5, 60, Season.Spring));
        return springPierreGeneralStoreStock;
    }

    private static ArrayList<Stock> getSummerPierreGeneralStoreStock() {
        ArrayList<Stock> summerPierreGeneralStoreStock = new ArrayList<>();
        summerPierreGeneralStoreStock.add(new Stock(SeedType.MelonSeed, 5, 120, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.TomatoSeed, 5, 75, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.BlueberrySeed, 5, 120, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.PepperSeed, 5, 60, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.WheatSeed, 5, 15, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.RadishSeed, 5, 60, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.PoppySeed, 5, 150, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.SpangleSeed, 5, 75, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.HopsStarter, 5, 90, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.CornSeed, 5, 225, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.SunflowerSeed, 5, 300, Season.Summer));
        summerPierreGeneralStoreStock.add(new Stock(SeedType.RedCabbageSeed, 5, 105, Season.Summer));
        return summerPierreGeneralStoreStock;
    }

    private static ArrayList<Stock> getFallPierreGeneralStoreStock() {
        ArrayList<Stock> fallPierreGeneralStoreStock = new ArrayList<>();
        fallPierreGeneralStoreStock.add(new Stock(SeedType.EggplantSeed, 5, 30, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.CornSeed, 5, 225, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.PumpkinSeed, 5, 150, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.BokChoySeed, 5, 75, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.YamSeed, 5, 90, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.CranberrySeed, 5, 360, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.SunflowerSeed, 5, 300, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.FairySeed, 5, 300, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.AmaranthSeed, 5, 105, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.GrapeStarter, 5, 90, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.WheatSeed, 5, 15, Season.Fall));
        fallPierreGeneralStoreStock.add(new Stock(SeedType.ArtichokeSeed, 5, 45, Season.Fall));
        return fallPierreGeneralStoreStock;
    }

    private static ArrayList<Stock> getFishShopStock() {
        ArrayList<Stock> fishShopStock = new ArrayList<>();
        fishShopStock.add(new Stock(Recipe.FishSmokerRecipe, 1, 10000));
        fishShopStock.add(new Stock(ShopItems.TroutSoup, 1, 250));
        fishShopStock.add(new Stock(ToolType.BambooPole, ToolType.BambooPole.getLevel(), 1, 500));
        fishShopStock.add(new Stock(ToolType.TrainingRod, ToolType.TrainingRod.getLevel(), 1, 25));
        fishShopStock.add(new Stock(ToolType.FiberglassRod, ToolType.FiberglassRod.getLevel(), 1, 1800));
        fishShopStock.add(new Stock(ToolType.IridiumRod, ToolType.IridiumRod.getLevel(), 1, 7500));
        return fishShopStock;
    }

    public ArrayList<Stock> getStock() {
        return stock;
    }
}
