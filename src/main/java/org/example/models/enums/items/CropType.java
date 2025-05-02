package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum CropType implements Item {
    BlueJazz(new int[]{1, 2, 2, 2}, 50, SeedType.JazzSeed, 7
            , null, 45, true, true, false
            , new Season[]{Season.Spring}),
    Carrot(new int[]{1, 1, 1}, 35, SeedType.CarrotSeed, 3
            , null, 75, true, true, false
            , new Season[]{Season.Spring}),
    Cauliflower(new int[]{1, 2, 4, 4, 1}, 175, SeedType.CauliflowerSeed, 12
            , null, 75, true, true, true
            , new Season[]{Season.Spring}),
    CoffeeBean(new int[]{1, 2, 2, 3, 2}, 15, SeedType.CoffeeBean, 10
            , 2, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer}),
    Garlic(new int[]{1, 1, 1, 1}, 60, SeedType.GarlicSeed, 4
            , null, 20, true, true, false
            , new Season[]{Season.Spring}),
    GreenBean(new int[]{1, 1, 1, 3, 4}, 40, SeedType.BeanStarter, 10
            , 3, 25, false, true, false
            , new Season[]{Season.Spring}),
    Kale(new int[]{1, 2, 2, 1}, 110, SeedType.KaleSeed, 6
            , null, 50, true, true, false
            , new Season[]{Season.Spring}),
    Parsnip(new int[]{1, 1, 1, 1}, 35, SeedType.ParsnipSeed, 4
            , null, 25, true, true, false
            , new Season[]{Season.Spring}),
    Potato(new int[]{1, 1, 1, 2, 1}, 80, SeedType.PotatoSeed, 6
            , null, 25, true, true, false
            , new Season[]{Season.Spring}),
    Rhubarb(new int[]{2, 2, 2, 3, 4}, 220, SeedType.RhubarbSeed, 13
            , null, 0, true, false, false
            , new Season[]{Season.Spring}),
    Strawberry(new int[]{1, 1, 2, 2, 2}, 120, SeedType.StrawberrySeed, 8
            , 4, 50, false, true, false
            , new Season[]{Season.Spring}),
    Tulip(new int[]{1, 1, 2, 2}, 30, SeedType.TulipBulbSeed, 6
            , null, 45, true, true, false
            , new Season[]{Season.Spring}),
    UnmilledRice(new int[]{1, 2, 2, 3}, 30, SeedType.RiceShoot, 8
            , null, 3, true, true, false
            , new Season[]{Season.Spring}),
    Blueberry(new int[]{1, 3, 3, 4, 2}, 50, SeedType.BlueberrySeed, 13
            , 4, 25, false, true, false
            , new Season[]{Season.Summer}),
    Corn(new int[]{2, 3, 3, 3, 3}, 50, SeedType.CornSeed, 14
            , 4, 25, false, true, false
            , new Season[]{Season.Summer, Season.Fall}),
    Hops(new int[]{1, 1, 2, 3, 4}, 25, SeedType.HopsStarter, 11
            , 1, 45, false, true, false
            , new Season[]{Season.Summer}),
    HotPepper(new int[]{1, 1, 1, 1, 1}, 40, SeedType.PepperSeed, 5
            , 3, 13, false, true, false
            , new Season[]{Season.Summer}),
    Melon(new int[]{1, 2, 3, 3, 3}, 250, SeedType.MelonSeed, 12
            , null, 113, true, true, true
            , new Season[]{Season.Summer}),
    Poppy(new int[]{1, 2, 2, 2}, 140, SeedType.PoppySeed, 7
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    Radish(new int[]{2, 1, 2, 1}, 90, SeedType.RadishSeed, 6
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    RedCabbage(new int[]{2, 1, 2, 2, 2}, 260, SeedType.RedCabbageSeed, 9
            , null, 75, true, true, false
            , new Season[]{Season.Summer}),
    Starfruit(new int[]{2, 3, 2, 3, 3}, 750, SeedType.StarfruitSeed, 13
            , null, 125, true, true, false
            , new Season[]{Season.Summer}),
    SummerSpangle(new int[]{1, 2, 3, 1}, 90, SeedType.SpangleSeed, 8
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    SummerSquash(new int[]{1, 1, 1, 2, 1}, 45, SeedType.SummerSquashSeed, 6
            , 3, 63, false, true, false
            , new Season[]{Season.Summer}),
    Sunflower(new int[]{1, 2, 3, 2}, 80, SeedType.SunflowerSeed, 8
            , null, 45, true, true, false
            , new Season[]{Season.Summer, Season.Fall}),
    Tomato(new int[]{2, 2, 2, 2, 3}, 60, SeedType.TomatoSeed, 11
            , 4, 20, false, true, false
            , new Season[]{Season.Summer}),
    Wheat(new int[]{1, 1, 1, 1}, 25, SeedType.WheatSeed, 4
            , null, 0, true, false, false
            , new Season[]{Season.Summer, Season.Fall}),
    Amaranth(new int[]{1, 2, 2, 2}, 150, SeedType.AmaranthSeed, 7
            , null, 50, true, true, false
            , new Season[]{Season.Fall}),
    Artichoke(new int[]{2, 2, 1, 2, 1}, 160, SeedType.ArtichokeSeed, 8
            , null, 30, true, true, false
            , new Season[]{Season.Fall}),
    Beet(new int[]{1, 1, 2, 2}, 100, SeedType.BeetSeed, 6
            , null, 30, true, true, false
            , new Season[]{Season.Fall}),
    BokChoy(new int[]{1, 1, 1, 1}, 80, SeedType.BokChoySeed, 4
            , null, 25, true, true, false
            , new Season[]{Season.Fall}),
    Broccoli(new int[]{2, 2, 2, 2}, 70, SeedType.BroccoliSeed, 8
            , 4, 63, true, true, false
            , new Season[]{Season.Fall}),
    Cranberry(new int[]{1, 2, 1, 1, 2}, 75, SeedType.CranberrySeed, 7
            , 5, 38, true, true, false
            , new Season[]{Season.Fall}),
    Eggplant(new int[]{1, 1, 1, 1}, 60, SeedType.EggplantSeed, 5
            , 5, 20, true, true, false
            , new Season[]{Season.Fall}),
    FairyRose(new int[]{1, 4, 4, 3}, 290, SeedType.FairySeed, 12
            , null, 45, true, true, false
            , new Season[]{Season.Fall}),
    Grape(new int[]{1, 1, 2, 3, 3}, 80, SeedType.GrapeStarter, 10
            , 3, 38, true, true, false
            , new Season[]{Season.Fall}),
    Pumpkin(new int[]{1, 2, 3, 4, 3}, 320, SeedType.PumpkinSeed, 13
            , null, 0, false, false, true
            , new Season[]{Season.Fall}),
    Yam(new int[]{1, 3, 3, 3}, 160, SeedType.YamSeed, 10
            , null, 45, true, true, false
            , new Season[]{Season.Fall}),
    SweetGemBerry(new int[]{2, 4, 6, 6, 6}, 3000, SeedType.RareSeed, 24
            , null, 0, false, false, false
            , new Season[]{Season.Fall}),
    PowderMelon(new int[]{1, 2, 1, 2, 1}, 60, SeedType.PowderMelonSeed, 7
            , null, 63, true, true, false
            , new Season[]{Season.Winter}),
    AncientFruit(new int[]{2, 7, 7, 7, 5}, 550, SeedType.AncientSeed, 28
            , 7, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer, Season.Fall}),
    Daffodil(new int[]{}, 30, null, 0
            , null, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}),
    Dandelion(new int[]{}, 40, null, 0
            , null, 25, false, true, false
            , new Season[]{Season.Spring}),
    Leek(new int[]{} , 60 , null , 0
            , null , 40 ,false , true , false
            , new Season[]{Season.Spring}),
    Morel(new int[]{} , 150 , null , 0
            , null , 20 , false , true , false
            , new Season[]{Season.Spring}),
    SalmonBerry(new int[]{} , 5 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Spring}),
    SpringOnion(new int[]{} , 8 , null , 0
            , null , 13 , false , true , false
            , new Season[]{Season.Spring}),
    WildHorseradish(new int[]{} , 50 , null , 0
            , null , 13 , false, true , false
            , new Season[]{Season.Spring}),
    FiddleheadFern(new int[]{} , 90 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Summer}),
    RedMushroom(new int[]{} , 75 , null , 0
            , null , -50 , false , true , false
            , new Season[]{Season.Summer}),
    SpiceBerry(new int[]{} , 80 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Summer}),
    SweetPea(new int[]{} , 50 , null , 0
            , null , 0 , false , false , false
            , new Season[]{Season.Summer}),
    Blackberry(new int[]{} , 25 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Fall}),
    Chanterelle(new int[]{} , 160 , null , 0
            , null , 75 , false , true , false
            , new Season[]{Season.Fall}),
    Hazelnut(new int[]{} , 40 , null , 0
            , null , 38 , false , true , false
            , new Season[]{Season.Fall}),
    PurpleMushroom(new int[]{} , 90 , null , 0
            , null , 30 , false , true , false
            , new Season[]{Season.Fall}),
    WildPlum(new int[]{} , 80 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Fall}),
    Crocus(new int[]{} , 60 , null , 0
            , null , 0 , false , false , false
            , new Season[]{Season.Winter}),
    CrystalFruit(new int[]{} , 150 , null , 0
            , null , 63 , false , true , false
            , new Season[]{Season.Winter}),
    Holly(new int[]{} , 80 , null , 0
            , null , -37 , false , true , false
            , new Season[]{Season.Winter}),
    SnowYum(new int[]{} , 100 , null , 0
            , null , 30 , false , true , false
            , new Season[]{Season.Winter}),
    WinterRoot(new int[]{} , 70 , null , 0
            , null , 25 , false , true , false
            , new Season[]{Season.Winter}),
    Fiber(null, 0, null, 0, null, 0, false, false, false, null); // TODO: WTF?

    private int price;
    private SeedType source;
    private int[] stages;
    private int totalHarvestTime, energy;
    private Integer regrowthTime;
    private boolean oneTime, isEdible, canBecomeGiant;
    private Season[] season;
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

    CropType(int[] stages, int price, SeedType source, int totalHarvestTime, Integer regrowthTime,
             int energy, boolean oneTime, boolean isEdible, boolean canBecomeGiant, Season[] season) {
        this.stages = stages;
        this.price = price;
        this.source = source;
        this.totalHarvestTime = totalHarvestTime;
        this.regrowthTime = regrowthTime;
        this.energy = energy;
        this.oneTime = oneTime;
        this.isEdible = isEdible;
        this.canBecomeGiant = canBecomeGiant;
        this.season = season;
    }

    public int getEnergy() {
        return energy;
    }

    public static HashMap<Season, ArrayList<CropType>> getForagingCropsBySeason() {
        return foragingCropsBySeason;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
