package org.example.models.enums.Plants;

import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum CropType implements PlantType {
    BlueJazz(FruitType.BlueJazz, new int[]{1, 2, 2, 2}, 50, SeedType.JazzSeed, 7
            , null, 45, true, true, false
            , new Season[]{Season.Spring}),
    Carrot(FruitType.Carrot, new int[]{1, 1, 1}, 35, SeedType.CarrotSeed, 3
            , null, 75, true, true, false
            , new Season[]{Season.Spring}),
    Cauliflower(FruitType.Cauliflower, new int[]{1, 2, 4, 4, 1}, 175, SeedType.CauliflowerSeed, 12
            , null, 75, true, true, true
            , new Season[]{Season.Spring}),
    CoffeeBean(FruitType.CoffeeBean, new int[]{1, 2, 2, 3, 2}, 15, SeedType.CoffeeBean, 10
            , 2, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer}),
    Garlic(FruitType.Garlic, new int[]{1, 1, 1, 1}, 60, SeedType.GarlicSeed, 4
            , null, 20, true, true, false
            , new Season[]{Season.Spring}),
    GreenBean(FruitType.GreenBean, new int[]{1, 1, 1, 3, 4}, 40, SeedType.BeanStarter, 10
            , 3, 25, false, true, false
            , new Season[]{Season.Spring}),
    Kale(FruitType.Kale, new int[]{1, 2, 2, 1}, 110, SeedType.KaleSeed, 6
            , null, 50, true, true, false
            , new Season[]{Season.Spring}),
    Parsnip(FruitType.Parsnip, new int[]{1, 1, 1, 1}, 35, SeedType.ParsnipSeed, 4
            , null, 25, true, true, false
            , new Season[]{Season.Spring}),
    Potato(FruitType.Potato, new int[]{1, 1, 1, 2, 1}, 80, SeedType.PotatoSeed, 6
            , null, 25, true, true, false
            , new Season[]{Season.Spring}),
    Rhubarb(FruitType.Rhubarb, new int[]{2, 2, 2, 3, 4}, 220, SeedType.RhubarbSeed, 13
            , null, 0, true, false, false
            , new Season[]{Season.Spring}),
    Strawberry(FruitType.Strawberry, new int[]{1, 1, 2, 2, 2}, 120, SeedType.StrawberrySeed, 8
            , 4, 50, false, true, false
            , new Season[]{Season.Spring}),
    Tulip(FruitType. Tulip, new int[]{1, 1, 2, 2}, 30, SeedType.TulipBulbSeed, 6
            , null, 45, true, true, false
            , new Season[]{Season.Spring}),
    UnmilledRice(FruitType.UnmilledRice, new int[]{1, 2, 2, 3}, 30, SeedType.RiceShoot, 8
            , null, 3, true, true, false
            , new Season[]{Season.Spring}),
    Blueberry(FruitType.Blueberry, new int[]{1, 3, 3, 4, 2}, 50, SeedType.BlueberrySeed, 13
            , 4, 25, false, true, false
            , new Season[]{Season.Summer}),
    Corn(FruitType.Corn, new int[]{2, 3, 3, 3, 3}, 50, SeedType.CornSeed, 14
            , 4, 25, false, true, false
            , new Season[]{Season.Summer, Season.Fall}),
    Hops(FruitType.Hops, new int[]{1, 1, 2, 3, 4}, 25, SeedType.HopsStarter, 11
            , 1, 45, false, true, false
            , new Season[]{Season.Summer}),
    HotPepper(FruitType.HotPepper, new int[]{1, 1, 1, 1, 1}, 40, SeedType.PepperSeed, 5
            , 3, 13, false, true, false
            , new Season[]{Season.Summer}),
    Melon(FruitType.Melon, new int[]{1, 2, 3, 3, 3}, 250, SeedType.MelonSeed, 12
            , null, 113, true, true, true
            , new Season[]{Season.Summer}),
    Poppy(FruitType.Poppy, new int[]{1, 2, 2, 2}, 140, SeedType.PoppySeed, 7
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    Radish(FruitType.Radish, new int[]{2, 1, 2, 1}, 90, SeedType.RadishSeed, 6
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    RedCabbage(FruitType.RedCabbage, new int[]{2, 1, 2, 2, 2}, 260, SeedType.RedCabbageSeed, 9
            , null, 75, true, true, false
            , new Season[]{Season.Summer}),
    Starfruit(FruitType.Starfruit, new int[]{2, 3, 2, 3, 3}, 750, SeedType.StarfruitSeed, 13
            , null, 125, true, true, false
            , new Season[]{Season.Summer}),
    SummerSpangle(FruitType.SummerSpangle, new int[]{1, 2, 3, 1}, 90, SeedType.SpangleSeed, 8
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    SummerSquash(FruitType.SummerSquash, new int[]{1, 1, 1, 2, 1}, 45, SeedType.SummerSquashSeed, 6
            , 3, 63, false, true, false
            , new Season[]{Season.Summer}),
    Sunflower(FruitType.Sunflower, new int[]{1, 2, 3, 2}, 80, SeedType.SunflowerSeed, 8
            , null, 45, true, true, false
            , new Season[]{Season.Summer, Season.Fall}),
    Tomato(FruitType.Tomato, new int[]{2, 2, 2, 2, 3}, 60, SeedType.TomatoSeed, 11
            , 4, 20, false, true, false
            , new Season[]{Season.Summer}),
    Wheat(FruitType.Wheat, new int[]{1, 1, 1, 1}, 25, SeedType.WheatSeed, 4
            , null, 0, true, false, false
            , new Season[]{Season.Summer, Season.Fall}),
    Amaranth(FruitType.Amaranth, new int[]{1, 2, 2, 2}, 150, SeedType.AmaranthSeed, 7
            , null, 50, true, true, false
            , new Season[]{Season.Fall}),
    Artichoke(FruitType.Artichoke, new int[]{2, 2, 1, 2, 1}, 160, SeedType.ArtichokeSeed, 8
            , null, 30, true, true, false
            , new Season[]{Season.Fall}),
    Beet(FruitType.Beet, new int[]{1, 1, 2, 2}, 100, SeedType.BeetSeed, 6
            , null, 30, true, true, false
            , new Season[]{Season.Fall}),
    BokChoy(FruitType.BokChoy, new int[]{1, 1, 1, 1}, 80, SeedType.BokChoySeed, 4
            , null, 25, true, true, false
            , new Season[]{Season.Fall}),
    Broccoli(FruitType.Broccoli, new int[]{2, 2, 2, 2}, 70, SeedType.BroccoliSeed, 8
            , 4, 63, true, true, false
            , new Season[]{Season.Fall}),
    Cranberry(FruitType.Cranberry, new int[]{1, 2, 1, 1, 2}, 75, SeedType.CranberrySeed, 7
            , 5, 38, true, true, false
            , new Season[]{Season.Fall}),
    Eggplant(FruitType.Eggplant, new int[]{1, 1, 1, 1}, 60, SeedType.EggplantSeed, 5
            , 5, 20, true, true, false
            , new Season[]{Season.Fall}),
    FairyRose(FruitType.FairyRose, new int[]{1, 4, 4, 3}, 290, SeedType.FairySeed, 12
            , null, 45, true, true, false
            , new Season[]{Season.Fall}),
    Grape(FruitType.Grape, new int[]{1, 1, 2, 3, 3}, 80, SeedType.GrapeStarter, 10
            , 3, 38, true, true, false
            , new Season[]{Season.Fall}),
    Pumpkin(FruitType.Pumpkin, new int[]{1, 2, 3, 4, 3}, 320, SeedType.PumpkinSeed, 13
            , null, 0, false, false, true
            , new Season[]{Season.Fall}),
    Yam(FruitType.Yam, new int[]{1, 3, 3, 3}, 160, SeedType.YamSeed, 10
            , null, 45, true, true, false
            , new Season[]{Season.Fall}),
    SweetGemBerry(FruitType.SweetGemBerry, new int[]{2, 4, 6, 6, 6}, 3000, SeedType.RareSeed, 24
            , null, 0, false, false, false
            , new Season[]{Season.Fall}),
    PowderMelon(FruitType.PowderMelon, new int[]{1, 2, 1, 2, 1}, 60, SeedType.PowderMelonSeed, 7
            , null, 63, true, true, false
            , new Season[]{Season.Winter}),
    AncientFruit(FruitType.AncientFruit, new int[]{2, 7, 7, 7, 5}, 550, SeedType.AncientSeed, 28
            , 7, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer, Season.Fall}),
    Daffodil(FruitType.Daffodil, new int[]{}, 30, null, 0
            , null, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}),
    Dandelion(FruitType.Dandelion, new int[]{}, 40, null, 0
            , null, 25, false, true, false
            , new Season[]{Season.Spring}),
    Leek(FruitType.Leek, new int[]{} , 60 , null , 0
            , null , 40 ,false , true , false
            , new Season[]{Season.Spring}),
    Morel(FruitType.Morel, new int[]{} , 150 , null , 0
            , null , 20 , false , true , false
            , new Season[]{Season.Spring}),
    SalmonBerry(FruitType.SalmonBerry, new int[]{} , 5 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Spring}),
    SpringOnion(FruitType.SpringOnion, new int[]{} , 8 , null , 0
            , null , 13 , false , true , false
            , new Season[]{Season.Spring}),
    WildHorseradish(FruitType.WildHorseradish, new int[]{} , 50 , null , 0
            , null , 13 , false, true , false
            , new Season[]{Season.Spring}),
    FiddleheadFern(FruitType.FiddleheadFern, new int[]{} , 90 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Summer}),
    RedMushroom(FruitType.RedMushroom, new int[]{} , 75 , null , 0
            , null , -50 , false , true , false
            , new Season[]{Season.Summer}),
    SpiceBerry(FruitType.SpiceBerry, new int[]{} , 80 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Summer}),
    SweetPea(FruitType.SweetPea, new int[]{} , 50 , null , 0
            , null , 0 , false , false , false
            , new Season[]{Season.Summer}),
    Blackberry(FruitType.Blackberry, new int[]{} , 25 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Fall}),
    Chanterelle(FruitType.Chanterelle, new int[]{} , 160 , null , 0
            , null , 75 , false , true , false
            , new Season[]{Season.Fall}),
    Hazelnut(FruitType.Hazelnut, new int[]{} , 40 , null , 0
            , null , 38 , false , true , false
            , new Season[]{Season.Fall}),
    PurpleMushroom(FruitType.PurpleMushroom, new int[]{} , 90 , null , 0
            , null , 30 , false , true , false
            , new Season[]{Season.Fall}),
    WildPlum(FruitType.WildPlum, new int[]{} , 80 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Fall}),
    Crocus(FruitType.Crocus, new int[]{} , 60 , null , 0
            , null , 0 , false , false , false
            , new Season[]{Season.Winter}),
    CrystalFruit(FruitType.CrystalFruit, new int[]{} , 150 , null , 0
            , null , 63 , false , true , false
            , new Season[]{Season.Winter}),
    Holly(FruitType.Holly, new int[]{} , 80 , null , 0
            , null , -37 , false , true , false
            , new Season[]{Season.Winter}),
    SnowYum(FruitType.SnowYam, new int[]{} , 100 , null , 0
            , null , 30 , false , true , false
            , new Season[]{Season.Winter}),
    WinterRoot(FruitType.WinterRoot, new int[]{} , 70 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Winter});

    private FruitType fruit;
    private int price;
    private SeedType source;
    private int[] stages;
    private int totalHarvestTime, energy;
    private Integer regrowthTime;
    private boolean oneTime, isEdible, canBecomeGiant;
    private Season[] seasons;
    private static HashMap<Season, ArrayList<CropType>> foragingCropsBySeason = new HashMap<>(){{
        put(Season.Spring, new ArrayList<>(List.of(CropType.Daffodil,
        CropType.Dandelion, CropType.Leek, CropType.Morel, CropType.SalmonBerry, CropType.SpringOnion,
            CropType.WildHorseradish)));
        put(Season.Summer, new ArrayList<>(List.of(CropType.FiddleheadFern, CropType.Grape, CropType.RedMushroom,
            CropType.SpiceBerry, CropType.SweetPea)));
        put(Season.Fall, new ArrayList<>(List.of(CropType.Blackberry, CropType.Chanterelle, CropType.Hazelnut,
                CropType.PurpleMushroom, CropType.WildPlum)));
        put(Season.Winter, new ArrayList<>(List.of(CropType.Crocus, CropType.CrystalFruit, CropType.Holly,
                CropType.SnowYum, CropType.WinterRoot)));
    }};

    private static HashMap<Season, ArrayList<CropType>> mixedSeedPossibilitiesBySeason = new HashMap<>(){{
        put(Season.Spring, new ArrayList<>(List.of(Cauliflower, Parsnip, Potato, BlueJazz, Tulip)));
        put(Season.Summer, new ArrayList<>(List.of(Corn, HotPepper, Radish, Wheat, Poppy, Sunflower, SummerSpangle)));
        put(Season.Fall, new ArrayList<>(List.of(Artichoke, Corn, Eggplant, Pumpkin, Sunflower, FairyRose)));
        put(Season.Winter, new ArrayList<>(List.of(PowderMelon)));
    }};

    CropType(FruitType fruit, int[] stages, int price, SeedType source, int totalHarvestTime, Integer regrowthTime,
             int energy, boolean oneTime, boolean isEdible, boolean canBecomeGiant, Season[] seasons) {
        this.fruit = fruit;
        this.stages = stages;
        this.price = price;
        this.source = source;
        this.totalHarvestTime = totalHarvestTime;
        this.regrowthTime = regrowthTime;
        this.energy = energy;
        this.oneTime = oneTime;
        this.isEdible = isEdible;
        this.canBecomeGiant = canBecomeGiant;
        this.seasons = seasons;
    }

    public int getEnergy() {
        return energy;
    }

    public static HashMap<Season, ArrayList<CropType>> getForagingCropsBySeason() {
        return foragingCropsBySeason;
    }

    public static HashMap<Season, ArrayList<CropType>> getMixedSeedPossibilitiesBySeason() {
        return mixedSeedPossibilitiesBySeason;
    }

    public FruitType getFruit() {
        return fruit;
    }

    public SeedType getSource() {
        return source;
    }

    public int[] getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public int getHarvestCycle() {
        if (regrowthTime == null) {
            return -1;
        } else {
            return (int) regrowthTime;
        }
    }

    public boolean getOneTime() {
        return oneTime;
    }

    public boolean canBecomeGiant() {
        return canBecomeGiant;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public Season[] getSeasons() {
        return seasons;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static CropType getItem(String itemName) {
        for (CropType item : values()) {
            if (item.name().equalsIgnoreCase(itemName.replace(" ", ""))) {
                return item;
            }
        }
        return null;
    }

}
