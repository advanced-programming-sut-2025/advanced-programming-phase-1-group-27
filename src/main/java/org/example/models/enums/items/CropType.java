package org.example.models.enums.items;

import org.example.models.enums.Seasons.Season;

public enum CropType implements Item {
    BlueJazz,
    Carrot,
    Cauliflower,
    CoffeeBean,
    Garlic,
    GreenBean,
    Kale,
    Parsnip,
    Potato,
    Rhubarb,
    Strawberry,
    Tulip,
    UnmilledRice,
    Blueberry,
    Corn,
    Hops,
    HotPepper,
    Melon,
    Poppy,
    Radish,
    RedCabbage,
    Starfruit,
    SummerSpangle,
    SummerSquash,
    Sunflower,
    Tomato,
    Wheat,
    Amaranth,
    Artichoke,
    Beet,
    BokChoy,
    Broccoli,
    Cranberry,
    Eggplant,
    FairyRose,
    Grape,
    Pumpkin,
    Yam,
    SweetGemBerry,
    PowderMelon,
    AncientFruit,
    Daffodil,
    Dandelion,
    Leek,
    Morel,
    Salmonberry,
    SpringOnion,
    WildHorseradish,
    FiddleheadFern,
    RedMushroom,
    SpiceBerry,
    SweetPea,
    Blackberry,
    Chanterelle,
    Hazelnut,
    PurpleMushroom,
    WildPlum,
    Crocus,
    CrystalFruit,
    Holly,
    SnowYum,
    WinterRoot;

    private int price;

    private String name;
    private SeedType source;
    private int[] stages;
    private int totalHarvestTime, regrowthTime, baseSellPrice, energy;
    private boolean oneTime, isEdible, canBecomeGiant;
    private Season season;

    @Override
    public int getPrice() {
        return 0;
    }
}
