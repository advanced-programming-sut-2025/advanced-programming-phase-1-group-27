package models.tools;

import enums.Item;

public class TrashCan extends Tool implements Item {
    private int percentage;

    public TrashCan(int level) {
        int energyUsage = 0;
        if(level == 0){
            this.percentage = 0;
        }else if(level == 1){
            this.percentage = 15;
        }else if(level == 2){
            this.percentage = 30;
        }else if(level == 3){
            this.percentage = 45;
        }else if(level == 4){
            this.percentage = 60;
        }
        super(level , energyUsage , "Trash can");
    }

    @Override
    public void use() {

    }
}
