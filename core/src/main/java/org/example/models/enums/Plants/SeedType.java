package org.example.models.enums.Plants;

import org.example.models.Item;
import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum SeedType implements PlantSourceType, Item {
    //Starter + Seed + Bean + Shoot
    GrassStater(CropType.Parsnip), //TODO in chie??
    ParsnipSeed(CropType.Parsnip),
    BeanStarter(CropType.GreenBean),
    CauliflowerSeed(CropType.Cauliflower),
    PotatoSeed(CropType.Potato),
    StrawberrySeed(CropType.Strawberry),
    TulipBulbSeed(CropType.Tulip),
    KaleSeed(CropType.Kale),
    CarrotSeed(CropType.Carrot),
    RhubarbSeed(CropType.Rhubarb),
    JazzSeed(CropType.BlueJazz),
    TomatoSeed(CropType.Tomato),
    PepperSeed(CropType.HotPepper),
    WheatSeed(CropType.Wheat),
    SummerSquashSeed(CropType.SummerSquash),
    RadishSeed(CropType.Radish),
    MelonSeed(CropType.Melon),
    HopsStarter(CropType.Hops),
    PoppySeed(CropType.Poppy),
    SpangleSeed(CropType.SummerSpangle),
    StarfruitSeed(CropType.Starfruit),
    CoffeeBean(CropType.CoffeeBean),
    SunflowerSeed(CropType.Sunflower),
    CornSeed(CropType.Corn),
    EggplantSeed(CropType.Eggplant),
    PumpkinSeed(CropType.Pumpkin),
    BroccoliSeed(CropType.Broccoli),
    AmaranthSeed(CropType.Amaranth),
    GrapeStarter(CropType.Grape),
    BeetSeed(CropType.Beet),
    YamSeed(CropType.Yam),
    BokChoySeed(CropType.BokChoy),
    CranberrySeed(CropType.Cranberry),
    FairySeed(CropType.FairyRose),
    RareSeed(CropType.SweetGemBerry),
    GarlicSeed(CropType.Garlic),
    BlueberrySeed(CropType.Blueberry),
    RedCabbageSeed(CropType.RedCabbage),
    ArtichokeSeed(CropType.Artichoke),
    RiceShoot(CropType.UnmilledRice),
    PowderMelonSeed(CropType.PowderMelon),
    AncientSeed(CropType.AncientFruit),
    MixedSeed(null),
    MapleSeed(TreeType.MapleTree),
    MahoganySeed(TreeType.MahoganyTree),
    MushroomTreeSeed(TreeType.MushroomTree),
    MysticTreeSeed(TreeType.MysticTree),
    ;

    private static HashMap<Season, ArrayList<SeedType>> foragingSeedsBySeason = new HashMap<>() {{
        put(Season.Spring, new ArrayList<>(List.of(AncientSeed, MixedSeed, JazzSeed, CarrotSeed, CauliflowerSeed,
                CoffeeBean, GarlicSeed, BeanStarter, KaleSeed, ParsnipSeed, PotatoSeed, RhubarbSeed, StrawberrySeed,
                TulipBulbSeed, RiceShoot)));
        put(Season.Summer, new ArrayList<>(List.of(AncientSeed, MixedSeed, BlueberrySeed, CornSeed, HopsStarter,
                PepperSeed, MelonSeed, PoppySeed, RadishSeed, RedCabbageSeed, StarfruitSeed, SpangleSeed,
                SummerSquashSeed, SunflowerSeed, TomatoSeed, WheatSeed)));
        put(Season.Fall, new ArrayList<>(List.of(AncientSeed, MixedSeed, AmaranthSeed, ArtichokeSeed, BeetSeed,
                BokChoySeed, BroccoliSeed, CranberrySeed, EggplantSeed, FairySeed, GrapeStarter, PumpkinSeed, YamSeed,
                RareSeed)));
        put(Season.Winter, new ArrayList<>(List.of(AncientSeed, MixedSeed, PowderMelonSeed)));
    }};
    private final PlantType plant;

    SeedType(PlantType plant) {
        this.plant = plant;
    }

    public static HashMap<Season, ArrayList<SeedType>> getForagingSeedsBySeason() {
        return foragingSeedsBySeason;
    }

    public static SeedType getItem(String itemName) {
        for (SeedType seedType : values()) {
            if (seedType.getName().equalsIgnoreCase(itemName.replace(" ", ""))) {
                return seedType;
            }
        }
        return null;
    }

    public PlantType getPlant() {
        return plant;
    }

    @Override
    public Integer getPrice() {
        return 50;
    }

    public String toString() {
        return this.name();
    }
}
