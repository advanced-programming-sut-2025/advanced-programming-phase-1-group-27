package org.example.models.enums.items;

import org.example.models.Stack;
import org.example.models.enums.items.products.AnimalProduct;
import org.example.models.enums.items.products.ProcessedProduct;

import java.util.ArrayList;
import java.util.List;

public enum Recipe implements Item {
    HoneyRecipe(0, ProcessedProduct.Honey, new ArrayList<>(List.of())),
    CheeseRecipe(0, ProcessedProduct.Cheese, new ArrayList<>(List.of(
            new Stack(AnimalProduct.CowMilk, 1)
    ))),
    LargeCheeseRecipe(0, ProcessedProduct.LargeCheese, new ArrayList<>(List.of(
            new Stack(AnimalProduct.LargeCowMilk, 1)
    ))),
    GoatCheeseRecipe(0, ProcessedProduct.GoatCheese, new ArrayList<>(List.of(
            new Stack(AnimalProduct.GoatMilk, 1)
    ))),
    LargeGoatCheeseRecipe(0, ProcessedProduct.LargeGoatCheese, new ArrayList<>(List.of(
            new Stack(AnimalProduct.LargeGoatMilk, 1)
    ))),
    BeerRecipe(0, ProcessedProduct.Beer, new ArrayList<>(List.of(
            new Stack(CropType.Wheat, 1)
    ))),
    VinegarRecipe(0, ProcessedProduct.Vinegar, new ArrayList<>(List.of(
            new Stack(ProcessedProduct.Rice, 1)
    ))),
    CoffeeRecipe(0, ProcessedProduct.Coffee, new ArrayList<>(List.of(
            new Stack(CropType.CoffeeBean, 5)
    ))),
    AmaranthJuiceRecipe(0, ProcessedProduct.AmaranthJuice, new ArrayList<>(List.of(
            new Stack(CropType.Amaranth, 1)
    ))),
    ArtichokeJuiceRecipe(0, ProcessedProduct.ArtichokeJuice, new ArrayList<>(List.of(
            new Stack(CropType.Artichoke, 1)
    ))),
    BeetJuiceRecipe(0, ProcessedProduct.BeetJuice, new ArrayList<>(List.of(
            new Stack(CropType.Beet, 1)
    ))),
    BokChoyJuiceRecipe(0, ProcessedProduct.BokChoyJuice, new ArrayList<>(List.of(
            new Stack(CropType.BokChoy, 1)
    ))),
    BroccoliJuiceRecipe(0, ProcessedProduct.BroccoliJuice, new ArrayList<>(List.of(
            new Stack(CropType.Broccoli, 1)
    ))),
    CarrotJuiceRecipe(0, ProcessedProduct.CarrotJuice, new ArrayList<>(List.of(
            new Stack(CropType.Carrot, 1)
    ))),
    CauliflowerJuiceRecipe(0, ProcessedProduct.CauliflowerJuice, new ArrayList<>(List.of(
            new Stack(CropType.Cauliflower, 1)
    ))),
    CornJuiceRecipe(0, ProcessedProduct.CornJuice, new ArrayList<>(List.of(
            new Stack(CropType.Corn, 1)
    ))),
    EggplantJuiceRecipe(0, ProcessedProduct.EggplantJuice, new ArrayList<>(List.of(
            new Stack(CropType.Eggplant, 1)
    ))),
    FiddleheadFernJuiceRecipe(0, ProcessedProduct.FiddleheadFernJuice, new ArrayList<>(List.of(
            new Stack(CropType.FiddleheadFern, 1)
    ))),
    GarlicJuiceRecipe(0, ProcessedProduct.GarlicJuice, new ArrayList<>(List.of(
            new Stack(CropType.Garlic, 1)
    ))),
    GreenBeanJuiceRecipe(0, ProcessedProduct.GreenBeanJuice, new ArrayList<>(List.of(
            new Stack(CropType.GreenBean, 1)
    ))),
    HopsJuiceRecipe(0, ProcessedProduct.HopsJuice, new ArrayList<>(List.of(
            new Stack(CropType.Hops, 1)
    ))),
    KaleJuiceRecipe(0, ProcessedProduct.KaleJuice, new ArrayList<>(List.of(
            new Stack(CropType.Kale, 1)
    ))),
    ParsnipJuiceRecipe(0, ProcessedProduct.ParsnipJuice, new ArrayList<>(List.of(
            new Stack(CropType.Parsnip, 1)
    ))),
    PotatoJuiceRecipe(0, ProcessedProduct.PotatoJuice, new ArrayList<>(List.of(
            new Stack(CropType.Potato, 1)
    ))),
    PumpkinJuiceRecipe(0, ProcessedProduct.PumpkinJuice, new ArrayList<>(List.of(
            new Stack(CropType.Pumpkin, 1)
    ))),
    RadishJuiceRecipe(0, ProcessedProduct.RadishJuice, new ArrayList<>(List.of(
            new Stack(CropType.Radish, 1)
    ))),
    RedCabbageJuiceRecipe(0, ProcessedProduct.RedCabbageJuice, new ArrayList<>(List.of(
            new Stack(CropType.RedCabbage, 1)
    ))),
    SummerSquashJuiceRecipe(0, ProcessedProduct.SummerSquashJuice, new ArrayList<>(List.of(
            new Stack(CropType.SummerSquash, 1)
    ))),
    TomatoJuiceRecipe(0, ProcessedProduct.TomatoJuice, new ArrayList<>(List.of(
            new Stack(CropType.Tomato, 1)
    ))),
    UnmilledRiceJuiceRecipe(0, ProcessedProduct.UnmilledRiceJuice, new ArrayList<>(List.of(
            new Stack(CropType.UnmilledRice, 1)
    ))),
    WheatJuiceRecipe(0, ProcessedProduct.WheatJuice, new ArrayList<>(List.of(
            new Stack(CropType.Wheat, 1)
    ))),
    YamJuiceRecipe(0, ProcessedProduct.YamJuice, new ArrayList<>(List.of(
            new Stack(CropType.Yam, 1)
    ))),
    MeadRecipe(0, ProcessedProduct.Mead, new ArrayList<>(List.of(
            new Stack(ProcessedProduct.Honey, 1)
    ))),
    PaleAleRecipe(0, ProcessedProduct.PaleAle, new ArrayList<>(List.of(
            new Stack(CropType.Hops, 1)
    ))),
    AncientFruitWineRecipe(0, ProcessedProduct.AncientFruitWine, new ArrayList<>(List.of(
            new Stack(CropType.AncientFruit, 1)
    ))),
    AppleWineRecipe(0, ProcessedProduct.AppleWine, new ArrayList<>(List.of(
            new Stack(FruitType.Apple, 1)
    ))),
    ApricotWineRecipe(0, ProcessedProduct.ApricotWine, new ArrayList<>(List.of(
            new Stack(FruitType.Apricot, 1)
    ))),
    BananaWineRecipe(0, ProcessedProduct.BananaWine, new ArrayList<>(List.of(
            new Stack(FruitType.Banana, 1)
    ))),
    BlackberryWineRecipe(0, ProcessedProduct.BlackberryWine, new ArrayList<>(List.of(
            new Stack(CropType.Blackberry, 1)
    ))),
    BlueberryWineRecipe(0, ProcessedProduct.BlueberryWine, new ArrayList<>(List.of(
            new Stack(CropType.Blueberry, 1)
    ))),
    CherryWineRecipe(0, ProcessedProduct.CherryWine, new ArrayList<>(List.of(
            new Stack(FruitType.Cherry, 1)
    ))),
    CranberriesWineRecipe(0, ProcessedProduct.CranberriesWine, new ArrayList<>(List.of(
            new Stack(CropType.Cranberry, 1)
    ))),
    CrystalFruitWineRecipe(0, ProcessedProduct.CrystalFruitWine, new ArrayList<>(List.of(
            new Stack(CropType.CrystalFruit, 1)
    ))),
    GrapeWineRecipe(0, ProcessedProduct.GrapeWine, new ArrayList<>(List.of(
            new Stack(CropType.Grape, 1)
    ))),
    HotPepperWineRecipe(0, ProcessedProduct.HotPepperWine, new ArrayList<>(List.of(
            new Stack(CropType.HotPepper, 1)
    ))),
    MangoWineRecipe(0, ProcessedProduct.MangoWine, new ArrayList<>(List.of(
            new Stack(FruitType.Mango, 1)
    ))),
    MelonWineRecipe(0, ProcessedProduct.MelonWine, new ArrayList<>(List.of(
            new Stack(CropType.Melon, 1)
    ))),
    OrangeWineRecipe(0, ProcessedProduct.OrangeWine, new ArrayList<>(List.of(
            new Stack(FruitType.Orange, 1)
    ))),
    PeachWineRecipe(0, ProcessedProduct.PeachWine, new ArrayList<>(List.of(
            new Stack(FruitType.Peach, 1)
    ))),
    PomegranateWineRecipe(0, ProcessedProduct.PomegranateWine, new ArrayList<>(List.of(
            new Stack(FruitType.Pomegranate, 1)
    ))),
    PowderMelonWineRecipe(0, ProcessedProduct.PowderMelonWine, new ArrayList<>(List.of(
            new Stack(CropType.PowderMelon, 1)
    ))),
    RhubarbWineRecipe(0, ProcessedProduct.RhubarbWine, new ArrayList<>(List.of(
            new Stack(CropType.Rhubarb, 1)
    ))),
    SalmonBerryWineRecipe(0, ProcessedProduct.SalmonBerryWine, new ArrayList<>(List.of(
            new Stack(CropType.SalmonBerry, 1)
    ))),
    SpiceBerryWineRecipe(0, ProcessedProduct.SpiceBerryWine, new ArrayList<>(List.of(
            new Stack(CropType.SpiceBerry, 1)
    ))),
    StarfruitWineRecipe(0, ProcessedProduct.StarfruitWine, new ArrayList<>(List.of(
            new Stack(CropType.Starfruit, 1)
    ))),
    StrawberryWineRecipe(0, ProcessedProduct.StrawberryWine, new ArrayList<>(List.of(
            new Stack(CropType.Strawberry, 1)
    ))),
    WildPlumWineRecipe(0, ProcessedProduct.WildPlumWine, new ArrayList<>(List.of(
            new Stack(CropType.WildPlum, 1)
    ))),
    ChanterelleDriedMushroomsRecipe(0, ProcessedProduct.ChanterelleDriedMushrooms, new ArrayList<>(List.of(
            new Stack(CropType.Chanterelle, 5)
    ))),
    CommonMushroomDriedMushroomsRecipe(0, ProcessedProduct.CommonMushroomDriedMushrooms, new ArrayList<>(List.of(
            new Stack(FruitType.CommonMushroom, 5)
    ))),
    MorelDriedMushroomsRecipe(0, ProcessedProduct.MorelDriedMushrooms, new ArrayList<>(List.of(
            new Stack(CropType.Morel, 5)
    ))),
    PurpleMushroomDriedMushroomsRecipe(0, ProcessedProduct.PurpleMushroomDriedMushrooms, new ArrayList<>(List.of(
            new Stack(CropType.PurpleMushroom, 5)
    ))),
    RedMushroomDriedMushroomsRecipe(0 , ProcessedProduct.RedMushroomDriedMushrooms, new ArrayList<>(List.of(
            new Stack(CropType.RedMushroom, 5)
    ))),
    AncientFruitDriedFruitRecipe(0 , ProcessedProduct.AncientFruitDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.AncientFruit, 5)
    ))),
    AppleDriedFruitRecipe(0 , ProcessedProduct.AppleDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Apple, 5)
    )) ),
    ApricotDriedFruitRecipe(0 , ProcessedProduct.ApricotDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Apricot, 5)
    )) ),
    BananaDriedFruitRecipe(0 , ProcessedProduct.BananaDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Banana, 5)
    )) ),
    BlackberryDriedFruitRecipe(0 , ProcessedProduct.BlackberryDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.Blackberry, 5)
    )) ),
    BlueberryDriedFruitRecipe(0 , ProcessedProduct.BlueberryDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.Blueberry, 5)
    )) ),
    CherryDriedFruitRecipe(0 , ProcessedProduct.CherryDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Cherry, 5)
    )) ),
    CranberriesDriedFruitRecipe(0 , ProcessedProduct.CranberriesDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.Cranberry , 5)
    )) ),
    CrystalFruitDriedFruitRecipe(0 , ProcessedProduct.CrystalFruitDriedFruit , new ArrayList<>(List.of(
            new Stack(CropType.CrystalFruit , 5)
    )) ),
    HotPepperDriedFruitRecipe(0 , ProcessedProduct.HotPepperDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.HotPepper, 5)
    )) ),
    MangoDriedFruitRecipe(0 , ProcessedProduct.MangoDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Mango, 5)
    )) ),
    MelonDriedFruitRecipe(0 , ProcessedProduct.MelonDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.Melon, 5)
    )) ),
    OrangeDriedFruitRecipe(0 , ProcessedProduct.OrangeDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Orange, 5)
    )) ),
    PeachDriedFruitRecipe(0 , ProcessedProduct.PeachDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Peach, 5)
    )) ),
    PomegranateDriedFruitRecipe(0 , ProcessedProduct.PomegranateDriedFruit, new ArrayList<>(List.of(
            new Stack(FruitType.Pomegranate, 5)
    )) ),
    PowderMelonDriedFruitRecipe(0 , ProcessedProduct.PowderMelonDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.PowderMelon , 5)
    )) ),
    RhubarbDriedFruitRecipe(0 , ProcessedProduct.RhubarbDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.Rhubarb, 5)
    )) ),
    SalmonBerryDriedFruitRecipe(0 , ProcessedProduct.SalmonBerryDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.SalmonBerry, 5)
    )) ),
    SpiceBerryDriedFruitRecipe(0 , ProcessedProduct.SpiceBerryDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.SpiceBerry, 5)
    )) ),
    StarfruitDriedFruitRecipe(0 , ProcessedProduct.StarfruitDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.Starfruit, 5)
    )) ),
    StrawberryDriedFruitRecipe(0 , ProcessedProduct.StrawberryDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.Strawberry, 5)
    )) ),
    WildPlumDriedFruitRecipe(0 , ProcessedProduct.WildPlumDriedFruit, new ArrayList<>(List.of(
            new Stack(CropType.WildPlum, 5)
    )) ),
    RaisinsRecipe(0 , ProcessedProduct.Raisins, new ArrayList<>(List.of(
            new Stack(CropType.Grape , 5)
    ))),
    CoalRecipe(0 , ProcessedProduct.Coal, new ArrayList<>(List.of(
            new Stack(MineralType.Wood , 10)
    ))),
    ClothRecipe(0 , ProcessedProduct.Cloth, new ArrayList<>(List.of(
            new Stack(AnimalProduct.Wool , 1)
    ))),
    MayonnaiseRecipe(0 , ProcessedProduct.Mayonnaise, new ArrayList<>(List.of(
            new Stack(AnimalProduct.Egg , 1)
    ))),
    LargeMayonnaiseRecipe(0 , ProcessedProduct.LargeMayonnaise, new ArrayList<>(List.of(
            new Stack(AnimalProduct.LargeEgg , 1)
    ))),
    DuckMayonnaiseRecipe(0 , ProcessedProduct.DuckMayonnaise, new ArrayList<>(List.of(
            new Stack(AnimalProduct.DuckEgg , 1)
    ))),
    DinosaurMayonnaiseRecipe(0 , ProcessedProduct.DinosaurMayonnaise, new ArrayList<>(List.of(
            new Stack(AnimalProduct.DinosaurEgg , 1)
    ))),
    TruffleOilRecipe(0 , ProcessedProduct.TruffleOil, new ArrayList<>(List.of(
            new Stack(AnimalProduct.Truffle , 1)
    ))),
    CornOilRecipe(0 , ProcessedProduct.CornOil, new ArrayList<>(List.of(
            new Stack(CropType.Corn , 1)
    ))),
    SunflowerSeedOilRecipe(0 , ProcessedProduct.SunflowerSeedOil, new ArrayList<>(List.of(
            new Stack(SeedType.SunflowerSeed , 1)
    ))),
    SunflowerOilRecipe(0 , ProcessedProduct.SunflowerOil, new ArrayList<>(List.of(
            new Stack(CropType.Sunflower , 1)
    ))),
    AmaranthPicklesRecipe(0 , ProcessedProduct.AmaranthPickles, new ArrayList<>(List.of(
            new Stack(CropType.Amaranth , 1)
    ))),
    ArtichokePicklesRecipe(0 , ProcessedProduct.ArtichokePickles, new ArrayList<>(List.of(
            new Stack(CropType.Artichoke , 1)
    ))),
    BeetPicklesRecipe(0 , ProcessedProduct.BeetPickles, new ArrayList<>(List.of(
            new Stack(CropType.Beet , 1)
    ))),
    BokChoyPicklesRecipe(0, ProcessedProduct.BokChoyPickles, new ArrayList<>(List.of(
            new Stack(CropType.BokChoy , 1)
    ))),
    BroccoliPicklesRecipe(0 , ProcessedProduct.BroccoliPickles, new ArrayList<>(List.of(
            new Stack(CropType.Broccoli , 1)
    ))),
    CarrotPicklesRecipe(0 , ProcessedProduct.CarrotPickles, new ArrayList<>(List.of(
            new Stack(CropType.Carrot , 1)
    ))),
    CauliflowerPicklesRecipe(0 , ProcessedProduct.CauliflowerPickles, new ArrayList<>(List.of(
            new Stack(CropType.Cauliflower , 1)
    ))),
    CornPicklesRecipe(0 , ProcessedProduct.CornPickles, new ArrayList<>(List.of(
            new Stack(CropType.Corn , 1)
    ))),
    EggplantPicklesRecipe(0 , ProcessedProduct.EggplantPickles, new ArrayList<>(List.of(
            new Stack(CropType.Eggplant, 1)
    ))),
    FiddleheadFernPicklesRecipe(0 , ProcessedProduct.FiddleheadFernPickles, new ArrayList<>(List.of(
            new Stack(CropType.Eggplant , 1)
    ))),
    GarlicPicklesRecipe(0 , ProcessedProduct.GarlicPickles, new ArrayList<>(List.of(
            new Stack(CropType.Garlic, 1)
    ))),
    GreenBeanPicklesRecipe(0 , ProcessedProduct.GreenBeanPickles, new ArrayList<>(List.of(
            new Stack(CropType.GreenBean , 1)
    ))),
    HopsPicklesRecipe(0 , ProcessedProduct.HopsPickles, new ArrayList<>(List.of(
            new Stack(CropType.Hops , 1)
    ))),
    KalePicklesRecipe(0 , ProcessedProduct.KalePickles, new ArrayList<>(List.of(
            new Stack(CropType.Kale , 1)
    ))),
    ParsnipPicklesRecipe(0 , ProcessedProduct.ParsnipPickles, new ArrayList<>(List.of(
            new Stack(CropType.Parsnip , 1)
    ))),
    PotatoPicklesRecipe(0 , ProcessedProduct.PotatoPickles, new ArrayList<>(List.of(
            new Stack(CropType.Potato , 1)
    ))),
    PumpkinPicklesRecipe(0 , ProcessedProduct.PumpkinPickles, new ArrayList<>(List.of(
            new Stack(CropType.Pumpkin , 1)
    ))),
    RadishPicklesRecipe(0 , ProcessedProduct.RadishPickles, new ArrayList<>(List.of(
            new Stack(CropType.Radish , 1)
    ))),
    RedCabbagePicklesRecipe(0 , ProcessedProduct.RedCabbagePickles, new ArrayList<>(List.of(
            new Stack(CropType.RedCabbage , 1)
    ))),
    SummerSquashPicklesRecipe(0 , ProcessedProduct.SummerSquashPickles, new ArrayList<>(List.of(
            new Stack(CropType.SummerSquash , 1)
    ))),
    TomatoPicklesRecipe(0 , ProcessedProduct.TomatoPickles, new ArrayList<>(List.of(
            new Stack(CropType.Tomato , 1)
    ))),
    UnmilledRicePicklesRecipe(0 , ProcessedProduct.UnmilledRicePickles, new ArrayList<>(List.of(
            new Stack(CropType.UnmilledRice , 1)
    ))),
    WheatPicklesRecipe(0 , ProcessedProduct.WheatPickles, new ArrayList<>(List.of(
            new Stack(CropType.Wheat , 1)
    ))),
    YamPicklesRecipe(0 , ProcessedProduct.YamPickles, new ArrayList<>(List.of(
            new Stack(CropType.Yam , 1)
    ))),
    AncientFruitJellyRecipe(0 , ProcessedProduct.AncientFruitJelly, new ArrayList<>(List.of(
            new Stack(CropType.AncientFruit , 1)
    ))),
    AppleJellyRecipe(0 , ProcessedProduct.AppleJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Apple , 1)
    ))),
    ApricotJellyRecipe(0 , ProcessedProduct.ApricotJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Apricot , 1)
    ))),
    BananaJellyRecipe(0 , ProcessedProduct.BananaJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Banana , 1)
    ))),
    BlackberryJellyRecipe(0 , ProcessedProduct.BlackberryJelly, new ArrayList<>(List.of(
            new Stack(CropType.Blackberry , 1)
    ))),
    BlueberryJellyRecipe(0 , ProcessedProduct.BlueberryJelly, new ArrayList<>(List.of(
            new Stack(CropType.Blueberry , 1)
    ))),
    CherryJellyRecipe(0 , ProcessedProduct.CherryJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Cherry , 1)
    ))),
    CranberriesJellyRecipe(0 , ProcessedProduct.CranberriesJelly, new ArrayList<>(List.of(
            new Stack(CropType.Cranberry , 1)
    ))),
    CrystalFruitJellyRecipe(0 , ProcessedProduct.CrystalFruitJelly, new ArrayList<>(List.of(
            new Stack(CropType.CrystalFruit , 1)
    ))),
    GrapeJellyRecipe(0 , ProcessedProduct.GrapeJelly, new ArrayList<>(List.of(
            new Stack(CropType.Grape , 1)
    ))),
    HotPepperJellyRecipe(0 , ProcessedProduct.HotPepperJelly, new ArrayList<>(List.of(
            new Stack(CropType.HotPepper , 1)
    ))),
    MangoJellyRecipe(0 , ProcessedProduct.MangoJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Mango , 1)
    ))),
    MelonJellyRecipe(0 , ProcessedProduct.MelonJelly, new ArrayList<>(List.of(
            new Stack(CropType.Melon , 1)
    ))),
    OrangeJellyRecipe(0 , ProcessedProduct.OrangeJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Orange , 1)
    ))),
    PeachJellyRecipe(0 , ProcessedProduct.PeachJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Peach , 1)
    ))),
    PomegranateJellyRecipe(0 , ProcessedProduct.PomegranateJelly, new ArrayList<>(List.of(
            new Stack(FruitType.Pomegranate , 1)
    ))),
    PowdermelonJellyRecipe(0 , ProcessedProduct.PowderMelonJelly, new ArrayList<>(List.of(
            new Stack(CropType.PowderMelon , 1)
    ))),
    RhubarbJellyRecipe(0 , ProcessedProduct.RhubarbJelly, new ArrayList<>(List.of(
            new Stack(CropType.Rhubarb , 1)
    ))),
    SalmonberryJellyRecipe(0 , ProcessedProduct.SalmonBerryJelly, new ArrayList<>(List.of(
            new Stack(CropType.SalmonBerry, 1)
    ))),
    SpiceBerryJellyRecipe(0 , ProcessedProduct.SpiceBerryJelly, new ArrayList<>(List.of(
            new Stack(CropType.SpiceBerry , 1)
    ))),
    StarfruitJellyRecipe(0 , ProcessedProduct.StarfruitJelly, new ArrayList<>(List.of(
            new Stack(CropType.Starfruit , 1)
    ))),
    StrawberryJellyRecipe(0 , ProcessedProduct.StrawberryJelly, new ArrayList<>(List.of(
            new Stack(CropType.Strawberry , 1)
    ))),
    WildPlumJellyRecipe(0 , ProcessedProduct.WildPlumJelly, new ArrayList<>(List.of(
            new Stack(CropType.WildPlum , 1)
    ))),
    SalmonSmokeFishRecipe(0 , ProcessedProduct.SalmonSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Salmon , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    SardineSmokeFishRecipe(0 , ProcessedProduct.SardineSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Sardine , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    ShadSmokeFishRecipe(0 , ProcessedProduct.ShadSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Shad , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    BlueDiscusSmokeFishRecipe(0 , ProcessedProduct.BlueDiscusSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.BlueDiscus , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    MidnightCarpSmokeFishRecipe(0 , ProcessedProduct.MidnightCarpSmokeFish , new ArrayList<>(List.of(
            new Stack(FishType.MidnightCarp , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    SquidSmokeFishRecipe(0 , ProcessedProduct.SquidSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Squid , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    TunaSmokeFishRecipe(0 , ProcessedProduct.TunaSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Tuna , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    PerchSmokeFishRecipe(0 , ProcessedProduct.PerchSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Perch , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    FlounderSmokeFishRecipe(0 , ProcessedProduct.FlounderSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Flounder , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    LionfishSmokeFishRecipe(0 , ProcessedProduct.LionfishSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Lionfish , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    HerringSmokeFishRecipe(0 , ProcessedProduct.HerringSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Herring , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    GhostfishSmokeFishRecipe(0 , ProcessedProduct.GhostfishSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Ghostfish , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    TilapiaSmokeFishRecipe(0 , ProcessedProduct.TilapiaSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Tilapia , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    DoradoSmokeFishRecipe(0 , ProcessedProduct.DoradoSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Dorado , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    SunfishSmokeFishRecipe(0 , ProcessedProduct.SunfishSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Sunfish , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    RainbowTroutSmokeFishRecipe(0 , ProcessedProduct.RainbowTroutSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.RainbowTrout , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    LegendSmokeFishRecipe(0 , ProcessedProduct.LegendSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Legend , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    GlacierfishSmokeFishRecipe(0 , ProcessedProduct.GlacierfishSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Glacierfish , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    AnglerSmokeFishRecipe(0 , ProcessedProduct.AnglerSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Angler , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    CrimsonfishSmokeFishRecipe(0 , ProcessedProduct.CrimsonfishSmokeFish, new ArrayList<>(List.of(
            new Stack(FishType.Crimsonfish , 1),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    CopperMetalBarRecipe(0 , ProcessedProduct.CopperMetalBar, new ArrayList<>(List.of(
            new Stack(MineralType.CopperOre , 5),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    IronMetalBarRecipe(0 , ProcessedProduct.IronMetalBar, new ArrayList<>(List.of(
            new Stack(MineralType.IronOre , 5),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    GoldMetalBarRecipe(0 , ProcessedProduct.GoldMetalBar, new ArrayList<>(List.of(
            new Stack(MineralType.GoldOre , 5),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    IridiumMetalBarRecipe(0 , ProcessedProduct.IridiumMetalBar, new ArrayList<>(List.of(
            new Stack(MineralType.IridiumOre , 5),
            new Stack(ProcessedProduct.Coal , 1)
    ))),
    FishSmoker(),
    DehydratorRecipe(),
    GrassStarterRecipe(),
    FriedEggRecipe(),
    BakedFishRecipe(),
    SaladRecipe(),
    OmeletteRecipe(),
    PumpkinPieRecipe(),
    SpaghettiRecipe(),
    PizzaRecipe(),
    TortillaRecipe(),
    MakiRollRecipe(),
    TripleShotEspressoRecipe(),
    CookieRecipe(),
    HashbrownsRecipe(),
    PancakeRecipe(),
    FruitSaladRecipe(),
    RedPlateRecipe(),
    BreadRecipe(),
    SalmonDinnerRecipe(),
    VegetableMedleyRecipe(),
    FarmerLunchRecipe(),
    SurvivalBurgerRecipe(),
    DishOfTheSeaRecipe(),
    SeaformPuddingRecipe(),
    MinerTreatRecipe();

    private final int price;
    private final Item finalProduct;
    private final ArrayList<Stack> ingredients;

    Recipe(int price, Item finalProduct, ArrayList<Stack> ingredients) {
        this.price = price;
        this.finalProduct = finalProduct;
        this.ingredients = ingredients;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
