package org.example.models.enums;

import org.example.models.Stack;
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

    private ArrayList<Stack> stacks;

    StocksForShops(ArrayList<Stack> stacks) {
        this.stacks = stacks;
    }

    public ArrayList<Stack> getStacks() {
        return stacks;
    }

    //Limits are daily and restock every morning
    //-1 means unlimited
    private static ArrayList<Stack> BlacksmithStoreStock() {
        ArrayList<Stack> BlacksmithStore = new ArrayList<>();
        BlacksmithStore.add(new Stack(MineralType.CopperOre, -1));
        BlacksmithStore.add(new Stack(MineralType.IronOre, -1));
        BlacksmithStore.add(new Stack(MineralType.GoldOre, -1));
        BlacksmithStore.add(new Stack(ProcessedProductType.Coal, -1));
        return BlacksmithStore;
    }

    private static ArrayList<Stack> MarnieShopStock() {
        ArrayList<Stack> MarnieShop = new ArrayList<>();
        MarnieShop.add(new Stack(ShopItems.Hay , -1));
        MarnieShop.add(new Stack(ToolType.MilkPail , 1));
        MarnieShop.add(new Stack(ToolType.Shear , 1));
        MarnieShop.add(new Stack(AnimalType.Chicken , 2));
        MarnieShop.add(new Stack(AnimalType.Cow , 2));
        MarnieShop.add(new Stack(AnimalType.Goat , 2));
        MarnieShop.add(new Stack(AnimalType.Duck , 2));
        MarnieShop.add(new Stack(AnimalType.Sheep , 2));
        MarnieShop.add(new Stack(AnimalType.Rabbit , 2));
        MarnieShop.add(new Stack(AnimalType.Dinosaur , 2));
        MarnieShop.add(new Stack(AnimalType.Pig , 2));
        return MarnieShop;
    }

    private static ArrayList<Stack> StardropSaloonStock() {
        ArrayList<Stack> StardropSaloon = new ArrayList<>();
        StardropSaloon.add(new Stack(ProcessedProductType.Beer , -1));
        StardropSaloon.add(new Stack(CookingProduct.Salad , -1));
        StardropSaloon.add(new Stack(CookingProduct.Bread , -1));
        StardropSaloon.add(new Stack(CookingProduct.Spaghetti , -1));
        StardropSaloon.add(new Stack(CookingProduct.Pizza, -1));
        StardropSaloon.add(new Stack(ProcessedProductType.Coffee , -1));
        StardropSaloon.add(new Stack(Recipe.HashbrownsRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.OmeletteRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.PancakeRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.BreadRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.TortillaRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.PizzaRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.MakiRollRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.TripleShotEspressoRecipe, 1));
        StardropSaloon.add(new Stack(Recipe.CookieRecipe, 1));
        return StardropSaloon;
    }

    private static ArrayList<Stack> CarpenterShopStock() {
        ArrayList<Stack> CarpenterShop = new ArrayList<>();
        CarpenterShop.add(new Stack(MineralType.Wood, -1));
        CarpenterShop.add(new Stack(MineralType.Stone, -1));
        CarpenterShop.add(new Stack(FarmBuildingType.Barn, 1));
        CarpenterShop.add(new Stack(FarmBuildingType.BigBarn, 1));
        CarpenterShop.add(new Stack(FarmBuildingType.DeluxeBarn, 1));
        CarpenterShop.add(new Stack(FarmBuildingType.Coop, 1));
        CarpenterShop.add(new Stack(FarmBuildingType.BigCoop, 1));
        CarpenterShop.add(new Stack(FarmBuildingType.DeluxeCoop, 1));
        CarpenterShop.add(new Stack(FarmBuildingType.Well, 1));
        CarpenterShop.add(new Stack(FarmBuildingType.ShippingBin, -1));
        return CarpenterShop;
    }

    private static ArrayList<Stack> PermanentJojaMartStock() {
        ArrayList<Stack> JojaMart = new ArrayList<>();
        JojaMart.add(new Stack(ShopItems.JojaCola , -1));
        JojaMart.add(new Stack(SeedType.AncientSeed , 1));
        JojaMart.add(new Stack(SeedType.GrassStater , -1));
        JojaMart.add(new Stack(ShopItems.Sugar , -1));
        JojaMart.add(new Stack(ShopItems.WheatFlour , -1));
        JojaMart.add(new Stack(ProcessedProductType.Rice , -1));
        return JojaMart;
    }

    private static ArrayList<Stack> SpringJojaMartStock() {
        ArrayList<Stack> SpringJojaMart = new ArrayList<>();
        SpringJojaMart.add(new Stack(SeedType.ParsnipSeed , 5));
        SpringJojaMart.add(new Stack(SeedType.BeanStarter , 5));
        SpringJojaMart.add(new Stack(SeedType.CauliflowerSeed , 5));
        SpringJojaMart.add(new Stack(SeedType.PotatoSeed , 5));
        SpringJojaMart.add(new Stack(SeedType.StrawberrySeed , 5));
        SpringJojaMart.add(new Stack(SeedType.TulipBulbSeed , 5));
        SpringJojaMart.add(new Stack(SeedType.KaleSeed , 5));
        SpringJojaMart.add(new Stack(SeedType.CoffeeBean, 1));
        SpringJojaMart.add(new Stack(SeedType.CarrotSeed , 10));
        SpringJojaMart.add(new Stack(SeedType.RhubarbSeed , 5));
        SpringJojaMart.add(new Stack(SeedType.JazzSeed , 5));
        return SpringJojaMart;
    }

    private static ArrayList<Stack> SummerMartJojaMartStock() {
        ArrayList<Stack> SummerMartJojaMart = new ArrayList<>();
        SummerMartJojaMart.add(new Stack(SeedType.TomatoSeed , 5));
        SummerMartJojaMart.add(new Stack(SeedType.PepperSeed , 5));
        SummerMartJojaMart.add(new Stack(SeedType.WheatSeed , 10));
        SummerMartJojaMart.add(new Stack(SeedType.SummerSquashSeed , 10));
        SummerMartJojaMart.add(new Stack(SeedType.RadishSeed , 5));
        SummerMartJojaMart.add(new Stack(SeedType.MelonSeed , 5));
        SummerMartJojaMart.add(new Stack(SeedType.HopsStarter , 5));
        SummerMartJojaMart.add(new Stack(SeedType.PoppySeed , 5));
        SummerMartJojaMart.add(new Stack(SeedType.SpangleSeed , 5));
        SummerMartJojaMart.add(new Stack(SeedType.StarfruitSeed , 5));
        SummerMartJojaMart.add(new Stack(SeedType.CoffeeBean, 1));
        SummerMartJojaMart.add(new Stack(SeedType.SunflowerSeed , 5));
        return SummerMartJojaMart;
    }

    private static ArrayList<Stack> FallMartJojaMartStock() {
        ArrayList<Stack> FallMartJojaMart = new ArrayList<>();
        FallMartJojaMart.add(new Stack(SeedType.CornSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.EggplantSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.PumpkinSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.BroccoliSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.AmaranthSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.GrapeStarter , 5));
        FallMartJojaMart.add(new Stack(SeedType.BeetSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.YamSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.BokChoySeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.CranberrySeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.SunflowerSeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.FairySeed , 5));
        FallMartJojaMart.add(new Stack(SeedType.RareSeed , 1));
        FallMartJojaMart.add(new Stack(SeedType.WheatSeed , 5));
        return FallMartJojaMart;
    }

    private static ArrayList<Stack> WinterMartJojaMartStock() {
        ArrayList<Stack> WinterMartJojaMart = new ArrayList<>();
        WinterMartJojaMart.add(new Stack(SeedType.PowderMelonSeed , 10));
        return WinterMartJojaMart;
    }

    private static ArrayList<Stack> PermanentPierreGeneralStoreStock(){
        ArrayList<Stack> PermanentPierreGeneralStore = new ArrayList<>();
        PermanentPierreGeneralStore.add(new Stack(ProcessedProductType.Rice , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.WheatFlour , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.Bouquet , 2));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.WeddingRing , 2));
        PermanentPierreGeneralStore.add(new Stack(Recipe.DehydratorRecipe , 1));
        PermanentPierreGeneralStore.add(new Stack(Recipe.GrassStarterRecipe , 1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.Sugar , -1));
        PermanentPierreGeneralStore.add(new Stack(ProcessedProductType.Oil , -1));
        PermanentPierreGeneralStore.add(new Stack(ProcessedProductType.Vinegar , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.BasicFertilizer , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.QualityFertilizer , -1));
        PermanentPierreGeneralStore.add(new Stack(SeedType.GrassStater , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.SpeedGro , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.DeluxeSpeedGro , -1));
        PermanentPierreGeneralStore.add(new Stack(SaplingType.AppleSapling , -1));
        PermanentPierreGeneralStore.add(new Stack(SaplingType.ApricotSapling , -1));
        PermanentPierreGeneralStore.add(new Stack(SaplingType.CherrySapling , -1));
        PermanentPierreGeneralStore.add(new Stack(SaplingType.OrangeSapling , -1));
        PermanentPierreGeneralStore.add(new Stack(SaplingType.PeachSapling , -1));
        PermanentPierreGeneralStore.add(new Stack(SaplingType.PomegranateSapling , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.BasicRetainingSoil , -1));
        PermanentPierreGeneralStore.add(new Stack(ShopItems.QualityRetainingSoil , -1));
        PermanentPierreGeneralStore.add(new Stack(ToolType.LargeBackPack , 1));
        PermanentPierreGeneralStore.add(new Stack(ToolType.LargeBackPack , 1));
        return PermanentPierreGeneralStore;
    }

    private static ArrayList<Stack> PierreGeneralStoreBackpack(){
        ArrayList<Stack> PierreGeneralStoreBackpack = new ArrayList<>();
        PierreGeneralStoreBackpack.add(new Stack(ToolType.LargeBackPack , 1));
        PierreGeneralStoreBackpack.add(new Stack(ToolType.DeluxeBackPack , 1));
        return PierreGeneralStoreBackpack;
    }

    private static ArrayList<Stack> SpringPierreGeneralStoreStock(){
        ArrayList<Stack> SpringPierreGeneralStoreStock = new ArrayList<>();
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.ParsnipSeed , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.BeanStarter , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.CauliflowerSeed , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.PotatoSeed , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.TulipBulbSeed , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.KaleSeed , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.JazzSeed , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.GarlicSeed , 5));
        SpringPierreGeneralStoreStock.add(new Stack(SeedType.RiceShoot , 5));
        return SpringPierreGeneralStoreStock;
    }

    private static ArrayList<Stack> SummerPierreGeneralStoreStock(){
        ArrayList<Stack> SummerPierreGeneralStoreStock = new ArrayList<>();
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.MelonSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.TomatoSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.BlueberrySeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.PepperSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.WheatSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.RadishSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.PoppySeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.SpangleSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.HopsStarter , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.CornSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.SunflowerSeed , 5));
        SummerPierreGeneralStoreStock.add(new Stack(SeedType.RedCabbageSeed , 5));
        return SummerPierreGeneralStoreStock;
    }


    private static ArrayList<Stack> FallPierreGeneralStoreStock(){
        ArrayList<Stack> FallPierreGeneralStoreStock = new ArrayList<>();
        FallPierreGeneralStoreStock.add(new Stack(SeedType.EggplantSeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.CornSeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.PumpkinSeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.BokChoySeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.YamSeed, 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.CranberrySeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.SunflowerSeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.FairySeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.AmaranthSeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.GrapeStarter , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.WheatSeed , 5));
        FallPierreGeneralStoreStock.add(new Stack(SeedType.ArtichokeSeed , 5));
        return FallPierreGeneralStoreStock;
    }

    private static ArrayList<Stack> FishShopStock(){
        ArrayList<Stack> FishShopStock = new ArrayList<>();
        FishShopStock.add(new Stack(Recipe.FishSmokerRecipe , 1));
        FishShopStock.add(new Stack(ShopItems.TroutSoup , 1));
        FishShopStock.add(new Stack(ToolType.BambooPole , 1));
        FishShopStock.add(new Stack(ToolType.TrainingRod , 1));
        FishShopStock.add(new Stack(ToolType.FiberglassRod , 1));
        FishShopStock.add(new Stack(ToolType.IridiumRod , 1));
        return FishShopStock;
    }
}
