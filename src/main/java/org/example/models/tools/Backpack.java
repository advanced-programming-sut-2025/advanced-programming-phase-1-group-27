package org.example.models.tools;

import org.example.models.Item;
import org.example.models.ProcessedProduct;
import org.example.models.Stacks;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

import java.util.*;

public class Backpack extends Tool{

    private int capacity;
    private List<Stacks> items;

    public Backpack(ToolType toolType) {
        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if (level == StackLevel.Basic) {
            this.capacity = 12;
        } else if (level == StackLevel.Large) {
            this.capacity = 24;
        } else if (level == StackLevel.Deluxe) {
            this.capacity = -1;//infinite
        }
        super(level, energyUsage, toolType.getName());
    }

    public List<Stacks> getItems() {
        return items;
    }

    public boolean reduceItems(Item item, int amount) {
        int totalAmount = 0;
        for (Stacks stacks : items) {
            if (stacks.getItem().getName().equals(item.getName())) {
                totalAmount += amount;
            }
        }
        if (totalAmount < amount) {
            return false;
        } else {
            for (Stacks stacks : items) {
                if (stacks.getItem().getName().equals(item.getName())) {
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

    public int addItems(Item item, StackLevel level, int amount) {
        for (Stacks stacks : items) {
            if (hasSameItemType(stacks, item) && stacks.getStackLevel() == level && amount > 0) {
                int quantity = Math.min(amount, 999 - stacks.getQuantity());
                amount -= quantity;
                stacks.addQuantity(quantity);
            }
        }
        while (!isFull() && amount > 0) {
            int quantity = Math.min(amount, 999);
            items.add(new Stacks(item, level, quantity));
            amount -= quantity;
        }
        return amount;
    }

//    public int addItems(Item item, StackLevel stackLevel, int amount) {
//        arranging();
//        if (capacity == -1) {
//            while (amount > 0) {
//                if (amount > 999) {
//                    items.add(new Stacks(item, stackLevel ,999));
//                    amount -= 999;
//                } else {
//                    items.add(new Stacks(item, stackLevel ,amount));
//                }
//            }
//            arranging();
//            return 0;
//        } else {
//            for (Stacks stacks : items) {
//                if (stacks.getItem() == item
//                        && stacks.getQuantity() < 999) {
//                    int canAdd = 999 - stacks.getQuantity();
//                    canAdd = Math.min(amount, canAdd);
//                    stacks.setQuantity(stacks.getQuantity() + canAdd);
//                    amount -= canAdd;
//                    if (amount == 0) {
//                        return 0;
//                    }
//                }
//            }
//            while (amount > 0) {
//                if (amount > 999) {
//                    if (items.size() <= capacity) {
//                        items.add(new Stacks(item, 999));
//                        amount -= 999;
//                    } else {
//                        break;
//                    }
//                } else {
//                    if (items.size() <= capacity) {
//                        items.add(new Stacks(item, amount));
//                        amount -= amount;
//                    } else {
//                        break;
//                    }
//                }
//            }
//            return amount;
//        }
//    }
//
//    private void arranging() {
//        Map<Item, Integer> itemCount = new HashMap<>();
//        for (Stacks stacks : items) {
//            if (stacks.getQuantity() > 0) {
//                itemCount.merge(stacks.getItem(), stacks.getQuantity(), Integer::sum);
//            }
//        }
//        items.clear();
//        for (Map.Entry<Item, Integer> entry : itemCount.entrySet()) {
//            Item item = entry.getKey();
//            int total = entry.getValue();
//            while (total >= 999) {
//                items.add(new Stacks(item, 999));
//                total -= 999;
//            }
//            if (total > 0) {
//                items.add(new Stacks(item, total));
//            }
//        }
//    }

    public boolean isToolAvailable(String toolName) {
        for (Stacks stacks : items) {
            if (stacks.getItem() instanceof ToolType) {
                if (stacks.getItem().getName().equalsIgnoreCase(toolName)) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean isFull() {
        return items.size() == capacity;
    }

    public boolean hasSameItemType(Stacks slot, Item item) {
        Item slotItem = slot.getItem();
        if (slotItem instanceof ProcessedProduct) {
            if (item instanceof ProcessedProduct)
                return slotItem.equals(item);
            return false;
        }
        if (item instanceof ProcessedProduct)
            return false;
        return item == slotItem;
    }
}
