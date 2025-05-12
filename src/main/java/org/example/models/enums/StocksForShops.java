package org.example.models.enums;

import org.example.models.enums.Plants.SaplingType;
import org.example.models.enums.Plants.SeedType;
import org.example.models.Stacks;
import org.example.models.enums.items.*;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.ProcessedProductType;

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
    PermanentPierreGeneralStoreStock(getPermanentPierreGeneralStoreStock()),
    SpringPierreGeneralStoreStock(getSpringPierreGeneralStoreStock()),
    SummerPierreGeneralStoreStock(getSummerPierreGeneralStoreStock()),
    WinterPierreGeneralStoreStock(new ArrayList<Stacks>()),
    FallPierreGeneralStoreStock(getFallPierreGeneralStoreStock()),
    FishShopStock(getFishShopStock());

    private ArrayList<Stacks> stacks;

    static {
        ArrayList<Stacks> permanentJojaMartStock = getPermanentJojaMartStock();
        SpringJojaMartStock.stacks.addAll(permanentJojaMartStock);
        SummerJojaMartStock.stacks.addAll(permanentJojaMartStock);
        FallJojaMartStock.stacks.addAll(permanentJojaMartStock);
        WinterJojaMartStock.stacks.addAll(permanentJojaMartStock);

        ArrayList<Stacks> permanentPierreGeneralStoreStock = getPermanentPierreGeneralStoreStock();
        SpringPierreGeneralStoreStock.stacks.addAll(permanentPierreGeneralStoreStock);
        SummerPierreGeneralStoreStock.stacks.addAll(permanentPierreGeneralStoreStock);
        FallPierreGeneralStoreStock.stacks.addAll(permanentPierreGeneralStoreStock);
        WinterPierreGeneralStoreStock.stacks.addAll(permanentPierreGeneralStoreStock);
    }

    StocksForShops(ArrayList<Stacks> stacks) {
        this.stacks = stacks;
    }

    public ArrayList<Stacks> getStacks() {
        return stacks;
    }

    //Limits are daily and restock every morning
    //-1 means unlimited
    private static ArrayList<Stacks> getBlacksmithStoreStock() {
        ArrayList<Stacks> blacksmithStore = new ArrayList<>();
        blacksmithStore.add(new Stacks(MineralType.CopperOre, -1));
        blacksmithStore.add(new Stacks(MineralType.IronOre, -1));
        blacksmithStore.add(new Stacks(MineralType.GoldOre, -1));
        blacksmithStore.add(new Stacks(ProcessedProductType.Coal, -1));
        return blacksmithStore;
    }

    private static ArrayList<Stacks> getMarnieRanchStock() {
        ArrayList<Stacks> marnieShop = new ArrayList<>();
        marnieShop.add(new Stacks(ShopItems.Hay, -1));
        marnieShop.add(new Stacks(ToolType.MilkPail, ToolType.MilkPail.getLevel(), 1));
        marnieShop.add(new Stacks(ToolType.Shear, ToolType.Shear.getLevel(), 1));
        marnieShop.add(new Stacks(AnimalType.Chicken, 2));
        marnieShop.add(new Stacks(AnimalType.Cow, 2));
        marnieShop.add(new Stacks(AnimalType.Goat, 2));
        marnieShop.add(new Stacks(AnimalType.Duck, 2));
        marnieShop.add(new Stacks(AnimalType.Sheep, 2));
        marnieShop.add(new Stacks(AnimalType.Rabbit, 2));
        marnieShop.add(new Stacks(AnimalType.Dinosaur, 2));
        marnieShop.add(new Stacks(AnimalType.Pig, 2));
        return marnieShop;
    }

    private static ArrayList<Stacks> getStardropSaloonStock() {
        ArrayList<Stacks> stardropSaloon = new ArrayList<>();
        stardropSaloon.add(new Stacks(ProcessedProductType.Beer, -1));
        stardropSaloon.add(new Stacks(CookingProduct.Salad, -1));
        stardropSaloon.add(new Stacks(CookingProduct.Bread, -1));
        stardropSaloon.add(new Stacks(CookingProduct.Spaghetti, -1));
        stardropSaloon.add(new Stacks(CookingProduct.Pizza, -1));
        stardropSaloon.add(new Stacks(ProcessedProductType.Coffee, -1));
        stardropSaloon.add(new Stacks(Recipe.HashbrownsRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.OmeletteRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.PancakeRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.BreadRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.TortillaRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.PizzaRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.MakiRollRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.TripleShotEspressoRecipe, 1));
        stardropSaloon.add(new Stacks(Recipe.CookieRecipe, 1));
        return stardropSaloon;
    }

    private static ArrayList<Stacks> getCarpenterShopStock() {
        ArrayList<Stacks> carpenterShop = new ArrayList<>();
        carpenterShop.add(new Stacks(MineralType.Wood, -1));
        carpenterShop.add(new Stacks(MineralType.Stone, -1));
        carpenterShop.add(new Stacks(BuildingType.Barn, 1));
        carpenterShop.add(new Stacks(BuildingType.BigBarn, 1));
        carpenterShop.add(new Stacks(BuildingType.DeluxeBarn, 1));
        carpenterShop.add(new Stacks(BuildingType.Coop, 1));
        carpenterShop.add(new Stacks(BuildingType.BigCoop, 1));
        carpenterShop.add(new Stacks(BuildingType.DeluxeCoop, 1));
        carpenterShop.add(new Stacks(BuildingType.Well, 1));
        carpenterShop.add(new Stacks(BuildingType.ShippingBin, -1));
        return carpenterShop;
    }

    private static ArrayList<Stacks> getPermanentJojaMartStock() {
        ArrayList<Stacks> jojaMart = new ArrayList<>();
        jojaMart.add(new Stacks(ShopItems.JojaCola, -1));
        jojaMart.add(new Stacks(SeedType.AncientSeed, 1));
        jojaMart.add(new Stacks(SeedType.GrassStater, -1));
        jojaMart.add(new Stacks(ShopItems.Sugar, -1));
        jojaMart.add(new Stacks(ShopItems.WheatFlour, -1));
        jojaMart.add(new Stacks(ShopItems.Rice, -1));
        return jojaMart;
    }

    private static ArrayList<Stacks> getSpringJojaMartStock() {
        ArrayList<Stacks> springJojaMart = new ArrayList<>();
        springJojaMart.add(new Stacks(SeedType.ParsnipSeed, 5));
        springJojaMart.add(new Stacks(SeedType.BeanStarter, 5));
        springJojaMart.add(new Stacks(SeedType.CauliflowerSeed, 5));
        springJojaMart.add(new Stacks(SeedType.PotatoSeed, 5));
        springJojaMart.add(new Stacks(SeedType.StrawberrySeed, 5));
        springJojaMart.add(new Stacks(SeedType.TulipBulbSeed, 5));
        springJojaMart.add(new Stacks(SeedType.KaleSeed, 5));
        springJojaMart.add(new Stacks(SeedType.CoffeeBean, 1));
        springJojaMart.add(new Stacks(SeedType.CarrotSeed, 10));
        springJojaMart.add(new Stacks(SeedType.RhubarbSeed, 5));
        springJojaMart.add(new Stacks(SeedType.JazzSeed, 5));
        return springJojaMart;
    }

    private static ArrayList<Stacks> getSummerJojaMartStock() {
        ArrayList<Stacks> summerMartJojaMart = new ArrayList<>();
        summerMartJojaMart.add(new Stacks(SeedType.TomatoSeed, 5));
        summerMartJojaMart.add(new Stacks(SeedType.PepperSeed, 5));
        summerMartJojaMart.add(new Stacks(SeedType.WheatSeed, 10));
        summerMartJojaMart.add(new Stacks(SeedType.SummerSquashSeed, 10));
        summerMartJojaMart.add(new Stacks(SeedType.RadishSeed, 5));
        summerMartJojaMart.add(new Stacks(SeedType.MelonSeed, 5));
        summerMartJojaMart.add(new Stacks(SeedType.HopsStarter, 5));
        summerMartJojaMart.add(new Stacks(SeedType.PoppySeed, 5));
        summerMartJojaMart.add(new Stacks(SeedType.SpangleSeed, 5));
        summerMartJojaMart.add(new Stacks(SeedType.StarfruitSeed, 5));
        summerMartJojaMart.add(new Stacks(SeedType.CoffeeBean, 1));
        summerMartJojaMart.add(new Stacks(SeedType.SunflowerSeed, 5));
        return summerMartJojaMart;
    }

    private static ArrayList<Stacks> getFallJojaMartStock() {
        ArrayList<Stacks> fallMartJojaMart = new ArrayList<>();
        fallMartJojaMart.add(new Stacks(SeedType.CornSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.EggplantSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.PumpkinSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.BroccoliSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.AmaranthSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.GrapeStarter, 5));
        fallMartJojaMart.add(new Stacks(SeedType.BeetSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.YamSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.BokChoySeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.CranberrySeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.SunflowerSeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.FairySeed, 5));
        fallMartJojaMart.add(new Stacks(SeedType.RareSeed, 1));
        fallMartJojaMart.add(new Stacks(SeedType.WheatSeed, 5));
        return fallMartJojaMart;
    }

    private static ArrayList<Stacks> getWinterJojaMartStock() {
        ArrayList<Stacks> winterJojaMart = new ArrayList<>();
        winterJojaMart.add(new Stacks(SeedType.PowderMelonSeed, 10));
        return winterJojaMart;
    }

    private static ArrayList<Stacks> getPermanentPierreGeneralStoreStock() {
        ArrayList<Stacks> permanentPierreGeneralStore = new ArrayList<>();
        permanentPierreGeneralStore.add(new Stacks(ShopItems.Rice, -1));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.WheatFlour, -1));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.Bouquet, 2));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.WeddingRing, 2));
        permanentPierreGeneralStore.add(new Stacks(Recipe.DehydratorRecipe, 1));
        permanentPierreGeneralStore.add(new Stacks(Recipe.GrassStarterRecipe, 1));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.Sugar, -1));
        permanentPierreGeneralStore.add(new Stacks(ProcessedProductType.Oil, -1));
        permanentPierreGeneralStore.add(new Stacks(ProcessedProductType.Vinegar, -1));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.DeluxeRetainingSoil, -1));
        permanentPierreGeneralStore.add(new Stacks(SeedType.GrassStater, -1));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.SpeedGro, -1));
        permanentPierreGeneralStore.add(new Stacks(SaplingType.AppleSapling, -1));
        permanentPierreGeneralStore.add(new Stacks(SaplingType.ApricotSapling, -1));
        permanentPierreGeneralStore.add(new Stacks(SaplingType.CherrySapling, -1));
        permanentPierreGeneralStore.add(new Stacks(SaplingType.OrangeSapling, -1));
        permanentPierreGeneralStore.add(new Stacks(SaplingType.PeachSapling, -1));
        permanentPierreGeneralStore.add(new Stacks(SaplingType.PomegranateSapling, -1));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.BasicRetainingSoil, -1));
        permanentPierreGeneralStore.add(new Stacks(ShopItems.QualityRetainingSoil, -1));
        permanentPierreGeneralStore.add(new Stacks(ToolType.LargeBackpack, ToolType.LargeBackpack.getLevel(), 1));
        permanentPierreGeneralStore.add(new Stacks(ToolType.DeluxeBackpack, ToolType.DeluxeBackpack.getLevel(), 1));
        return permanentPierreGeneralStore;
    }

    private static ArrayList<Stacks> getSpringPierreGeneralStoreStock() {
        ArrayList<Stacks> springPierreGeneralStoreStock = new ArrayList<>();
        springPierreGeneralStoreStock.add(new Stacks(SeedType.ParsnipSeed, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.BeanStarter, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.CauliflowerSeed, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.PotatoSeed, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.TulipBulbSeed, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.KaleSeed, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.JazzSeed, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.GarlicSeed, 5));
        springPierreGeneralStoreStock.add(new Stacks(SeedType.RiceShoot, 5));
        return springPierreGeneralStoreStock;
    }

    private static ArrayList<Stacks> getSummerPierreGeneralStoreStock() {
        ArrayList<Stacks> summerPierreGeneralStoreStock = new ArrayList<>();
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.MelonSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.TomatoSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.BlueberrySeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.PepperSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.WheatSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.RadishSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.PoppySeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.SpangleSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.HopsStarter, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.CornSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.SunflowerSeed, 5));
        summerPierreGeneralStoreStock.add(new Stacks(SeedType.RedCabbageSeed, 5));
        return summerPierreGeneralStoreStock;
    }


    private static ArrayList<Stacks> getFallPierreGeneralStoreStock() {
        ArrayList<Stacks> fallPierreGeneralStoreStock = new ArrayList<>();
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.EggplantSeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.CornSeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.PumpkinSeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.BokChoySeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.YamSeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.CranberrySeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.SunflowerSeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.FairySeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.AmaranthSeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.GrapeStarter, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.WheatSeed, 5));
        fallPierreGeneralStoreStock.add(new Stacks(SeedType.ArtichokeSeed, 5));
        return fallPierreGeneralStoreStock;
    }

    private static ArrayList<Stacks> getFishShopStock() {
        ArrayList<Stacks> fishShopStock = new ArrayList<>();
        fishShopStock.add(new Stacks(Recipe.FishSmokerRecipe, 1));
        fishShopStock.add(new Stacks(ShopItems.TroutSoup, 1));
        fishShopStock.add(new Stacks(ToolType.BambooPole, ToolType.BambooPole.getLevel(), 1));
        fishShopStock.add(new Stacks(ToolType.TrainingRod, ToolType.TrainingRod.getLevel(), 1));
        fishShopStock.add(new Stacks(ToolType.FiberglassRod,ToolType.FiberglassRod.getLevel(), 1));
        fishShopStock.add(new Stacks(ToolType.IridiumRod, ToolType.IridiumRod.getLevel(), 1));
        return fishShopStock;
    }
}
