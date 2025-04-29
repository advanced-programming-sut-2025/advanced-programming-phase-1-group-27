package org.example.models;

import org.example.models.enums.AbilityType;
import org.example.models.enums.Gender;
import org.example.models.enums.items.Recipe;
import org.example.models.tools.Backpack;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends User {
    private ArrayList<Recipe> availableRecipes; // TODO: this should be filled when abilities are upgraded or recipes are purchased from a shop
    // player's inventory
    private Backpack backpack;
    // items which are place in the fridge
    private ArrayList<Stack> refrigerator = new ArrayList<>();
    // maps ability type to user's ability
    private HashMap<AbilityType, Ability> abilityFinder = new HashMap<>(){{
        put(AbilityType.Farming, farming);
        put(AbilityType.Fishing, fishing);
        put(AbilityType.Foraging, foraging);
        put(AbilityType.Mining, mining);
    }};
    private int energy, maxEnergy = 200;
    private Ability farming, mining, foraging, fishing;
    private Cell currentCell;
    private Map currentMap;
    private int money;
    private Tool currentTool;

    public Player(String username, String password, String nickname, String email, Gender gender) {
        super(username, password, nickname, email, gender);
    }

    public int getMoney() {
        return money;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public void walk(Place destination) {

    }

    public void consumeEnergy(int val) {
        energy -= val;
        if (energy < 0) {
            this.passOut();
        }
    }

    public int getEnrgy() {
        return this.energy;
    }

    public void goToSleep() {
        //set the energy for tommorow and...
    }

    public void passOut() {
        // set the 75% energy for tommorow and...
    }

    public void farmXp(int xp) {
        // add xp for farmingAbility
    }

    public void mineXp(int xp) {
        // ..
    }

    public void forageXp(int xp) {
        // ..
    }

    public void fishXp(int xp) {
        // ..
    }

    public String showItems() {

    }

    public void trashItem(String itemName, int n) {

    }

    public void trashItem(String itemName) {

    }
}
