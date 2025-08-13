package org.example.common.models.tools;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Item;
import org.example.common.models.ProcessedProduct;
import org.example.common.models.Stacks;
import org.example.common.models.StackLevel;
import org.example.common.models.items.ToolType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Backpack extends Tool {

    private int capacity;
    private ArrayList<Stacks> items = new ArrayList<>();

    public Backpack(ToolType backpackType) {

        super(backpackType.getLevel(), 0, backpackType.getName(), backpackType);

        this.capacity = ToolType.getBackpackCapacity(backpackType);

    }

    public Backpack(ToolType backpackType, ArrayList<Stacks> items) {
        this(backpackType);
        this.items = items;
    }

    public Backpack(LinkedTreeMap<String, Object> info) {
        super(
                ToolType.getItem((String) info.get("type")).getLevel(), // level
                0, // energy usage
                ToolType.getItem((String) info.get("type")).getName(), // name
                ToolType.getItem((String) info.get("type")) // backpackType
        );
        this.capacity = ((Number) info.get("capacity")).intValue();
        ArrayList<LinkedTreeMap<String, Object>> itemsInfo =
                (ArrayList<LinkedTreeMap<String, Object>>) info.get("items");
        for (LinkedTreeMap<String, Object> slotInfo : itemsInfo) {
            items.add(new Stacks(slotInfo));
        }
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("type", this.getToolType().getName());
        info.put("capacity", capacity);
        ArrayList itemsInfo = new ArrayList();
        for (Stacks stack : items) {
            itemsInfo.add(stack.getInfo());
        }
        info.put("items", itemsInfo);
        return info;
    }

    public ArrayList<Stacks> getItems() {
        return items;
    }

    public int getCapacity() {
        return capacity;
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
            if (stacks.getItem().equals(item) && stacks.getStackLevel() == level) {
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
        return overflow == 0;
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

    public int getItemTotalCount(Item item) {
        int cnt = 0;
        for (Stacks slot : items) {
            if (slot.getItem().getName().equalsIgnoreCase(item.getName())) {
                cnt += slot.getQuantity();
            }
        }
        return cnt;
    }

    public boolean isFull() {
        return items.size() == capacity;
    }

    private boolean hasSameItemType(Stacks slot, Item item) {
        Item slotItem = slot.getItem();
        if (slotItem instanceof ProcessedProduct) {
            if (item instanceof ProcessedProduct) {
                return slotItem.equals(item);
            }
            return false;
        }
        if (item instanceof ProcessedProduct)
            return false;
        return item == slotItem;
    }

    private boolean areSameStacks(Stacks s1, Stacks s2) {
        if (s1 == null || s2 == null)
            return false;
        return s1.getItem() == s2.getItem() && s1.getStackLevel() == s2.getStackLevel();
    }

    public void mergeItems() {
        ArrayList<Stacks> copy = new ArrayList<>(items);
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i).getQuantity() == 0)
                copy.set(i, null);
        }
        for (int i = 0; i < copy.size(); i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                if (areSameStacks(copy.get(i), copy.get(j))) {
                    int amount = Math.min(999 - copy.get(i).getQuantity(), copy.get(j).getQuantity());
                    copy.get(i).addQuantity(amount);
                    copy.get(j).addQuantity(-amount);
                    if (copy.get(j).getQuantity() == 0)
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
            stack.setItem(ToolType.CopperHoe);
            stack.setStackLevel(StackLevel.Copper);
        } else if (item == ToolType.CopperHoe) {
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
            stack.setItem(ToolType.CopperAxe);
            stack.setStackLevel(StackLevel.Copper);
        } else if (item == ToolType.CopperAxe) {
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
            stack.setItem(ToolType.CopperPickaxe);
            stack.setStackLevel(StackLevel.Copper);
        } else if (item == ToolType.CopperPickaxe) {
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
            stack.setItem(ToolType.CopperWateringCan);
            stack.setStackLevel(StackLevel.Copper);
        } else if (item == ToolType.CopperWateringCan) {
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
            stack.setItem(ToolType.CopperTrashCan);
            stack.setStackLevel(StackLevel.Copper);
        } else if (item == ToolType.CopperTrashCan) {
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

    public Stacks get(int index) {
        return items.get(index);
    }

    public void switchItem(int index1, int index2){
        Collections.swap(items, index1, index2);
    }


}
