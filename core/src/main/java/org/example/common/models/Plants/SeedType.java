package org.example.common.models.Plants;

import org.example.common.models.Item;
import org.example.common.models.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum SeedType implements PlantSourceType, Item {
    //Starter + Seed + Bean + Shoot
    GrassStater(CropType.Parsnip,"Items/SeedType/Grass_Starter.png"), //TODO in chie??
    ParsnipSeed(CropType.Parsnip,"Items/SeedType/Parsnip_Seeds.png"),
    BeanStarter(CropType.GreenBean,"Items/SeedType/Bean_Starter.png"),
    CauliflowerSeed(CropType.Cauliflower,"Items/SeedType/Cauliflower_Seeds.png"),
    PotatoSeed(CropType.Potato,"Items/SeedType/Potato_Seeds.png"),
    StrawberrySeed(CropType.Strawberry,"Items/SeedType/Strawberry_Seeds.png"),
    TulipBulbSeed(CropType.Tulip,"Items/SeedType/Tulip_Bulb.png"),
    KaleSeed(CropType.Kale,"Items/SeedType/Kale_Seeds.png"),
    CarrotSeed(CropType.Carrot,"Items/SeedType/Carrot_Seeds.png"),
    RhubarbSeed(CropType.Rhubarb,"Items/SeedType/Rhubarb_Seeds.png"),
    JazzSeed(CropType.BlueJazz,"Items/SeedType/Jazz_Seeds.png"),
    TomatoSeed(CropType.Tomato,"Items/SeedType/Tomato_Seeds.png"),
    PepperSeed(CropType.HotPepper,"Items/SeedType/Pepper_Seeds.png"),
    WheatSeed(CropType.Wheat,"Items/SeedType/Wheat_Seeds.png"),
    SummerSquashSeed(CropType.SummerSquash,"Items/SeedType/Summer_Seeds.png"),
    RadishSeed(CropType.Radish,"Items/SeedType/Radish_Seeds.png"),
    MelonSeed(CropType.Melon,"Items/SeedType/Melon_Seeds.png"),
    HopsStarter(CropType.Hops,"Items/SeedType/Hops_Starter.png"),
    PoppySeed(CropType.Poppy,"Items/SeedType/Poppy_Seeds.png"),
    SpangleSeed(CropType.SummerSpangle,"Items/SeedType/Spangle_Seeds.png"),
    StarfruitSeed(CropType.Starfruit,"Items/SeedType/Starfruit_Seeds.png"),
    CoffeeBean(CropType.CoffeeBean,"Items/SeedType/Coffee_Bean.png"),
    SunflowerSeed(CropType.Sunflower,"Items/SeedType/Sunflower_Seeds.png"),
    CornSeed(CropType.Corn,"Items/SeedType/Corn_Seeds.png"),
    EggplantSeed(CropType.Eggplant,"Items/SeedType/Eggplant_Seeds.png"),
    PumpkinSeed(CropType.Pumpkin,"Items/SeedType/Pumpkin_Seeds.png"),
    BroccoliSeed(CropType.Broccoli,"Items/SeedType/Broccoli_Seeds.png"),
    AmaranthSeed(CropType.Amaranth,"Items/SeedType/Amaranth_Seeds.png"),
    GrapeStarter(CropType.Grape,"Items/SeedType/Grape_Starter.png"),
    BeetSeed(CropType.Beet,"Items/SeedType/Beet_Seeds.png"),
    YamSeed(CropType.Yam,"Items/SeedType/Yam_Seeds.png"),
    BokChoySeed(CropType.BokChoy,"Items/SeedType/Bok_Choy_Seeds.png"),
    CranberrySeed(CropType.Cranberry,"Items/SeedType/Cranberry_Seeds.png"),
    FairySeed(CropType.FairyRose,"Items/SeedType/Fairy_Seeds.png"),
    RareSeed(CropType.SweetGemBerry,"Items/SeedType/Rare_Seeds.png"),
    GarlicSeed(CropType.Garlic,"Items/SeedType/Garlic_Seeds.png"),
    BlueberrySeed(CropType.Blueberry,"Items/SeedType/Blueberry_Seeds.png"),
    RedCabbageSeed(CropType.RedCabbage,"Items/SeedType/Red_Cabbage_Seeds.png"),
    ArtichokeSeed(CropType.Artichoke,"Items/SeedType/Artichoke_Seeds.png"),
    RiceShoot(CropType.UnmilledRice,"Items/SeedType/Rice_Shoot.png"),
    PowderMelonSeed(CropType.PowderMelon,"Items/SeedType/PowderMelon_Seeds.png"),
    AncientSeed(CropType.AncientFruit,"Items/SeedType/Ancient_Seeds.png"),
    MixedSeed(null, "Items/SeedType/Mixed_Seeds.png"),
    MapleSeed(TreeType.MapleTree, "Items/SeedType/Maple_Seeds.png"),
    MahoganySeed(TreeType.MahoganyTree, "Items/SeedType/Mahogany_Seeds.png"),
    MushroomTreeSeed(TreeType.MushroomTree, "Items/SeedType/Dried_Purple_Mushrooms.png"),
    MysticTreeSeed(TreeType.MysticTree, "Items/SeedType/Mystic_Tree_Seeds.png"),;

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
    private final String address;

    SeedType(PlantType plant, String address) {
        this.plant = plant;
        this.address = address;
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


    public String getAddress(){
        return address;
    }

    @Override
    public Integer getPrice() {
        return 50;
    }

    public String toString() {
        return this.name();
    }
}
