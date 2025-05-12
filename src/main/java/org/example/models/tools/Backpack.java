package org.example.models.tools;

import org.example.models.Item;
import org.example.models.ProcessedProduct;
import org.example.models.Stacks;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

import java.util.*;

public class Backpack extends Tool {

    private int capacity;
    private ArrayList<Stacks> items = new ArrayList<>();

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
        super(level, energyUsage, toolType.getName(), toolType);
    }

    public List<Stacks> getItems() {
        return items;
    }

    public boolean reduceItems(Item item, int amount) {
        int totalAmount = 0;
        for (Stacks stacks : items) {
            if (stacks.getItem().getName().equalsIgnoreCase(item.getName())) {
                totalAmount += amount;
            }
        }
        if (totalAmount < amount) {
            return false;
        } else {
            for (Stacks stacks : items) {
                if (stacks.getItem().getName().equalsIgnoreCase(item.getName())) {
                    if (stacks.getQuantity() < amount) {
                        amount -= stacks.getQuantity();
                        stacks.setQuantity(0);
                    } else {
                        stacks.setQuantity(stacks.getQuantity() - amount);
                        break;
                    }
                }
            }
            mergeItems();
            return true;
        }
    }

    // This function can only be used if we are sure that there is enough of an item.
    public void reduceItems(Item item, StackLevel level, int amount) {
        for (Stacks stacks : items) {
            if (stacks.getItem().getName().equalsIgnoreCase(item.getName()) && stacks.getStackLevel() == level) {
                if (stacks.getQuantity() < amount) {
                    amount -= stacks.getQuantity();
                    stacks.setQuantity(0);
                } else {
                    stacks.setQuantity(stacks.getQuantity() - amount);
                    break;
                }
            }
        }
        mergeItems();
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

    public Stacks getStackToolWithName(String toolName) {
        for (Stacks stacks : items) {
            if (stacks.getItem() instanceof ToolType) {
                if (stacks.getItem().getName().equalsIgnoreCase(toolName)) {
                    return stacks;
                }

            }
        }
        return null;
    }

    public boolean canAdd(Item item, StackLevel level, int amount) {
        int overflow = addItems(item, level, amount);
        reduceItems(item, level, amount - overflow);
        return overflow > 0;
    }

    public boolean hasEnoughItem(Item item, int quantity) {
        int counter = 0;
        for (Stacks slot : items) {
            if (slot.getItem().getName().equalsIgnoreCase(item.getName()))
                counter += slot.getQuantity();
        }
        return counter >= quantity;
    }

    public Stacks getSlotByItemName(String itemName) {
        for (Stacks slot : items) {
            if (slot.getItem().getName().equalsIgnoreCase(itemName))
                return slot;
        }
        return null;
    }

    public Item getItemWithName(String itemName) {
        for (Stacks slot : items) {
            if (slot.getItem().getName().equalsIgnoreCase(itemName)) {
                return slot.getItem();
            }
        }
        return null;
    }

    public StackLevel getStackLevel(Item item) {
        for (Stacks slot : items) {
            if (slot.getItem().getName().equalsIgnoreCase(item.getName())) {
                return slot.getStackLevel();
            }
        }
        return null;
    }

    private boolean isFull() {
        return items.size() == capacity;
    }

    private boolean hasSameItemType(Stacks slot, Item item) {
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

    private boolean areSameStacks(Stacks s1, Stacks s2) {
        return s1.getItem().equals(s2.getItem()) && s1.getStackLevel() == s2.getStackLevel();
    }

    private void mergeItems() {
        ArrayList<Stacks> copy = new ArrayList<>(items);
        for (int i = 0; i < copy.size(); i++) {
            for (int j = 0; j < copy.size(); j++) {
                if (i != j && areSameStacks(copy.get(i), copy.get(j))) {
                    copy.get(i).addQuantity(copy.get(j).getQuantity());
                    copy.set(j, null);
                }
            }
        }
        items.clear();
        for (Stacks stacks : copy) {
            if (stacks != null)
                items.add(stacks);
        }
    }

    public void upgradeLevel(Stacks stack) {
        Item item = stack.getItem();
        if (item == ToolType.BasicHoe) {
            stack.setItem(ToolType.BronzeHoe);
            stack.setStackLevel(StackLevel.Bronze);
        } else if (item == ToolType.BronzeHoe) {
            stack.setItem(ToolType.IronHoe);
            stack.setStackLevel(StackLevel.Iron);
        } else if (item == ToolType.IronHoe) {
            stack.setItem(ToolType.GoldHoe);
            stack.setStackLevel(StackLevel.Gold);
        } else if (item == ToolType.GoldHoe) {
            stack.setItem(ToolType.IridiumHoe);
            stack.setStackLevel(StackLevel.Iridium);
        }
        if (item == ToolType.BasicAxe) {
            stack.setItem(ToolType.BronzeAxe);
            stack.setStackLevel(StackLevel.Bronze);
        } else if (item == ToolType.BronzeAxe) {
            stack.setItem(ToolType.IronAxe);
            stack.setStackLevel(StackLevel.Iron);
        } else if (item == ToolType.IronAxe) {
            stack.setItem(ToolType.GoldAxe);
            stack.setStackLevel(StackLevel.Gold);
        } else if (item == ToolType.GoldAxe) {
            stack.setItem(ToolType.IridiumAxe);
            stack.setStackLevel(StackLevel.Iridium);
        }
        if (item == ToolType.BasicPickaxe) {
            stack.setItem(ToolType.BronzePickaxe);
            stack.setStackLevel(StackLevel.Bronze);
        } else if (item == ToolType.BronzePickaxe) {
            stack.setItem(ToolType.IronPickaxe);
            stack.setStackLevel(StackLevel.Iron);
        } else if (item == ToolType.IronPickaxe) {
            stack.setItem(ToolType.GoldPickaxe);
            stack.setStackLevel(StackLevel.Gold);
        } else if (item == ToolType.GoldPickaxe) {
            stack.setItem(ToolType.IridiumPickaxe);
            stack.setStackLevel(StackLevel.Iridium);
        }
        if (item == ToolType.BasicWateringCan) {
            stack.setItem(ToolType.BronzeWateringCan);
            stack.setStackLevel(StackLevel.Bronze);
        } else if (item == ToolType.BronzeWateringCan) {
            stack.setItem(ToolType.IronWateringCan);
            stack.setStackLevel(StackLevel.Iron);
        } else if (item == ToolType.IronWateringCan) {
            stack.setItem(ToolType.GoldWateringCan);
            stack.setStackLevel(StackLevel.Gold);
        } else if (item == ToolType.GoldWateringCan) {
            stack.setItem(ToolType.IridiumWateringCan);
            stack.setStackLevel(StackLevel.Iridium);
        }
        if (item == ToolType.BasicTrashCan) {
            stack.setItem(ToolType.BronzeTrashCan);
            stack.setStackLevel(StackLevel.Bronze);
        } else if (item == ToolType.BronzeTrashCan) {
            stack.setItem(ToolType.IronTrashCan);
            stack.setStackLevel(StackLevel.Iron);
        } else if (item == ToolType.IronTrashCan) {
            stack.setItem(ToolType.GoldTrashCan);
            stack.setStackLevel(StackLevel.Gold);
        } else if (item == ToolType.GoldTrashCan) {
            stack.setItem(ToolType.IridiumTrashCan);
            stack.setStackLevel(StackLevel.Iridium);
        }
    }
}
