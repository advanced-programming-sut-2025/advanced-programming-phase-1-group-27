package models.tools;

import enums.Item;

public class Backpack extends Tool implements Item {

    private int capacity;

    public Backpack(int level) {
        int energyUsage = 0;
        if(level == 0){
            this.capacity = 12;
        }else if(level == 1){
            this.capacity = 24;
        }else if(level == 2){
            this.capacity = -1;//infinite
        }
        super(level , energyUsage , "Backpack");
    }

    @Override
    public void use() {

    }
}
