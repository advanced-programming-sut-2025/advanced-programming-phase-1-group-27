package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Backpack;

import java.util.ArrayList;
import java.util.HashMap;

public class ShippingBin extends Building {
    private ArrayList<Stacks> items = new ArrayList<>();

    public ShippingBin(Cell topLeftCell) {
        super(topLeftCell, 1, 1);
    }

    public ShippingBin(Cell topLeftCell, LinkedTreeMap<String, Object> info) {
        super(topLeftCell, 1, 1);
        this.items = new Backpack((LinkedTreeMap<String, Object>) info.get("itemsInfo")).getItems();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("itemsInfo", new Backpack(ToolType.BasicBackpack, items).getInfo());
        return info;
    }

    public void addItem(Stacks item) {
        items.add(item);
    }

    public int refresh() {
        int totalMoney = 0;
        for (Stacks stack : items) {
            System.out.println(stack.getQuantity());
            totalMoney += stack.getTotalPrice();
        }
        items.clear();
        return totalMoney;
    }

    public ArrayList<Stacks> getItems() {
        return items;
    }
}
