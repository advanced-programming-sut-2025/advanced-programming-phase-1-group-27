package org.example.models.tools;


import org.example.enums.Item;

public class Axe extends Tool implements Item {
    //If usage is failed -1
    //If foraging talent is max -1
    public Axe(int level) {
        int energyUsage = 0;
        if(level == 0){
            energyUsage = 5;
        }else if(level == 1){
            energyUsage = 4;
        }else if(level == 2){
            energyUsage = 3;
        }else if(level == 3){
            energyUsage = 2;
        }else if(level == 4){
            energyUsage = 1;
        }
        super(level , energyUsage , "Axe");
    }

    @Override
    public void use() {

    }

}
