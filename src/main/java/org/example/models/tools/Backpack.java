package org.example.models.tools;

import org.example.enums.items.Item;

import java.util.ArrayList;

public class Backpack extends Tool implements Item {

    private int capacity;
    private ArrayList<Item> items;

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

    public Tool getTool(String toolName) {
        Tool tool = getTool(toolName);
        for(Item item : items){
            if(tool == item){
                return tool;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
