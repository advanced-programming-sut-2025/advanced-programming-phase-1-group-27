package org.example.models.enums.items;

import org.example.models.Item;

public enum FruitType implements Item {
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
    MysticSyrup(1000 , true , 500);

    private final int price;
    private final boolean isFruitEdible;
    private final int energy;

    FruitType(int price, boolean isFruitEdible, int energy) {
        this.price = price;
        this.isFruitEdible = isFruitEdible;
        this.energy = energy;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public boolean isFruitEdible() {
        return isFruitEdible;
    }

    public int getEnergy(){
        return energy;
    }
}
