package org.example.models.enums.items;

import org.example.models.Ingredient;
import org.example.models.Item;
import org.example.models.enums.items.products.AnimalProduct;
import org.example.models.enums.items.products.CraftingProduct;
import org.example.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;
import java.util.List;

public enum Recipe implements Item {
    HoneyRecipe(0, ProcessedProductType.Honey, new ArrayList<>(List.of())),
    CheeseRecipe(0, ProcessedProductType.Cheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.CowMilk, 1)
    ))),
    LargeCheeseRecipe(0, ProcessedProductType.LargeCheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.LargeCowMilk, 1)
    ))),
    GoatCheeseRecipe(0, ProcessedProductType.GoatCheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.GoatMilk, 1)
    ))),
    LargeGoatCheeseRecipe(0, ProcessedProductType.LargeGoatCheese, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.LargeGoatMilk, 1)
    ))),
    BeerRecipe(0, ProcessedProductType.Beer, new ArrayList<>(List.of(
            new Ingredient(CropType.Wheat, 1)
    ))),
    VinegarRecipe(0, ProcessedProductType.Vinegar, new ArrayList<>(List.of(
            new Ingredient(ProcessedProductType.Rice, 1)
    ))),
    CoffeeRecipe(0, ProcessedProductType.Coffee, new ArrayList<>(List.of(
            new Ingredient(CropType.CoffeeBean, 5)
    ))),
    JuiceRecipe(null, ProcessedProductType.Juice, new ArrayList<>(List.of(
          new Ingredient(new ArrayList<>(List.of(FruitType.Apple, )), 1)
    ))),
    AmaranthJuiceRecipe(0, ProcessedProductType.AmaranthJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Amaranth, 1)
    ))),
    ArtichokeJuiceRecipe(0, ProcessedProductType.ArtichokeJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Artichoke, 1)
    ))),
    BeetJuiceRecipe(0, ProcessedProductType.BeetJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Beet, 1)
    ))),
    BokChoyJuiceRecipe(0, ProcessedProductType.BokChoyJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.BokChoy, 1)
    ))),
    BroccoliJuiceRecipe(0, ProcessedProductType.BroccoliJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Broccoli, 1)
    ))),
    CarrotJuiceRecipe(0, ProcessedProductType.CarrotJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Carrot, 1)
    ))),
    CauliflowerJuiceRecipe(0, ProcessedProductType.CauliflowerJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Cauliflower, 1)
    ))),
    CornJuiceRecipe(0, ProcessedProductType.CornJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Corn, 1)
    ))),
    EggplantJuiceRecipe(0, ProcessedProductType.EggplantJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Eggplant, 1)
    ))),
    FiddleheadFernJuiceRecipe(0, ProcessedProductType.FiddleheadFernJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.FiddleheadFern, 1)
    ))),
    GarlicJuiceRecipe(0, ProcessedProductType.GarlicJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Garlic, 1)
    ))),
    GreenBeanJuiceRecipe(0, ProcessedProductType.GreenBeanJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.GreenBean, 1)
    ))),
    HopsJuiceRecipe(0, ProcessedProductType.HopsJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Hops, 1)
    ))),
    KaleJuiceRecipe(0, ProcessedProductType.KaleJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Kale, 1)
    ))),
    ParsnipJuiceRecipe(0, ProcessedProductType.ParsnipJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Parsnip, 1)
    ))),
    PotatoJuiceRecipe(0, ProcessedProductType.PotatoJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Potato, 1)
    ))),
    PumpkinJuiceRecipe(0, ProcessedProductType.PumpkinJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Pumpkin, 1)
    ))),
    RadishJuiceRecipe(0, ProcessedProductType.RadishJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Radish, 1)
    ))),
    RedCabbageJuiceRecipe(0, ProcessedProductType.RedCabbageJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.RedCabbage, 1)
    ))),
    SummerSquashJuiceRecipe(0, ProcessedProductType.SummerSquashJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.SummerSquash, 1)
    ))),
    TomatoJuiceRecipe(0, ProcessedProductType.TomatoJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Tomato, 1)
    ))),
    UnmilledRiceJuiceRecipe(0, ProcessedProductType.UnmilledRiceJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.UnmilledRice, 1)
    ))),
    WheatJuiceRecipe(0, ProcessedProductType.WheatJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Wheat, 1)
    ))),
    YamJuiceRecipe(0, ProcessedProductType.YamJuice, new ArrayList<>(List.of(
            new Ingredient(CropType.Yam, 1)
    ))),
    MeadRecipe(0, ProcessedProductType.Mead, new ArrayList<>(List.of(
            new Ingredient(ProcessedProductType.Honey, 1)
    ))),
    PaleAleRecipe(0, ProcessedProductType.PaleAle, new ArrayList<>(List.of(
            new Ingredient(CropType.Hops, 1)
    ))),
    AncientFruitWineRecipe(0, ProcessedProductType.AncientFruitWine, new ArrayList<>(List.of(
            new Ingredient(CropType.AncientFruit, 1)
    ))),
    AppleWineRecipe(0, ProcessedProductType.AppleWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Apple, 1)
    ))),
    ApricotWineRecipe(0, ProcessedProductType.ApricotWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Apricot, 1)
    ))),
    BananaWineRecipe(0, ProcessedProductType.BananaWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Banana, 1)
    ))),
    BlackberryWineRecipe(0, ProcessedProductType.BlackberryWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Blackberry, 1)
    ))),
    BlueberryWineRecipe(0, ProcessedProductType.BlueberryWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Blueberry, 1)
    ))),
    CherryWineRecipe(0, ProcessedProductType.CherryWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Cherry, 1)
    ))),
    CranberriesWineRecipe(0, ProcessedProductType.CranberriesWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Cranberry, 1)
    ))),
    CrystalFruitWineRecipe(0, ProcessedProductType.CrystalFruitWine, new ArrayList<>(List.of(
            new Ingredient(CropType.CrystalFruit, 1)
    ))),
    GrapeWineRecipe(0, ProcessedProductType.GrapeWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Grape, 1)
    ))),
    HotPepperWineRecipe(0, ProcessedProductType.HotPepperWine, new ArrayList<>(List.of(
            new Ingredient(CropType.HotPepper, 1)
    ))),
    MangoWineRecipe(0, ProcessedProductType.MangoWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Mango, 1)
    ))),
    MelonWineRecipe(0, ProcessedProductType.MelonWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Melon, 1)
    ))),
    OrangeWineRecipe(0, ProcessedProductType.OrangeWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Orange, 1)
    ))),
    PeachWineRecipe(0, ProcessedProductType.PeachWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Peach, 1)
    ))),
    PomegranateWineRecipe(0, ProcessedProductType.PomegranateWine, new ArrayList<>(List.of(
            new Ingredient(FruitType.Pomegranate, 1)
    ))),
    PowderMelonWineRecipe(0, ProcessedProductType.PowderMelonWine, new ArrayList<>(List.of(
            new Ingredient(CropType.PowderMelon, 1)
    ))),
    RhubarbWineRecipe(0, ProcessedProductType.RhubarbWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Rhubarb, 1)
    ))),
    SalmonBerryWineRecipe(0, ProcessedProductType.SalmonBerryWine, new ArrayList<>(List.of(
            new Ingredient(CropType.SalmonBerry, 1)
    ))),
    SpiceBerryWineRecipe(0, ProcessedProductType.SpiceBerryWine, new ArrayList<>(List.of(
            new Ingredient(CropType.SpiceBerry, 1)
    ))),
    StarfruitWineRecipe(0, ProcessedProductType.StarfruitWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Starfruit, 1)
    ))),
    StrawberryWineRecipe(0, ProcessedProductType.StrawberryWine, new ArrayList<>(List.of(
            new Ingredient(CropType.Strawberry, 1)
    ))),
    WildPlumWineRecipe(0, ProcessedProductType.WildPlumWine, new ArrayList<>(List.of(
            new Ingredient(CropType.WildPlum, 1)
    ))),
    ChanterelleDriedMushroomsRecipe(0, ProcessedProductType.ChanterelleDriedMushrooms, new ArrayList<>(List.of(
            new Ingredient(CropType.Chanterelle, 5)
    ))),
    CommonMushroomDriedMushroomsRecipe(0, ProcessedProductType.CommonMushroomDriedMushrooms, new ArrayList<>(List.of(
            new Ingredient(FruitType.CommonMushroom, 5)
    ))),
    MorelDriedMushroomsRecipe(0, ProcessedProductType.MorelDriedMushrooms, new ArrayList<>(List.of(
            new Ingredient(CropType.Morel, 5)
    ))),
    PurpleMushroomDriedMushroomsRecipe(0, ProcessedProductType.PurpleMushroomDriedMushrooms, new ArrayList<>(List.of(
            new Ingredient(CropType.PurpleMushroom, 5)
    ))),
    RedMushroomDriedMushroomsRecipe(0 , ProcessedProductType.RedMushroomDriedMushrooms, new ArrayList<>(List.of(
            new Ingredient(CropType.RedMushroom, 5)
    ))),
    AncientFruitDriedFruitRecipe(0 , ProcessedProductType.AncientFruitDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.AncientFruit, 5)
    ))),
    AppleDriedFruitRecipe(0 , ProcessedProductType.AppleDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Apple, 5)
    )) ),
    ApricotDriedFruitRecipe(0 , ProcessedProductType.ApricotDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Apricot, 5)
    )) ),
    BananaDriedFruitRecipe(0 , ProcessedProductType.BananaDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Banana, 5)
    )) ),
    BlackberryDriedFruitRecipe(0 , ProcessedProductType.BlackberryDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.Blackberry, 5)
    )) ),
    BlueberryDriedFruitRecipe(0 , ProcessedProductType.BlueberryDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.Blueberry, 5)
    )) ),
    CherryDriedFruitRecipe(0 , ProcessedProductType.CherryDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Cherry, 5)
    )) ),
    CranberriesDriedFruitRecipe(0 , ProcessedProductType.CranberriesDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.Cranberry , 5)
    )) ),
    CrystalFruitDriedFruitRecipe(0 , ProcessedProductType.CrystalFruitDriedFruit , new ArrayList<>(List.of(
            new Ingredient(CropType.CrystalFruit , 5)
    )) ),
    HotPepperDriedFruitRecipe(0 , ProcessedProductType.HotPepperDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.HotPepper, 5)
    )) ),
    MangoDriedFruitRecipe(0 , ProcessedProductType.MangoDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Mango, 5)
    )) ),
    MelonDriedFruitRecipe(0 , ProcessedProductType.MelonDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.Melon, 5)
    )) ),
    OrangeDriedFruitRecipe(0 , ProcessedProductType.OrangeDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Orange, 5)
    )) ),
    PeachDriedFruitRecipe(0 , ProcessedProductType.PeachDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Peach, 5)
    )) ),
    PomegranateDriedFruitRecipe(0 , ProcessedProductType.PomegranateDriedFruit, new ArrayList<>(List.of(
            new Ingredient(FruitType.Pomegranate, 5)
    )) ),
    PowderMelonDriedFruitRecipe(0 , ProcessedProductType.PowderMelonDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.PowderMelon , 5)
    )) ),
    RhubarbDriedFruitRecipe(0 , ProcessedProductType.RhubarbDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.Rhubarb, 5)
    )) ),
    SalmonBerryDriedFruitRecipe(0 , ProcessedProductType.SalmonBerryDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.SalmonBerry, 5)
    )) ),
    SpiceBerryDriedFruitRecipe(0 , ProcessedProductType.SpiceBerryDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.SpiceBerry, 5)
    )) ),
    StarfruitDriedFruitRecipe(0 , ProcessedProductType.StarfruitDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.Starfruit, 5)
    )) ),
    StrawberryDriedFruitRecipe(0 , ProcessedProductType.StrawberryDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.Strawberry, 5)
    )) ),
    WildPlumDriedFruitRecipe(0 , ProcessedProductType.WildPlumDriedFruit, new ArrayList<>(List.of(
            new Ingredient(CropType.WildPlum, 5)
    )) ),
    RaisinsRecipe(0 , ProcessedProductType.Raisins, new ArrayList<>(List.of(
            new Ingredient(CropType.Grape , 5)
    ))),
    CoalRecipe(0 , ProcessedProductType.Coal, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood , 10)
    ))),
    ClothRecipe(0 , ProcessedProductType.Cloth, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.Wool , 1)
    ))),
    MayonnaiseRecipe(0 , ProcessedProductType.Mayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.Egg , 1)
    ))),
    LargeMayonnaiseRecipe(0 , ProcessedProductType.LargeMayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.LargeEgg , 1)
    ))),
    DuckMayonnaiseRecipe(0 , ProcessedProductType.DuckMayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.DuckEgg , 1)
    ))),
    DinosaurMayonnaiseRecipe(0 , ProcessedProductType.DinosaurMayonnaise, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.DinosaurEgg , 1)
    ))),
    TruffleOilRecipe(0 , ProcessedProductType.TruffleOil, new ArrayList<>(List.of(
            new Ingredient(AnimalProduct.Truffle , 1)
    ))),
    CornOilRecipe(0 , ProcessedProductType.CornOil, new ArrayList<>(List.of(
            new Ingredient(CropType.Corn , 1)
    ))),
    SunflowerSeedOilRecipe(0 , ProcessedProductType.SunflowerSeedOil, new ArrayList<>(List.of(
            new Ingredient(SeedType.SunflowerSeed , 1)
    ))),
    SunflowerOilRecipe(0 , ProcessedProductType.SunflowerOil, new ArrayList<>(List.of(
            new Ingredient(CropType.Sunflower , 1)
    ))),
    AmaranthPicklesRecipe(0 , ProcessedProductType.AmaranthPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Amaranth , 1)
    ))),
    ArtichokePicklesRecipe(0 , ProcessedProductType.ArtichokePickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Artichoke , 1)
    ))),
    BeetPicklesRecipe(0 , ProcessedProductType.BeetPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Beet , 1)
    ))),
    BokChoyPicklesRecipe(0, ProcessedProductType.BokChoyPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.BokChoy , 1)
    ))),
    BroccoliPicklesRecipe(0 , ProcessedProductType.BroccoliPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Broccoli , 1)
    ))),
    CarrotPicklesRecipe(0 , ProcessedProductType.CarrotPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Carrot , 1)
    ))),
    CauliflowerPicklesRecipe(0 , ProcessedProductType.CauliflowerPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Cauliflower , 1)
    ))),
    CornPicklesRecipe(0 , ProcessedProductType.CornPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Corn , 1)
    ))),
    EggplantPicklesRecipe(0 , ProcessedProductType.EggplantPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Eggplant, 1)
    ))),
    FiddleheadFernPicklesRecipe(0 , ProcessedProductType.FiddleheadFernPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Eggplant , 1)
    ))),
    GarlicPicklesRecipe(0 , ProcessedProductType.GarlicPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Garlic, 1)
    ))),
    GreenBeanPicklesRecipe(0 , ProcessedProductType.GreenBeanPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.GreenBean , 1)
    ))),
    HopsPicklesRecipe(0 , ProcessedProductType.HopsPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Hops , 1)
    ))),
    KalePicklesRecipe(0 , ProcessedProductType.KalePickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Kale , 1)
    ))),
    ParsnipPicklesRecipe(0 , ProcessedProductType.ParsnipPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Parsnip , 1)
    ))),
    PotatoPicklesRecipe(0 , ProcessedProductType.PotatoPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Potato , 1)
    ))),
    PumpkinPicklesRecipe(0 , ProcessedProductType.PumpkinPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Pumpkin , 1)
    ))),
    RadishPicklesRecipe(0 , ProcessedProductType.RadishPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Radish , 1)
    ))),
    RedCabbagePicklesRecipe(0 , ProcessedProductType.RedCabbagePickles, new ArrayList<>(List.of(
            new Ingredient(CropType.RedCabbage , 1)
    ))),
    SummerSquashPicklesRecipe(0 , ProcessedProductType.SummerSquashPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.SummerSquash , 1)
    ))),
    TomatoPicklesRecipe(0 , ProcessedProductType.TomatoPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Tomato , 1)
    ))),
    UnmilledRicePicklesRecipe(0 , ProcessedProductType.UnmilledRicePickles, new ArrayList<>(List.of(
            new Ingredient(CropType.UnmilledRice , 1)
    ))),
    WheatPicklesRecipe(0 , ProcessedProductType.WheatPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Wheat , 1)
    ))),
    YamPicklesRecipe(0 , ProcessedProductType.YamPickles, new ArrayList<>(List.of(
            new Ingredient(CropType.Yam , 1)
    ))),
    AncientFruitJellyRecipe(0 , ProcessedProductType.AncientFruitJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.AncientFruit , 1)
    ))),
    AppleJellyRecipe(0 , ProcessedProductType.AppleJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Apple , 1)
    ))),
    ApricotJellyRecipe(0 , ProcessedProductType.ApricotJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Apricot , 1)
    ))),
    BananaJellyRecipe(0 , ProcessedProductType.BananaJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Banana , 1)
    ))),
    BlackberryJellyRecipe(0 , ProcessedProductType.BlackberryJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Blackberry , 1)
    ))),
    BlueberryJellyRecipe(0 , ProcessedProductType.BlueberryJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Blueberry , 1)
    ))),
    CherryJellyRecipe(0 , ProcessedProductType.CherryJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Cherry , 1)
    ))),
    CranberriesJellyRecipe(0 , ProcessedProductType.CranberriesJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Cranberry , 1)
    ))),
    CrystalFruitJellyRecipe(0 , ProcessedProductType.CrystalFruitJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.CrystalFruit , 1)
    ))),
    GrapeJellyRecipe(0 , ProcessedProductType.GrapeJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Grape , 1)
    ))),
    HotPepperJellyRecipe(0 , ProcessedProductType.HotPepperJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.HotPepper , 1)
    ))),
    MangoJellyRecipe(0 , ProcessedProductType.MangoJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Mango , 1)
    ))),
    MelonJellyRecipe(0 , ProcessedProductType.MelonJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Melon , 1)
    ))),
    OrangeJellyRecipe(0 , ProcessedProductType.OrangeJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Orange , 1)
    ))),
    PeachJellyRecipe(0 , ProcessedProductType.PeachJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Peach , 1)
    ))),
    PomegranateJellyRecipe(0 , ProcessedProductType.PomegranateJelly, new ArrayList<>(List.of(
            new Ingredient(FruitType.Pomegranate , 1)
    ))),
    PowdermelonJellyRecipe(0 , ProcessedProductType.PowderMelonJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.PowderMelon , 1)
    ))),
    RhubarbJellyRecipe(0 , ProcessedProductType.RhubarbJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Rhubarb , 1)
    ))),
    SalmonberryJellyRecipe(0 , ProcessedProductType.SalmonBerryJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.SalmonBerry, 1)
    ))),
    SpiceBerryJellyRecipe(0 , ProcessedProductType.SpiceBerryJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.SpiceBerry , 1)
    ))),
    StarfruitJellyRecipe(0 , ProcessedProductType.StarfruitJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Starfruit , 1)
    ))),
    StrawberryJellyRecipe(0 , ProcessedProductType.StrawberryJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.Strawberry , 1)
    ))),
    WildPlumJellyRecipe(0 , ProcessedProductType.WildPlumJelly, new ArrayList<>(List.of(
            new Ingredient(CropType.WildPlum , 1)
    ))),
    SalmonSmokeFishRecipe(0 , ProcessedProductType.SalmonSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Salmon , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    SardineSmokeFishRecipe(0 , ProcessedProductType.SardineSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Sardine , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    ShadSmokeFishRecipe(0 , ProcessedProductType.ShadSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Shad , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    BlueDiscusSmokeFishRecipe(0 , ProcessedProductType.BlueDiscusSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.BlueDiscus , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    MidnightCarpSmokeFishRecipe(0 , ProcessedProductType.MidnightCarpSmokeFish , new ArrayList<>(List.of(
            new Ingredient(FishType.MidnightCarp , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    SquidSmokeFishRecipe(0 , ProcessedProductType.SquidSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Squid , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    TunaSmokeFishRecipe(0 , ProcessedProductType.TunaSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Tuna , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    PerchSmokeFishRecipe(0 , ProcessedProductType.PerchSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Perch , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    FlounderSmokeFishRecipe(0 , ProcessedProductType.FlounderSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Flounder , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    LionfishSmokeFishRecipe(0 , ProcessedProductType.LionfishSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Lionfish , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    HerringSmokeFishRecipe(0 , ProcessedProductType.HerringSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Herring , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    GhostfishSmokeFishRecipe(0 , ProcessedProductType.GhostfishSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Ghostfish , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    TilapiaSmokeFishRecipe(0 , ProcessedProductType.TilapiaSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Tilapia , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    DoradoSmokeFishRecipe(0 , ProcessedProductType.DoradoSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Dorado , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    SunfishSmokeFishRecipe(0 , ProcessedProductType.SunfishSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Sunfish , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    RainbowTroutSmokeFishRecipe(0 , ProcessedProductType.RainbowTroutSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.RainbowTrout , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    LegendSmokeFishRecipe(0 , ProcessedProductType.LegendSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Legend , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    GlacierfishSmokeFishRecipe(0 , ProcessedProductType.GlacierfishSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Glacierfish , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    AnglerSmokeFishRecipe(0 , ProcessedProductType.AnglerSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Angler , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    CrimsonfishSmokeFishRecipe(0 , ProcessedProductType.CrimsonfishSmokeFish, new ArrayList<>(List.of(
            new Ingredient(FishType.Crimsonfish , 1),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    CopperMetalBarRecipe(0 , ProcessedProductType.CopperMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.CopperOre , 5),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    IronMetalBarRecipe(0 , ProcessedProductType.IronMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.IronOre , 5),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    GoldMetalBarRecipe(0 , ProcessedProductType.GoldMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.GoldOre , 5),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    IridiumMetalBarRecipe(0 , ProcessedProductType.IridiumMetalBar, new ArrayList<>(List.of(
            new Ingredient(MineralType.IridiumOre , 5),
            new Ingredient(ProcessedProductType.Coal , 1)
    ))),
    FishSmoker(10000, CraftingProduct.FishSmoker, new ArrayList<>(List.of(
            new Ingredient(MineralType.Wood, 50),
            new Ingredient(ProcessedProductType.IronMetalBar, 3),
            new Ingredient(ProcessedProductType.Coal , 10)
    ))),
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
    TroutSoupRecipe(),
    MinerTreatRecipe(),
    RiceRecipe(0, ProcessedProductType.Rice, new ArrayList<>(List.of(
            new Ingredient(CropType.UnmilledRice, 1)
    )));

    private final int price;
    private final Item finalProduct;
    private final ArrayList<Ingredient> ingredients;

    Recipe(int price, Item finalProduct, ArrayList<Ingredient> ingredients) {
        this.price = price;
        this.finalProduct = finalProduct;
        this.ingredients = ingredients;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
