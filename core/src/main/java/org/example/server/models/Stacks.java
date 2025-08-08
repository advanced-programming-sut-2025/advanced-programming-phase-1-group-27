package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.ItemManager;
import org.example.server.models.enums.StackLevel;

import java.util.HashMap;

public class Stacks {
    private Item item;
    private StackLevel stackLevel;
    private int quantity;

    public Stacks(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.stackLevel = StackLevel.Basic;
    }

    public Stacks(Item item, StackLevel stackLevel, int quantity) {
        this.item = item;
        this.stackLevel = stackLevel;
        this.quantity = quantity;
    }

    public Stacks(LinkedTreeMap<String, Object> info) {
        this.item = ItemManager.getItemByName((String) info.get("item"));
        this.stackLevel = StackLevel.getStackLevel((String) info.get("stackLevel"));
        this.quantity = ((Number) info.get("quantity")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("item", item.getName());
        if (stackLevel == null) {
            System.out.println("item : " + item.getName());
        }
        info.put("stackLevel", stackLevel.name());
        info.put("quantity", quantity);
        return info;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public StackLevel getStackLevel() {
        return stackLevel;
    }

    public void setStackLevel(StackLevel stackLevel) {
        this.stackLevel = stackLevel;
    }

    public int getPrice() {
        return (int) ((double) item.getPrice() * this.stackLevel.getPriceModifier());
    }

    public int getTotalPrice() {
        return quantity * (int) ((double) item.getPrice() * this.stackLevel.getPriceModifier());
    }

    public static boolean compare(Stacks s1, Stacks s2) {
        return s1.item == s2.item && s1.stackLevel == s2.stackLevel && s1.quantity == s2.quantity;
    }

    public static Stacks copy(Stacks stack) {
        return new Stacks(stack.item, stack.stackLevel, stack.quantity);
    }
}
