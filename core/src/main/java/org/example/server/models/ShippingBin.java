package org.example.server.models;

import java.util.ArrayList;

public class ShippingBin extends Building {
    private ArrayList<Stacks> items = new ArrayList<>();

    public ShippingBin(Cell topLeftCell) {
        super(topLeftCell, 1, 1);
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
}
