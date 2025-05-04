package org.example.models.enums.Plants;

import org.example.models.Item;
import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum SeedType implements PlantSourceType, Item {
    //Starter + Seed + Bean + Shoot
    GrassStater,
    ParsnipSeed,
    BeanStarter,
    CauliflowerSeed,
    PotatoSeed,
    StrawberrySeed,
    TulipBulbSeed,
    KaleSeed,
    CarrotSeed,
    RhubarbSeed,
    JazzSeed,
    TomatoSeed,
    PepperSeed,
    WheatSeed,
    SummerSquashSeed,
    RadishSeed,
    MelonSeed,
    HopsStarter,
    PoppySeed,
    SpangleSeed,
    StarfruitSeed,
    CoffeeBean,
    SunflowerSeed,
    CornSeed,
    EggplantSeed,
    PumpkinSeed,
    BroccoliSeed,
    AmaranthSeed,
    GrapeStarter,
    BeetSeed,
    YamSeed,
    BokChoySeed,
    CranberrySeed,
    FairySeed,
    RareSeed,
    GarlicSeed,
    BlueberrySeed,
    RedCabbageSeed,
    ArtichokeSeed,
    RiceShoot,
    PowderMelonSeed,
    AncientSeed,
    MixedSeed,
    MapleSeed,
    MahoganySeed,
    MushroomTreeSeed,
    MysticTreeSeed;

    private static HashMap<Season, ArrayList<SeedType>> foragingSeedsBySeason = new HashMap<>(){{
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

    public boolean isFarmable() {
        //TODO
        return true;
    }

    public static HashMap<Season, ArrayList<SeedType>> getForagingSeedsBySeason() {
        return foragingSeedsBySeason;
    }

    @Override
    public Integer getPrice() {
        return 0;
    }
}
