package org.example.models.tools;

import org.example.models.Ingredient;
import org.example.models.Item;
import org.example.models.Stacks;

import java.util.*;

public class Backpack extends Tool implements Item {

    private int capacity;
    private List<Stacks> items;

    public Backpack(int level) {
        int energyUsage = 0;
        if (level == 0) {
            this.capacity = 12;
        } else if (level == 1) {
            this.capacity = 24;
        } else if (level == 2) {
            this.capacity = -1;//infinite
        }
        super(level, energyUsage, "Backpack");
    }

    @Override
    public void use() {

    }

    @Override
    public Integer getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    public List<Stacks> getItems() {
        return items;
    }

    public boolean reduceItems(Item item, int amount) {
        int totalAmount = 0;
        for (Stacks stacks : items) {
            if (stacks.getItem() == item) {
                totalAmount += amount;
            }
        }
        if (totalAmount < amount) {
            return false;
        } else {
            for (Stacks stacks : items) {
                if (stacks.getItem() == item) {
                    if (stacks.getQuantity() <= amount) {
                        amount -= stacks.getQuantity();
                        stacks.setQuantity(0);
                    } else {
                        stacks.setQuantity(stacks.getQuantity() - amount);
                    }
                }
            }
            return true;
        }
    }

    public int addItems(Item item, int amount) {
        arranging();
        if (capacity == -1) {
            while (amount > 0) {
                if (amount > 999) {
                    items.add(new Stacks(item, 999));
                    amount -= 999;
                } else {
                    items.add(new Stacks(item, amount));
                }
            }
            arranging();
            return 0;
        } else {
            for (Stacks stacks : items) {
                if (stacks.getItem() == item
                        && stacks.getQuantity() < 999) {
                    int canAdd = 999 - stacks.getQuantity();
                    canAdd = Math.min(amount, canAdd);
                    stacks.setQuantity(stacks.getQuantity() + canAdd);
                    amount -= canAdd;
                    if (amount == 0) {
                        return 0;
                    }
                }
            }
            while (amount > 0) {
                if (amount > 999) {
                    if (items.size() <= capacity) {
                        items.add(new Stacks(item, 999));
                        amount -= 999;
                    } else {
                        break;
                    }
                }else {
                    if (items.size() <= capacity) {
                        items.add(new Stacks(item, amount));
                        amount -= amount;
                    }else {
                        break;
                    }
                }
            }
            return amount;
        }
    }

    private void arranging() {
        Map<Item, Integer> itemCount = new HashMap<>();
        for (Stacks stacks : items) {
            if (stacks.getQuantity() > 0) {
                itemCount.merge(stacks.getItem(), stacks.getQuantity(), Integer::sum);
            }
        }
        items.clear();
        for (Map.Entry<Item, Integer> entry : itemCount.entrySet()) {
            Item item = entry.getKey();
            int total = entry.getValue();
            while (total >= 999) {
                items.add(new Stacks(item, 999));
                total -= 999;
            }
            if (total > 0) {
                items.add(new Stacks(item, total));
            }
        }
    }

}
