package org.example.models;

import org.example.models.Map.Map;
import org.example.models.NPCs.NPC;
import org.example.models.enums.AbilityType;
import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.Recipe;
import org.example.models.Map.FarmMap;
import org.example.models.enums.items.ToolType;
import org.example.models.tools.Backpack;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends User {
    private ArrayList<Recipe> availableCraftingRecipes = new ArrayList<>(); // TODO: this should be filled when abilities are upgraded or recipes are purchased from a shop
    private ArrayList<Recipe> availableCookingRecipes = new ArrayList<>();
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
    private boolean passedOut = false;
    private Ability farming, mining, foraging, fishing;
    private Cell currentCell; // TODO: sobhan
    private Menu currentMenu; // TODO: sobhan. depends on current cell
    private Map currentMap = null; // TODO rassa reeeedi
    private FarmMap farmMap = null;
    private int money;
    private Tool currentTool;
    //TODO : refresh every morning
    private java.util.Map<NPC , Boolean> npcMetToday = new HashMap<>();
    private java.util.Map<NPC , Boolean> npcGiftToday = new HashMap<>();
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
        this.dayEnergy = 150; // 50 (energy) + 150 (day energy) = 200
        this.farming = new Ability();
        this.mining = new Ability();
        this.foraging = new Ability();
        this.fishing = new Ability();
        this.currentCell = null; // TODO: sobhan. ko ja bashe?
        this.money = 0; // TODO: parsa. pool?
        this.currentTool = null;
        // crafting recipes
        availableCraftingRecipes.add(Recipe.FurnaceRecipe);
        availableCraftingRecipes.add(Recipe.ScarecrowRecipe);
        availableCraftingRecipes.add(Recipe.MayonnaiseRecipe);
        // cooking recipes
        availableCookingRecipes.add(Recipe.FriedEggRecipe);
        availableCookingRecipes.add(Recipe.BakedFishRecipe);
        availableCookingRecipes.add(Recipe.SaladRecipe);
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

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setFarmMap(Map farmMap) {
        this.currentMap = farmMap;
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

    public ArrayList<Recipe> getAvailableCraftingRecipes() {
        return availableCraftingRecipes;
    }

    public ArrayList<Recipe> getAvailableCookingRecipes() {
        return availableCookingRecipes;
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

    public void reduceEnergy(int amount) {
        energy -= amount;
        if (energy <= 0 && dayEnergy <= 0)
            passedOut = true;
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

    public boolean hasPassedOut() {
        return passedOut;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Position getPosition() {
        return currentCell.getPosition();
    }

    public java.util.Map<NPC, Boolean> getNpcMetToday() {
        return npcMetToday;
    }

    public java.util.Map<NPC, Boolean> getNpcGiftToday() {
        return npcGiftToday;
    }

    public Ability getAbility(AbilityType type) {
        return abilityFinder.get(type);
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public FarmMap getFarmMap() {
        return farmMap;
    }

    public void setFarmMap(FarmMap farmMap) {
        this.farmMap = farmMap;
    }

    public boolean hasEnoughIngredients(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (getAvailableIngredient(ingredient) == null)
                return false;
        }
        return true;
    }

    public void useRecipe(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            Item item = getAvailableIngredient(ingredient);
            backpack.reduceItems(item, ingredient.getQuantity());
        }
        backpack.addItems(recipe.getFinalProduct(), StackLevel.Basic, 1);
    }

    public Item getItemFromBackpack(String itemName) {
        for (Stacks slot : backpack.getItems()) {
            if (slot.getItem().getName().equals(itemName))
                return slot.getItem();
        }
        return null;
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
    
    private Item getAvailableIngredient(Ingredient ingredient) {
        for (Item item : ingredient.getPossibleIngredients()) {
            if (hasEnoughItem(item, ingredient.getQuantity()))
                return item;
        }
        return null;
    }

    private boolean hasEnoughItem(Item item, int quantity) {
        int counter = 0;
        for (Stacks slot : backpack.getItems()) {
            if (slot.getItem().getName().equals(item.getName()))
                counter += slot.getQuantity();
        }
        return counter >= quantity;
    }
}
