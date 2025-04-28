package org.example.models.enums.items;

import org.example.models.enums.items.products.ProcessedProduct;

import java.util.ArrayList;
import java.util.List;

public enum ArtisanTypes {
    BeeHouse(new ArrayList<>(List.of(ProcessedProduct.Honey))),
    CheesePress(new ArrayList<>(List.of(
            ProcessedProduct.Cheese,
            ProcessedProduct.LargeCheese,
            ProcessedProduct.GoatCheese,
            ProcessedProduct.LargeGoatCheese
    ))),
    Keg(new ArrayList<>(List.of(
            ProcessedProduct.Beer,
            ProcessedProduct.Vinegar,
            ProcessedProduct.Coffee,
            ProcessedProduct.AmaranthJuice,
            ProcessedProduct.ArtichokeJuice,
            ProcessedProduct.BeetJuice,
            ProcessedProduct.BokChoyJuice,
            ProcessedProduct.BroccoliJuice,
            ProcessedProduct.CarrotJuice,
            ProcessedProduct.CauliflowerJuice,
            ProcessedProduct.CornJuice,
            ProcessedProduct.EggplantJuice,
            ProcessedProduct.FiddleheadFernJuice,
            ProcessedProduct.GarlicJuice,
            ProcessedProduct.GreenBeanJuice,
            ProcessedProduct.HopsJuice,
            ProcessedProduct.KaleJuice,
            ProcessedProduct.ParsnipJuice,
            ProcessedProduct.PotatoJuice,
            ProcessedProduct.PumpkinJuice,
            ProcessedProduct.RadishJuice,
            ProcessedProduct.RedCabbageJuice,
            ProcessedProduct.SummerSquashJuice,
            ProcessedProduct.TomatoJuice,
            ProcessedProduct.UnmilledRiceJuice,
            ProcessedProduct.WheatJuice,
            ProcessedProduct.YamJuice,
            ProcessedProduct.Mead,
            ProcessedProduct.PaleAle,
            ProcessedProduct.AncientFruitWine,
            ProcessedProduct.AppleWine,
            ProcessedProduct.ApricotWine,
            ProcessedProduct.BananaWine,
            ProcessedProduct.BlackberryWine,
            ProcessedProduct.BlueberryWine,
            ProcessedProduct.CherryWine,
            ProcessedProduct.CranberriesWine,
            ProcessedProduct.CrystalFruitWine,
            ProcessedProduct.GrapeWine,
            ProcessedProduct.HotPepperWine,
            ProcessedProduct.MangoWine,
            ProcessedProduct.MelonWine,
            ProcessedProduct.OrangeWine,
            ProcessedProduct.PeachWine,
            ProcessedProduct.PomegranateWine,
            ProcessedProduct.PowdermelonWine,
            ProcessedProduct.RhubarbWine,
            ProcessedProduct.SalmonBerryWine,
            ProcessedProduct.SpiceBerryWine,
            ProcessedProduct.StarfruitWine,
            ProcessedProduct.StrawberryWine,
            ProcessedProduct.WildPlumWine
    ))),
    Dehydrator(new ArrayList<>(List.of(
            ProcessedProduct.ChanterelleDriedMushrooms,
            ProcessedProduct.CommonMushroomDriedMushrooms,
            ProcessedProduct.MorelDriedMushrooms,
            ProcessedProduct.PurpleMushroomDriedMushrooms,
            ProcessedProduct.RedMushroomDriedMushrooms,
            ProcessedProduct.AncientFruitDriedFruit,
            ProcessedProduct.AppleDriedFruit,
            ProcessedProduct.ApricotDriedFruit,
            ProcessedProduct.BananaDriedFruit,
            ProcessedProduct.BlackberryDriedFruit,
            ProcessedProduct.BlueberryDriedFruit,
            ProcessedProduct.CherryDriedFruit,
            ProcessedProduct.CranberriesDriedFruit,
            ProcessedProduct.CrystalFruitDriedFruit,
            ProcessedProduct.HotPepperDriedFruit,
            ProcessedProduct.MangoDriedFruit,
            ProcessedProduct.MelonDriedFruit,
            ProcessedProduct.OrangeDriedFruit,
            ProcessedProduct.PeachDriedFruit,
            ProcessedProduct.PomegranateDriedFruit,
            ProcessedProduct.PowderMelonDriedFruit,
            ProcessedProduct.RhubarbDriedFruit,
            ProcessedProduct.SalmonBerryDriedFruit,
            ProcessedProduct.SpiceBerryDriedFruit,
            ProcessedProduct.StarfruitDriedFruit,
            ProcessedProduct.StrawberryDriedFruit,
            ProcessedProduct.WildPlumDriedFruit,
            ProcessedProduct.Raisins
    ))),
    CharcoalKiln(new ArrayList<>(List.of(
            ProcessedProduct.Coal
    ))),
    Loom(new ArrayList<>(List.of(
            ProcessedProduct.Cloth
    ))),
    MayonnaiseMachine(new ArrayList<>(List.of(
            ProcessedProduct.Mayonnaise,
            ProcessedProduct.DuckMayonnaise,
            ProcessedProduct.DinosaurMayonnaise
    ))),
    OilMaker(new ArrayList<>(List.of(
            ProcessedProduct.TruffleOil,
            ProcessedProduct.CornOil,
            ProcessedProduct.SunflowerSeedOil,
            ProcessedProduct.SunflowerOil
    ))),
    PreservesJar(new ArrayList<>(List.of(
            ProcessedProduct.AmaranthPickles,
            ProcessedProduct.ArtichokePickles,
            ProcessedProduct.BeetPickles,
            ProcessedProduct.BokChoyPickles,
            ProcessedProduct.BroccoliPickles,
            ProcessedProduct.CarrotPickles,
            ProcessedProduct.CauliflowerPickles,
            ProcessedProduct.CornPickles,
            ProcessedProduct.EggplantPickles,
            ProcessedProduct.FiddleheadFernPickles,
            ProcessedProduct.GarlicPickles,
            ProcessedProduct.GreenBeanPickles,
            ProcessedProduct.HopsPickles,
            ProcessedProduct.KalePickles,
            ProcessedProduct.ParsnipPickles,
            ProcessedProduct.PotatoPickles,
            ProcessedProduct.PumpkinPickles,
            ProcessedProduct.RadishPickles,
            ProcessedProduct.RedCabbagePickles,
            ProcessedProduct.SummerSquashPickles,
            ProcessedProduct.TomatoPickles,
            ProcessedProduct.UnmilledRicePickles,
            ProcessedProduct.WheatPickles,
            ProcessedProduct.YamPickles,
            ProcessedProduct.AncientFruitJelly,
            ProcessedProduct.AppleJelly,
            ProcessedProduct.ApricotJelly,
            ProcessedProduct.BananaJelly,
            ProcessedProduct.BlackberryJelly,
            ProcessedProduct.BlueberryJelly,
            ProcessedProduct.CherryJelly,
            ProcessedProduct.CranberriesJelly,
            ProcessedProduct.CrystalFruitJelly,
            ProcessedProduct.GrapeJelly,
            ProcessedProduct.HotPepperJelly,
            ProcessedProduct.MangoJelly,
            ProcessedProduct.MelonJelly,
            ProcessedProduct.OrangeJelly,
            ProcessedProduct.PeachJelly,
            ProcessedProduct.PomegranateJelly,
            ProcessedProduct.PowderMelonJelly,
            ProcessedProduct.RhubarbJelly,
            ProcessedProduct.SalmonBerryJelly,
            ProcessedProduct.SpiceBerryJelly,
            ProcessedProduct.StarfruitJelly,
            ProcessedProduct.StrawberryJelly,
            ProcessedProduct.WildPlumJelly
    ))),
    FishSmoker(new ArrayList<>(List.of(
            ProcessedProduct.SalmonSmokeFish,
            ProcessedProduct.SardineSmokeFish,
            ProcessedProduct.ShadSmokeFish,
            ProcessedProduct.BlueDiscusSmokeFish,
            ProcessedProduct.MidnightCarpSmokeFish,
            ProcessedProduct.SquidSmokeFish,
            ProcessedProduct.TunaSmokeFish,
            ProcessedProduct.PerchSmokeFish,
            ProcessedProduct.FlounderSmokeFish,
            ProcessedProduct.LionfishSmokeFish,
            ProcessedProduct.HerringSmokeFish,
            ProcessedProduct.GhostfishSmokeFish,
            ProcessedProduct.TilapiaSmokeFish,
            ProcessedProduct.DoradoSmokeFish,
            ProcessedProduct.SunfishSmokeFish,
            ProcessedProduct.RainbowTroutSmokeFish,
            ProcessedProduct.LegendSmokeFish,
            ProcessedProduct.GlacierfishSmokeFish,
            ProcessedProduct.AnglerSmokeFish,
            ProcessedProduct.CrimsonfishSmokeFish
    ))),
    Furnace(new ArrayList<>(List.of(
            ProcessedProduct.CopperMetalBar,
            ProcessedProduct.IronMetalBar,
            ProcessedProduct.GoldMetalBar,
            ProcessedProduct.IridiumMetalBar
    )));

    private final ArrayList<ProcessedProduct> products;

    ArtisanTypes(ArrayList<ProcessedProduct> products) {
        this.products = products;
    }

    public ArrayList<ProcessedProduct> getProducts() {
        return products;
    }
}
