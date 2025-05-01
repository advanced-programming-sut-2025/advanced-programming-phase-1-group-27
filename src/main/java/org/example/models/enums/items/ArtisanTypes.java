package org.example.models.enums.items;

import org.example.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;
import java.util.List;

public enum ArtisanTypes {
    BeeHouse(new ArrayList<>(List.of(ProcessedProductType.Honey))),
    CheesePress(new ArrayList<>(List.of(
            ProcessedProductType.Cheese,
            ProcessedProductType.LargeCheese,
            ProcessedProductType.GoatCheese,
            ProcessedProductType.LargeGoatCheese
    ))),
    Keg(new ArrayList<>(List.of(
            ProcessedProductType.Beer,
            ProcessedProductType.Vinegar,
            ProcessedProductType.Coffee,
            ProcessedProductType.AmaranthJuice,
            ProcessedProductType.ArtichokeJuice,
            ProcessedProductType.BeetJuice,
            ProcessedProductType.BokChoyJuice,
            ProcessedProductType.BroccoliJuice,
            ProcessedProductType.CarrotJuice,
            ProcessedProductType.CauliflowerJuice,
            ProcessedProductType.CornJuice,
            ProcessedProductType.EggplantJuice,
            ProcessedProductType.FiddleheadFernJuice,
            ProcessedProductType.GarlicJuice,
            ProcessedProductType.GreenBeanJuice,
            ProcessedProductType.HopsJuice,
            ProcessedProductType.KaleJuice,
            ProcessedProductType.ParsnipJuice,
            ProcessedProductType.PotatoJuice,
            ProcessedProductType.PumpkinJuice,
            ProcessedProductType.RadishJuice,
            ProcessedProductType.RedCabbageJuice,
            ProcessedProductType.SummerSquashJuice,
            ProcessedProductType.TomatoJuice,
            ProcessedProductType.UnmilledRiceJuice,
            ProcessedProductType.WheatJuice,
            ProcessedProductType.YamJuice,
            ProcessedProductType.Mead,
            ProcessedProductType.PaleAle,
            ProcessedProductType.AncientFruitWine,
            ProcessedProductType.AppleWine,
            ProcessedProductType.ApricotWine,
            ProcessedProductType.BananaWine,
            ProcessedProductType.BlackberryWine,
            ProcessedProductType.BlueberryWine,
            ProcessedProductType.CherryWine,
            ProcessedProductType.CranberriesWine,
            ProcessedProductType.CrystalFruitWine,
            ProcessedProductType.GrapeWine,
            ProcessedProductType.HotPepperWine,
            ProcessedProductType.MangoWine,
            ProcessedProductType.MelonWine,
            ProcessedProductType.OrangeWine,
            ProcessedProductType.PeachWine,
            ProcessedProductType.PomegranateWine,
            ProcessedProductType.PowderMelonWine,
            ProcessedProductType.RhubarbWine,
            ProcessedProductType.SalmonBerryWine,
            ProcessedProductType.SpiceBerryWine,
            ProcessedProductType.StarfruitWine,
            ProcessedProductType.StrawberryWine,
            ProcessedProductType.WildPlumWine
    ))),
    Dehydrator(new ArrayList<>(List.of(
            ProcessedProductType.ChanterelleDriedMushrooms,
            ProcessedProductType.CommonMushroomDriedMushrooms,
            ProcessedProductType.MorelDriedMushrooms,
            ProcessedProductType.PurpleMushroomDriedMushrooms,
            ProcessedProductType.RedMushroomDriedMushrooms,
            ProcessedProductType.AncientFruitDriedFruit,
            ProcessedProductType.AppleDriedFruit,
            ProcessedProductType.ApricotDriedFruit,
            ProcessedProductType.BananaDriedFruit,
            ProcessedProductType.BlackberryDriedFruit,
            ProcessedProductType.BlueberryDriedFruit,
            ProcessedProductType.CherryDriedFruit,
            ProcessedProductType.CranberriesDriedFruit,
            ProcessedProductType.CrystalFruitDriedFruit,
            ProcessedProductType.HotPepperDriedFruit,
            ProcessedProductType.MangoDriedFruit,
            ProcessedProductType.MelonDriedFruit,
            ProcessedProductType.OrangeDriedFruit,
            ProcessedProductType.PeachDriedFruit,
            ProcessedProductType.PomegranateDriedFruit,
            ProcessedProductType.PowderMelonDriedFruit,
            ProcessedProductType.RhubarbDriedFruit,
            ProcessedProductType.SalmonBerryDriedFruit,
            ProcessedProductType.SpiceBerryDriedFruit,
            ProcessedProductType.StarfruitDriedFruit,
            ProcessedProductType.StrawberryDriedFruit,
            ProcessedProductType.WildPlumDriedFruit,
            ProcessedProductType.Raisins
    ))),
    CharcoalKiln(new ArrayList<>(List.of(
            ProcessedProductType.Coal
    ))),
    Loom(new ArrayList<>(List.of(
            ProcessedProductType.Cloth
    ))),
    MayonnaiseMachine(new ArrayList<>(List.of(
            ProcessedProductType.Mayonnaise,
            ProcessedProductType.DuckMayonnaise,
            ProcessedProductType.DinosaurMayonnaise
    ))),
    OilMaker(new ArrayList<>(List.of(
            ProcessedProductType.TruffleOil,
            ProcessedProductType.CornOil,
            ProcessedProductType.SunflowerSeedOil,
            ProcessedProductType.SunflowerOil
    ))),
    PreservesJar(new ArrayList<>(List.of(
            ProcessedProductType.AmaranthPickles,
            ProcessedProductType.ArtichokePickles,
            ProcessedProductType.BeetPickles,
            ProcessedProductType.BokChoyPickles,
            ProcessedProductType.BroccoliPickles,
            ProcessedProductType.CarrotPickles,
            ProcessedProductType.CauliflowerPickles,
            ProcessedProductType.CornPickles,
            ProcessedProductType.EggplantPickles,
            ProcessedProductType.FiddleheadFernPickles,
            ProcessedProductType.GarlicPickles,
            ProcessedProductType.GreenBeanPickles,
            ProcessedProductType.HopsPickles,
            ProcessedProductType.KalePickles,
            ProcessedProductType.ParsnipPickles,
            ProcessedProductType.PotatoPickles,
            ProcessedProductType.PumpkinPickles,
            ProcessedProductType.RadishPickles,
            ProcessedProductType.RedCabbagePickles,
            ProcessedProductType.SummerSquashPickles,
            ProcessedProductType.TomatoPickles,
            ProcessedProductType.UnmilledRicePickles,
            ProcessedProductType.WheatPickles,
            ProcessedProductType.YamPickles,
            ProcessedProductType.AncientFruitJelly,
            ProcessedProductType.AppleJelly,
            ProcessedProductType.ApricotJelly,
            ProcessedProductType.BananaJelly,
            ProcessedProductType.BlackberryJelly,
            ProcessedProductType.BlueberryJelly,
            ProcessedProductType.CherryJelly,
            ProcessedProductType.CranberriesJelly,
            ProcessedProductType.CrystalFruitJelly,
            ProcessedProductType.GrapeJelly,
            ProcessedProductType.HotPepperJelly,
            ProcessedProductType.MangoJelly,
            ProcessedProductType.MelonJelly,
            ProcessedProductType.OrangeJelly,
            ProcessedProductType.PeachJelly,
            ProcessedProductType.PomegranateJelly,
            ProcessedProductType.PowderMelonJelly,
            ProcessedProductType.RhubarbJelly,
            ProcessedProductType.SalmonBerryJelly,
            ProcessedProductType.SpiceBerryJelly,
            ProcessedProductType.StarfruitJelly,
            ProcessedProductType.StrawberryJelly,
            ProcessedProductType.WildPlumJelly
    ))),
    FishSmoker(new ArrayList<>(List.of(
            ProcessedProductType.SalmonSmokeFish,
            ProcessedProductType.SardineSmokeFish,
            ProcessedProductType.ShadSmokeFish,
            ProcessedProductType.BlueDiscusSmokeFish,
            ProcessedProductType.MidnightCarpSmokeFish,
            ProcessedProductType.SquidSmokeFish,
            ProcessedProductType.TunaSmokeFish,
            ProcessedProductType.PerchSmokeFish,
            ProcessedProductType.FlounderSmokeFish,
            ProcessedProductType.LionfishSmokeFish,
            ProcessedProductType.HerringSmokeFish,
            ProcessedProductType.GhostfishSmokeFish,
            ProcessedProductType.TilapiaSmokeFish,
            ProcessedProductType.DoradoSmokeFish,
            ProcessedProductType.SunfishSmokeFish,
            ProcessedProductType.RainbowTroutSmokeFish,
            ProcessedProductType.LegendSmokeFish,
            ProcessedProductType.GlacierfishSmokeFish,
            ProcessedProductType.AnglerSmokeFish,
            ProcessedProductType.CrimsonfishSmokeFish
    ))),
    Furnace(new ArrayList<>(List.of(
            ProcessedProductType.CopperMetalBar,
            ProcessedProductType.IronMetalBar,
            ProcessedProductType.GoldMetalBar,
            ProcessedProductType.IridiumMetalBar
    )));

    private final ArrayList<ProcessedProductType> products;

    ArtisanTypes(ArrayList<ProcessedProductType> products) {
        this.products = products;
    }

    public ArrayList<ProcessedProductType> getProducts() {
        return products;
    }
}
