package org.example.server.models.enums.Plants;

import com.badlogic.gdx.graphics.Texture;
import org.example.server.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum CropType implements PlantType {
    Grass(FruitType.Fiber, new int[]{1}, 50, 0, -1, 0, true,
            false, false, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}),


    // Regular Crops:
    BlueJazz(FruitType.BlueJazz, new int[]{1, 2, 2, 2}, 50, 7
            , null, 45, true, true, false
            , new Season[]{Season.Spring}),
    Carrot(FruitType.Carrot, new int[]{1, 1, 1}, 35, 3
            , null, 75, true, true, false
            , new Season[]{Season.Spring}),
    Cauliflower(FruitType.Cauliflower, new int[]{1, 2, 4, 4, 1}, 175, 12
            , null, 75, true, true, true
            , new Season[]{Season.Spring}),
    CoffeeBean(FruitType.CoffeeBean, new int[]{1, 2, 2, 3, 2}, 15, 10
            , 2, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer}),
    Garlic(FruitType.Garlic, new int[]{1, 1, 1, 1}, 60, 4
            , null, 20, true, true, false
            , new Season[]{Season.Spring}),
    GreenBean(FruitType.GreenBean, new int[]{1, 1, 1, 3, 4}, 40, 10
            , 3, 25, false, true, false
            , new Season[]{Season.Spring}),
    Kale(FruitType.Kale, new int[]{1, 2, 2, 1}, 110, 6
            , null, 50, true, true, false
            , new Season[]{Season.Spring}),
    Parsnip(FruitType.Parsnip, new int[]{1, 1, 1, 1}, 35, 4
            , null, 25, true, true, false
            , new Season[]{Season.Spring}),
    Potato(FruitType.Potato, new int[]{1, 1, 1, 2, 1}, 80, 6
            , null, 25, true, true, false
            , new Season[]{Season.Spring}),
    Rhubarb(FruitType.Rhubarb, new int[]{2, 2, 2, 3, 4}, 220, 13
            , null, 0, true, false, false
            , new Season[]{Season.Spring}),
    Strawberry(FruitType.Strawberry, new int[]{1, 1, 2, 2, 2}, 120, 8
            , 4, 50, false, true, false
            , new Season[]{Season.Spring}),
    Tulip(FruitType.Tulip, new int[]{1, 1, 2, 2}, 30, 6
            , null, 45, true, true, false
            , new Season[]{Season.Spring}),
    UnmilledRice(FruitType.UnmilledRice, new int[]{1, 2, 2, 3}, 30, 8
            , null, 3, true, true, false
            , new Season[]{Season.Spring}),
    Blueberry(FruitType.Blueberry, new int[]{1, 3, 3, 4, 2}, 50, 13
            , 4, 25, false, true, false
            , new Season[]{Season.Summer}),
    Corn(FruitType.Corn, new int[]{2, 3, 3, 3, 3}, 50, 14
            , 4, 25, false, true, false
            , new Season[]{Season.Summer, Season.Fall}),
    Hops(FruitType.Hops, new int[]{1, 1, 2, 3, 4}, 25, 11
            , 1, 45, false, true, false
            , new Season[]{Season.Summer}),
    HotPepper(FruitType.HotPepper, new int[]{1, 1, 1, 1, 1}, 40, 5
            , 3, 13, false, true, false
            , new Season[]{Season.Summer}),
    Melon(FruitType.Melon, new int[]{1, 2, 3, 3, 3}, 250, 12
            , null, 113, true, true, true
            , new Season[]{Season.Summer}),
    Poppy(FruitType.Poppy, new int[]{1, 2, 2, 2}, 140, 7
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    Radish(FruitType.Radish, new int[]{2, 1, 2, 1}, 90, 6
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    RedCabbage(FruitType.RedCabbage, new int[]{2, 1, 2, 2, 2}, 260, 9
            , null, 75, true, true, false
            , new Season[]{Season.Summer}),
    Starfruit(FruitType.Starfruit, new int[]{2, 3, 2, 3, 3}, 750, 13
            , null, 125, true, true, false
            , new Season[]{Season.Summer}),
    SummerSpangle(FruitType.SummerSpangle, new int[]{1, 2, 3, 1}, 90, 8
            , null, 45, true, true, false
            , new Season[]{Season.Summer}),
    SummerSquash(FruitType.SummerSquash, new int[]{1, 1, 1, 2, 1}, 45, 6
            , 3, 63, false, true, false
            , new Season[]{Season.Summer}),
    Sunflower(FruitType.Sunflower, new int[]{1, 2, 3, 2}, 80, 8
            , null, 45, true, true, false
            , new Season[]{Season.Summer, Season.Fall}),
    Tomato(FruitType.Tomato, new int[]{2, 2, 2, 2, 3}, 60, 11
            , 4, 20, false, true, false
            , new Season[]{Season.Summer}),
    Wheat(FruitType.Wheat, new int[]{1, 1, 1, 1}, 25, 4
            , null, 0, true, false, false
            , new Season[]{Season.Summer, Season.Fall}),
    Amaranth(FruitType.Amaranth, new int[]{1, 2, 2, 2}, 150, 7
            , null, 50, true, true, false
            , new Season[]{Season.Fall}),
    Artichoke(FruitType.Artichoke, new int[]{2, 2, 1, 2, 1}, 160, 8
            , null, 30, true, true, false
            , new Season[]{Season.Fall}),
    Beet(FruitType.Beet, new int[]{1, 1, 2, 2}, 100, 6
            , null, 30, true, true, false
            , new Season[]{Season.Fall}),
    BokChoy(FruitType.BokChoy, new int[]{1, 1, 1, 1}, 80, 4
            , null, 25, true, true, false
            , new Season[]{Season.Fall}),
    Broccoli(FruitType.Broccoli, new int[]{2, 2, 2, 2}, 70, 8
            , 4, 63, true, true, false
            , new Season[]{Season.Fall}),
    Cranberry(FruitType.Cranberry, new int[]{1, 2, 1, 1, 2}, 75, 7
            , 5, 38, true, true, false
            , new Season[]{Season.Fall}),
    Eggplant(FruitType.Eggplant, new int[]{1, 1, 1, 1}, 60, 5
            , 5, 20, true, true, false
            , new Season[]{Season.Fall}),
    FairyRose(FruitType.FairyRose, new int[]{1, 4, 4, 3}, 290, 12
            , null, 45, true, true, false
            , new Season[]{Season.Fall}),
    Grape(FruitType.Grape, new int[]{1, 1, 2, 3, 3}, 80, 10
            , 3, 38, true, true, false
            , new Season[]{Season.Fall}),
    Pumpkin(FruitType.Pumpkin, new int[]{1, 2, 3, 4, 3}, 320, 13
            , null, 0, false, false, true
            , new Season[]{Season.Fall}),
    Yam(FruitType.Yam, new int[]{1, 3, 3, 3}, 160, 10
            , null, 45, true, true, false
            , new Season[]{Season.Fall}),
    SweetGemBerry(FruitType.SweetGemBerry, new int[]{2, 4, 6, 6, 6}, 3000, 24
            , null, 0, false, false, false
            , new Season[]{Season.Fall}),
    PowderMelon(FruitType.PowderMelon, new int[]{1, 2, 1, 2, 1}, 60, 7
            , null, 63, true, true, false
            , new Season[]{Season.Winter}),
    AncientFruit(FruitType.AncientFruit, new int[]{2, 7, 7, 7, 5}, 550, 28
            , 7, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer, Season.Fall}),



    // Foragings:
    Daffodil(FruitType.Daffodil, new int[]{}, 30, 0
            , null, 0, false, false, false
            , new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter},
            "assets/Images/Crops/Foragings/Daffodil.png"),
    Dandelion(FruitType.Dandelion, new int[]{}, 40, 0
            , null, 25, false, true, false
            , new Season[]{Season.Spring},
            "assets/Images/Crops/Foragings/Dandelion.png"),
    Leek(FruitType.Leek, new int[]{}, 60, 0
            , null, 40, false, true, false
            , new Season[]{Season.Spring},
            "assets/Images/Crops/Foragings/Leek.png"),
    Morel(FruitType.Morel, new int[]{}, 150, 0
            , null, 20, false, true, false
            , new Season[]{Season.Spring},
            "assets/Images/Crops/Foragings/Morel.png"),
    SalmonBerry(FruitType.SalmonBerry, new int[]{}, 5, 0
            , null, 25, false, true, false
            , new Season[]{Season.Spring},
            "assets/Images/Crops/Foragings/Salmonberry.png"),
    SpringOnion(FruitType.SpringOnion, new int[]{}, 8, 0
            , null, 13, false, true, false
            , new Season[]{Season.Spring},
            "assets/Images/Crops/Foragings/Spring_Onion.png"),
    WildHorseradish(FruitType.WildHorseradish, new int[]{}, 50, 0
            , null, 13, false, true, false
            , new Season[]{Season.Spring},
            "assets/Images/Crops/Foragings/Wild_Horseradish.png"),
    FiddleheadFern(FruitType.FiddleheadFern, new int[]{}, 90, 0
            , null, 25, false, true, false
            , new Season[]{Season.Summer},
            "assets/Images/Crops/Foragings/Fiddlehead_Fern.png"),
    RedMushroom(FruitType.RedMushroom, new int[]{}, 75, 0
            , null, -50, false, true, false
            , new Season[]{Season.Summer},
            "assets/Images/Crops/Foragings/Red_Mushroom.png"),
    SpiceBerry(FruitType.SpiceBerry, new int[]{}, 80, 0
            , null, 25, false, true, false
            , new Season[]{Season.Summer},
            "assets/Images/Crops/Foragings/Spice_Berry.png"),
    SweetPea(FruitType.SweetPea, new int[]{}, 50, 0
            , null, 0, false, false, false
            , new Season[]{Season.Summer},
            "assets/Images/Crops/Foragings/Sweet_Pea.png"),
    Blackberry(FruitType.Blackberry, new int[]{}, 25, 0
            , null, 25, false, true, false
            , new Season[]{Season.Fall},
            "assets/Images/Crops/Foragings/Blackberry.png"),
    Chanterelle(FruitType.Chanterelle, new int[]{}, 160, 0
            , null, 75, false, true, false
            , new Season[]{Season.Fall},
            "assets/Images/Crops/Foragings/Chanterelle.png"),
    Hazelnut(FruitType.Hazelnut, new int[]{}, 40, 0
            , null, 38, false, true, false
            , new Season[]{Season.Fall},
            "assets/Images/Crops/Foragings/Hazelnut.png"),
    PurpleMushroom(FruitType.PurpleMushroom, new int[]{}, 90, 0
            , null, 30, false, true, false
            , new Season[]{Season.Fall},
            "assets/Images/Crops/Foragings/Purple_Mushroom.png"),
    WildPlum(FruitType.WildPlum, new int[]{}, 80, 0
            , null, 25, false, true, false
            , new Season[]{Season.Fall},
            "assets/Images/Crops/Foragings/Wild_Plum.png"),
    Crocus(FruitType.Crocus, new int[]{}, 60, 0
            , null, 0, false, false, false
            , new Season[]{Season.Winter},
            "assets/Images/Crops/Foragings/Crocus.png"),
    CrystalFruit(FruitType.CrystalFruit, new int[]{}, 150, 0
            , null, 63, false, true, false
            , new Season[]{Season.Winter},
            "assets/Images/Crops/Foragings/Crystal_Fruit.png"),
    Holly(FruitType.Holly, new int[]{}, 80, 0
            , null, -37, false, true, false
            , new Season[]{Season.Winter},
            "assets/Images/Crops/Foragings/Holly.png"),
    SnowYum(FruitType.SnowYam, new int[]{}, 100, 0
            , null, 30, false, true, false
            , new Season[]{Season.Winter},
            "assets/Images/Crops/Foragings/Snow_Yam.png"),
    WinterRoot(FruitType.WinterRoot, new int[]{}, 70, 0
            , null, 25, false, true, false
            , new Season[]{Season.Winter},
            "assets/Images/Crops/Foragings/Winter_Root.png"),;

    private static final HashMap<Season, ArrayList<CropType>> foragingCropsBySeason = new HashMap<>() {{
        put(Season.Spring, new ArrayList<>(List.of(Grass, Daffodil, Dandelion, Leek, Morel, SalmonBerry,
                SpringOnion, WildHorseradish)));
        put(Season.Summer, new ArrayList<>(List.of(Grass, FiddleheadFern, Grape, RedMushroom, SpiceBerry,
                SweetPea)));
        put(Season.Fall, new ArrayList<>(List.of(Grass, Blackberry, Chanterelle, Hazelnut, PurpleMushroom,
                WildPlum)));
        put(Season.Winter, new ArrayList<>(List.of(Grass, Crocus, CrystalFruit, Holly, SnowYum, WinterRoot)));
    }};
    private static HashMap<Season, ArrayList<CropType>> mixedSeedPossibilitiesBySeason = new HashMap<>() {{
        put(Season.Spring, new ArrayList<>(List.of(Cauliflower, Parsnip, Potato, BlueJazz, Tulip)));
        put(Season.Summer, new ArrayList<>(List.of(Corn, HotPepper, Radish, Wheat, Poppy, Sunflower, SummerSpangle)));
        put(Season.Fall, new ArrayList<>(List.of(Artichoke, Corn, Eggplant, Pumpkin, Sunflower, FairyRose)));
        put(Season.Winter, new ArrayList<>(List.of(PowderMelon)));
    }};
    private final FruitType fruit;
    private final int price;
    private final int[] stages;
    private final int totalHarvestTime, energy;
    private final Integer regrowthTime;
    private final boolean oneTime, isEdible, canBecomeGiant;
    private final ArrayList<Season> seasons;
    private final String address;
    private final ArrayList<String> addresses;
    private SeedType source;

    CropType(FruitType fruit, int[] stages, int price, int totalHarvestTime, Integer regrowthTime,
             int energy, boolean oneTime, boolean isEdible, boolean canBecomeGiant, Season[] seasons) {
        this(fruit, stages, price, totalHarvestTime, regrowthTime, energy, oneTime, isEdible, canBecomeGiant, seasons,
                "assets/Images/Crops/Foragings/Crocus.png",
                null);
    }

    CropType(FruitType fruit, int[] stages, int price, int totalHarvestTime, Integer regrowthTime,
             int energy, boolean oneTime, boolean isEdible, boolean canBecomeGiant, Season[] seasons, String address) {
        this(fruit, stages, price, totalHarvestTime, regrowthTime, energy, oneTime, isEdible, canBecomeGiant, seasons,
                address,
                null);
    }

    CropType(FruitType fruit, int[] stages, int price, int totalHarvestTime, Integer regrowthTime,
             int energy, boolean oneTime, boolean isEdible, boolean canBecomeGiant, Season[] seasons,
             String address, ArrayList<String> addresses) {
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
        this.seasons = new ArrayList<>(List.of(seasons));
        this.address = address;
        this.addresses = addresses;
    }

    public static HashMap<Season, ArrayList<CropType>> getForagingCropsBySeason() {
        return foragingCropsBySeason;
    }

    public static HashMap<Season, ArrayList<CropType>> getMixedSeedPossibilitiesBySeason() {
        return mixedSeedPossibilitiesBySeason;
    }

    public static CropType getItem(String itemName) {
        for (CropType item : values()) {
            if (item.toString().equalsIgnoreCase(itemName.replace(" ", ""))) {
                return item;
            }
        }
        return null;
    }

    public int getEnergy() {
        return energy;
    }

    public FruitType getFruit() {
        return fruit;
    }

    public SeedType getSource() {
        if (source == null)
            for (SeedType seedType : SeedType.values()) {
                if (seedType.getPlant() == this)
                    return source = seedType;
            }
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

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
