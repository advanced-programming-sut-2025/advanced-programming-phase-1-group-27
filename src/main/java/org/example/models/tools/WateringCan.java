package org.example.models.tools;

import org.example.enums.Item;

public class WateringCan extends Tool implements Item {
    //Water: 40 - 55 - 70 - 85 - 100
    //EnergyUsage : 5 - 4 - 3 - 2 - 1;
    //If farming talent is max -1
    private int waterCapacity;
    public WateringCan(int level) {
        int energyUsage = 0;
        if(level == 0){
            energyUsage = 5;
            this.waterCapacity = 40;
        }else if(level == 1){
            energyUsage = 4;
            this.waterCapacity = 55;
        }else if(level == 2){
            energyUsage = 3;
            this.waterCapacity = 70;
        }else if(level == 3){
            energyUsage = 2;
            this.waterCapacity = 85;
        }else if(level == 4){
            energyUsage = 1;
            this.waterCapacity = 100;
        }
        super(level , energyUsage , "Watering can");
    }

    @Override
    public void use() {

    }
}
