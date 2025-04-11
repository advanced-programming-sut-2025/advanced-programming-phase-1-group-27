package models.tools;

public class Pickaxe extends Tool{
    //EnergyUsage : 5 - 4 - 3 - 2 - 1;
    //If usage is failed -1
    //If mining talent is max -1
    public Pickaxe(int level) {
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
        super(level , energyUsage , "Pickaxe");
    }

    @Override
    public void use() {

    }
}
