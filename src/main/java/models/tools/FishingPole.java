package models.tools;

public class FishingPole extends Tool{
    private int price;
    //Required fishing talent : 0 , 0 , 2 , 4
    //If fishing talent is max -1
    public FishingPole(int level) {
        int energyUsage = 0;
        if(level == 0){
            energyUsage = 8;
            this.price = 25;
        }else if(level == 1){
            energyUsage = 8;
            this.price = 500;
        }else if(level == 2){
            energyUsage = 6;
            this.price = 1800;
        }else if(level == 3){
            energyUsage = 4;
            this.price = 7500;
        }
        super(level , energyUsage , "FishingPole");
    }

    @Override
    public void use() {

    }
}
