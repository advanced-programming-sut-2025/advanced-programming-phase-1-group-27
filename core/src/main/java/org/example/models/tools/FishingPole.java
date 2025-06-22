package org.example.models.tools;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.enums.AbilityType;
import org.example.models.enums.CellType;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class FishingPole extends Tool{

    private int price;
    //Required fishing talent : 0 , 0 , 2 , 4
    //If fishing talent is max -1
    public FishingPole(ToolType toolType) {

        super( toolType.getLevel() , getEnergyUsageByType(toolType) , toolType.getName(), toolType);
        setPriceByType(toolType);
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int getEnergyUsage() {
        int energy = super.getEnergyUsage();
        if (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() == 4) {
            energy--;
        }
        return Math.min(energy , 0);
    }

    public boolean enoughAbility(){
        if(this.getLevel() == StackLevel.Basic){
            return App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 0;
        }else if(this.getLevel() == StackLevel.Large) {
            return App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 0;
        }else if(this.getLevel() == StackLevel.Fiberglass){
            return App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 2;
        }else if(this.getLevel() == StackLevel.Iridium){
            return App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 4;
        }
        return false;
    }

    private static int getEnergyUsageByType(ToolType toolType){

        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if(level == StackLevel.Training){
            energyUsage = 8;
        }else if(level == StackLevel.Bamboo){
            energyUsage = 8;
        }else if(level == StackLevel.Fiberglass){
            energyUsage = 6;
        }else if(level == StackLevel.Iridium){
            energyUsage = 4;
        }

        return energyUsage;

    }

    private void setPriceByType(ToolType toolType){


        StackLevel level = toolType.getLevel();

        if(level == StackLevel.Training){
            this.price = 25;
        }else if(level == StackLevel.Bamboo){
            this.price = 500;
        }else if(level == StackLevel.Fiberglass){
            this.price = 1800;
        }else if(level == StackLevel.Iridium){
            this.price = 7500;
        }


    }



}
