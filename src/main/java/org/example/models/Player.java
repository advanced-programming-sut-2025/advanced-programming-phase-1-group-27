package org.example.models;

import org.example.models.NPCs.NPC;
import org.example.models.enums.AbilityType;
import org.example.models.enums.Gender;
import org.example.models.enums.Weathers.Weather;
import org.example.models.enums.items.Recipe;
import org.example.models.Map.FarmMap;
import org.example.models.enums.items.ToolType;
import org.example.models.tools.Backpack;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends User {
    private ArrayList<Recipe> availableRecipes = new ArrayList<>(); // TODO: this should be filled when abilities are upgraded or recipes are purchased from a shop
    // player's inventory
    private Backpack backpack = new Backpack(ToolType.BasicBackpack); // TODO: ba parsa check shavad
    // items which are place in the fridge
    private ArrayList<Stacks> refrigerator = new ArrayList<>();
    // maps ability type to user's ability
    private HashMap<AbilityType, Ability> abilityFinder = new HashMap<>(){{
        put(AbilityType.Farming, farming);
        put(AbilityType.Fishing, fishing);
        put(AbilityType.Foraging, foraging);
        put(AbilityType.Mining, mining);
    }};
    private int energy, dayEnergy, maxEnergy = 200;
    private Ability farming, mining, foraging, fishing;
    private Cell currentCell; // TODO: sobhan
    private FarmMap currentFarmMap;
    private int money;
    private Tool currentTool;
    //TODO : refresh every morning
    private Map<NPC , Boolean> npcMetToday = new HashMap<>();
    private Map<NPC , Boolean> npcGiftToday = new HashMap<>();
    private Player spouse = null; // in case the player gets married
    private Poll poll = null; // in order to terminate the game

    public Player(String username, String password, String nickname, String email, Gender gender) {
        super(username, password, nickname, email, gender);
        initFields();
    }

    public Player(User user) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getGender());
        initFields();
    }

    private void initFields() {
        this.energy = 50;
        this.dayEnergy = 200;
        this.farming = new Ability();
        this.mining = new Ability();
        this.foraging = new Ability();
        this.fishing = new Ability();
        this.currentCell = null; // TODO: sobhan. ko ja bashe?
        this.money = 0; // TODO: parsa. pool?
        this.currentTool = null;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
        if (spouse != null)
            spouse.addMoney(money);
    }

    public void spendMoney(int money) {
        this.money -= money;
        if (spouse != null)
            spouse.spendMoney(money);
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

    public void setFarmMap(FarmMap farmMap) {
        this.currentFarmMap = farmMap;
    }

    public void setSpouse(Player spouse) {
        this.spouse = spouse;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public void walk(Building destination) {

    }

    public void consumeEnergy(int val) {
        energy -= val;
        if (energy < 0) {
            this.passOut();
        }
    }

    public void setNextTurnEnergy() {
        dayEnergy += energy;
        int val = Math.min(50, dayEnergy);
        energy = val;
        dayEnergy -= val;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getDayEnergy() {
        return dayEnergy;
    }

    public void setDayEnergy(int dayEnergy) {
        this.dayEnergy = dayEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void goToSleep() {
        //set the energy for tommorow and...
    }

    public void passOut() { // TODO: sobhan. bedard mikhore in?

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
        // TODO
        return null;
    }

    public void trashItem(String itemName, int n) {

    }

    public void trashItem(String itemName) {

    }

    public Position getPosition() {
        return currentCell.getPosition();
    }

    public Map<NPC, Boolean> getNpcMetToday() {
        return npcMetToday;
    }

    public Map<NPC, Boolean> getNpcGiftToday() {
        return npcGiftToday;
    }

    public Ability getAbility(AbilityType type) {
        return abilityFinder.get(type);
    }
}
