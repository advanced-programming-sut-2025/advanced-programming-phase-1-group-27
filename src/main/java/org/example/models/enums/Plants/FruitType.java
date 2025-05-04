package org.example.models.enums.Plants;

import org.example.models.Item;

public enum FruitType implements Item {
//Trees :
    Apricot(59 , true ,38),
    Cherry(80 , true ,38),
    Banana(150 , true ,75),
    Mango(130 , true ,100),
    Orange(100 , true ,38),
    Peach(140 , true ,38),
    Apple(100 , true ,38),
    Pomegranate(140 , true ,38),
    OakResin(150 , false , 0),
    MapleSyrup(200 , false , 0),
    PineTar(100 , false , 0),
    Sap(2 , true , -2),
    CommonMushroom(40 , true , 38),
    MysticSyrup(1000 , true , 500),
//Foraging Crops :
    Daffodil(30, true, 0),
    Dandelion(40, true, 25),
    Leek(60, true, 40),
    Morel(150, true, 20),
    Salmonberry(5, true, 25),
    SpringOnion(8, true, 13),
    WildHorseradish(50, true, 13),
    FiddleheadFern(90, true, 25),
    Grape(80, true, 38),
    RedMushroom(75, true, -50),
    SpiceBerry(80, true, 25),
    SweetPea(50, true, 0),
    Blackberry(25, true, 25),
    Chanterelle(160, true, 75),
    Hazelnut(40, true, 38),
    PurpleMushroom(90, true, 30),
    WildPlum(80, true, 25),
    Crocus(60, true, 0),
    CrystalFruit(150, true, 63),
    Holly(80, true, -37),
    SnowYam(100, true, 30),
    WinterRoot(70, true, 25),
//All Crops :
    BlueJazz(50, true, 45),
    Carrot(35, true, 75),
    Cauliflower(175, true, 75),
    CoffeeBean(15, false, 0),
    Garlic(60, true, 20),
    GreenBean(40, true, 25),
    Kale(110, true, 50),
    Parsnip(35, true, 25),
    Potato(80, true, 25),
    Rhubarb(220, false, 0),
    Strawberry(120, true, 50),
    Tulip(30, true, 45),
    UnmilledRice(30, true, 3),
    Blueberry(50, true, 25),
    Corn(50, true, 25),
    Hops(25, true, 45),
    HotPepper(40, true, 13),
    Melon(250, true, 113),
    Poppy(140, true, 45),
    Radish(90, true, 45),
    RedCabbage(260, true, 75),
    Starfruit(750, true, 125),
    SummerSpangle(90, true, 45),
    SummerSquash(45, true, 63),
    Sunflower(80, true, 45),
    Tomato(60, true, 20),
    Wheat(25, false, 0),
    Amaranth(150, true, 50),
    Artichoke(160, true, 30),
    Beet(100, true, 30),
    BokChoy(80, true, 25),
    Broccoli(70, true, 63),
    Cranberries(75, true, 38),
    Eggplant(60, true, 20),
    FairyRose(290, true, 45),
    //Grape(80, true, 38),
    Pumpkin(320, false, 0),
    Yam(160, true, 45),
    SweetGemBerry(3000, false, 0),
    PowderMelon(60, true, 63),
    AncientFruit(550, false, 0);

    private final int price;
    private final boolean isFruitEdible;
    private final int energy;


    FruitType(int price, boolean isFruitEdible, int energy) {
        this.price = price;
        this.isFruitEdible = isFruitEdible;
        this.energy = energy;
    }

    public boolean isFruitEdible() {
        return isFruitEdible;
    }

    public int getEnergy(){
        return energy;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return this.toString();
    }
}
