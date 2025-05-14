package org.example.models;

import org.example.models.Map.Map;
import org.example.models.NPCs.NPC;
import org.example.models.Relations.Dialogue;
import org.example.models.Relations.Relation;
import org.example.models.enums.*;
import org.example.models.enums.items.Recipe;
import org.example.models.Map.FarmMap;
import org.example.models.enums.items.ToolType;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.CraftingProduct;
import org.example.models.tools.Backpack;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends User {
    private ArrayList<Recipe> availableCraftingRecipes = new ArrayList<>();
    private ArrayList<Recipe> availableCookingRecipes = new ArrayList<>();
    // player's inventory
    private Backpack backpack = new Backpack(ToolType.BasicBackpack); // TODO: ba parsa check shavad
    // items which are place in the fridge
    private final Backpack refrigerator = new Backpack(ToolType.LargeBackpack);
    // maps ability type to user's ability
    private HashMap<AbilityType, Ability> abilityFinder = new HashMap<>() {{
        put(AbilityType.Farming, farming);
        put(AbilityType.Fishing, fishing);
        put(AbilityType.Foraging, foraging);
        put(AbilityType.Mining, mining);
    }};
    private int energy, dayEnergy, maxEnergy = 200, boostEnergy = 0;
    private Ability farming, mining, foraging, fishing;
    private Cell currentCell;
    private Menu currentMenu = Menu.Home; // TODO : sobhan. depends on current cell
    private Map currentMap = null; // TODO : rassa reeeedi
    private FarmMap farmMap = null;
    private int money;
    private Tool currentTool;
    private ArrayList<Dialogue> dialogues = new ArrayList<>();
    private java.util.Map<Player, Relation> relations = new HashMap<>();
    //refresh every morning
    private java.util.Map<NPC, Boolean> npcMetToday = new HashMap<>();
    private java.util.Map<NPC, Boolean> npcGiftToday = new HashMap<>();
    private java.util.Map<Player, Boolean> playerMetToday = new HashMap<>();
    private java.util.Map<Player, Boolean> playerGiftToday = new HashMap<>();
    private java.util.Map<Player , Boolean> playerHuggedToday = new HashMap<>();
    private java.util.Map<Player, Boolean> playerTradeToday = new HashMap<>();
    private Player spouse = null; // in case the player gets married
    private Buff currentBuff = null;

    {
        backpack.addItems(ToolType.BasicAxe, ToolType.BasicAxe.getLevel(), 1);
        backpack.addItems(ToolType.BasicHoe, ToolType.BasicHoe.getLevel(), 1);
        backpack.addItems(ToolType.BasicPickaxe, ToolType.BasicPickaxe.getLevel(), 1);
        backpack.addItems(ToolType.Scythe, ToolType.Scythe.getLevel(), 1);
        backpack.addItems(ToolType.BasicWateringCan, ToolType.BasicWateringCan.getLevel(), 1);
        backpack.addItems(ToolType.TrainingRod, ToolType.TrainingRod.getLevel(), 1);
        backpack.addItems(ToolType.BasicTrashCan, ToolType.BasicTrashCan.getLevel(), 1);
    }

    public void refreshNPCThings() {
        npcMetToday.clear();
        npcGiftToday.clear();
        for(NPC npc : App.getCurrentGame().getNPCs()){
            npcMetToday.put(npc, false);
            npcGiftToday.put(npc, false);
        }
    }

    public void refreshPlayerThings() {
        java.util.Map<Player , Boolean> players = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers()) {
            if(playerMetToday.get(player) == Boolean.TRUE
                    || playerGiftToday.get(player) == Boolean.TRUE
                    || playerHuggedToday.get(player) == Boolean.TRUE
                    || playerTradeToday.get(player) == Boolean.TRUE) {
                players.put(player, true);
            }else {
                players.put(player, false);
            }
        }
        for(Player player : App.getCurrentGame().getPlayers()) {
            if(player == App.getCurrentGame().getCurrentPlayer()) {
                continue;
            }else {
                if(players.get(player) == Boolean.FALSE) {
                    decreaseXP(player , 10);
                }
            }
        }
        playerMetToday.clear();
        playerGiftToday.clear();
        playerHuggedToday.clear();
        playerTradeToday.clear();
        for(Player player : App.getCurrentGame().getPlayers()){
            playerMetToday.put(player, false);
            playerGiftToday.put(player, false);
            playerHuggedToday.put(player, false);
            playerTradeToday.put(player, false);
        }
    }

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
        this.currentCell = null;
        this.money = 8569;
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

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
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

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(ToolType backpack) {

    }

    public Backpack getRefrigerator() {
        return refrigerator;
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

    public Player getSpouse() {
        return spouse;
    }

    public void removeBuff() {
        if (currentBuff == null)
            return;
        if (currentBuff.getAbility() == AbilityType.MaxEnergyUltimate ||
                currentBuff.getAbility() == AbilityType.MaxEnergyCommunity) {
            boostEnergy = 0;
        } else {
            abilityFinder.get(currentBuff.getAbility()).reduceLevel();
            removeRecipes(currentBuff.getAbility(), abilityFinder.get(currentBuff.getAbility()).getLevel() + 1);
        }
        currentBuff = null;
    }

    public void setBuff(Buff buff) {
        currentBuff = new Buff(buff);
        if (currentBuff.getAbility() == AbilityType.MaxEnergyUltimate) {
            boostEnergy = 100;
        } else if (currentBuff.getAbility() == AbilityType.MaxEnergyCommunity) {
            boostEnergy = 50;
        } else {
            abilityFinder.get(currentBuff.getAbility()).addLevel();
            addRecipes(currentBuff.getAbility(), abilityFinder.get(currentBuff.getAbility()).getLevel());
        }
    }

    public Buff getCurrentBuff() {
        return currentBuff;
    }

    public ArrayList<Recipe> getAvailableCraftingRecipes() {
        return availableCraftingRecipes;
    }

    public ArrayList<Recipe> getAvailableCookingRecipes() {
        return availableCookingRecipes;
    }

    public void consumeEnergy(int amount) {
        energy -= amount;
        if (amount > boostEnergy) {
            dayEnergy -= amount - boostEnergy;
            boostEnergy = 0;
        } else
            boostEnergy -= amount;
    }

    public void setNextTurnEnergy() {
        energy = Math.min(50, dayEnergy + boostEnergy);
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int amount) {
        energy += amount;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
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
        return (energy <= 0 && dayEnergy <= 0 && boostEnergy <= 0);
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
            if (slot.getItem().getName().equalsIgnoreCase(itemName))
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
        int level = farming.getLevel();
        farming.addXp(xp);
        if (farming.getLevel() > level) {
            addRecipes(AbilityType.Farming, level + 1);
        }
    }

    public void mineXp(int xp) {
        int level = mining.getLevel();
        mining.addXp(xp);
        if (mining.getLevel() > level) {
            addRecipes(AbilityType.Mining, level + 1);
        }
    }

    public void forageXp(int xp) {
        int level = foraging.getLevel();
        foraging.addXp(xp);
        if (foraging.getLevel() > level) {
            addRecipes(AbilityType.Foraging, level + 1);
        }
    }

    public void fishXp(int xp) {
        int level = fishing.getLevel();
        fishing.addXp(xp);
        if (fishing.getLevel() > level) {
            addRecipes(AbilityType.Fishing, level + 1);
        }
    }

    public String showItems() {
        // TODO
        return null;
    }

    public void trashItem(String itemName, int n) {

    }

    public void trashItem(String itemName) {

    }

    public boolean isByWater() {
        for (Cell adjacentCell : currentCell.getAdjacentCells()) {
            if (adjacentCell.getType() == CellType.Water)
                return true;
        }
        return false;
    }

    public Item getAvailableIngredient(Ingredient ingredient) {
        for (Item item : ingredient.getPossibleIngredients()) {
            if (backpack.hasEnoughItem(item, ingredient.getQuantity()))
                return item;
        }
        return null;
    }

    private void removeRecipes(AbilityType type, int level) {
        ArrayList<Recipe> recipes = Ability.getRecipeList(type, level);
        for (Recipe recipe : recipes) {
            if (recipe.getFinalProduct() instanceof CraftingProduct)
                availableCraftingRecipes.remove(recipe);
            else if (recipe.getFinalProduct() instanceof CookingProduct)
                availableCookingRecipes.remove(recipe);
            // TODO
        }
    }

    private void addRecipes(AbilityType type, int level) {
        ArrayList<Recipe> recipes = Ability.getRecipeList(type, level);
        for (Recipe recipe : recipes) {
            if (recipe.getFinalProduct() instanceof CraftingProduct)
                availableCraftingRecipes.add(recipe);
            else if (recipe.getFinalProduct() instanceof CookingProduct)
                availableCookingRecipes.add(recipe);
        }
    }

    public void addXP(Player player, int amount) {
        if (!relations.containsKey(player)) {
            relations.put(player, new Relation());
        }
        Relation relation = relations.get(player);
        int xp = relation.getXp();
        int level = relation.getLevel();
        xp += amount;
        int max = (level + 1) * 100;
        if (xp > max) {
            if (level == 4) {
                xp = max;
            }else if (level == 2) {
                xp = max;
            }else if (level == 3) {
                xp = max;
            } else {
                xp -= max;
                relation.setLevel(level + 1);
            }
        }
        relation.setXp(xp);
    }

    public boolean canMarried(Player player) {
        if(!relations.containsKey(player)){
            relations.put(player, new Relation());
        }
        Relation relation = relations.get(player);
        int level = relation.getLevel();
        int xp = relation.getXp();
        int max = (level + 1) * 100;
        if(level == 3
                && xp == max){
            return true;
        }
        return false;
    }

    public void setCurrentMap(Map map) {
        currentMap = map;
    }

    public boolean canFlowered(Player player){
        if(!relations.containsKey(player)){
            relations.put(player, new Relation());
        }
        Relation relation = relations.get(player);
        int level = relation.getLevel();
        int xp = relation.getXp();
        int max = (level + 1) * 100;
        if(level == 2
                && xp == max){
            return true;
        }
        return false;
    }

    public void goNextLevel(Player player) {
        Relation relation = relations.get(player);
        int level = relation.getLevel();
        int xp = relation.getXp();
        relation.setLevel(level + 1);
        relation.setXp(0);
    }

    public void decreaseXP(Player player, int amount) {
        if (!relations.containsKey(player)) {
            relations.put(player, new Relation());
        }
        Relation relation = relations.get(player);
        int xp = relation.getXp();
        int level = relation.getLevel();
        xp -= amount;
        if (xp < 0) {
            if (level == 0) {
                xp = 0;
            }else {
                xp -= level * 100;
                level--;
                relation.setLevel(level);
            }
        }
        relation.setXp(xp);
    }

    public ArrayList<Dialogue> getDialogues() {
        return dialogues;
    }

    public void deleteDialogue(Dialogue dialogue) {
        dialogues.remove(dialogue);
    }

    public void addDialogue(Dialogue dialogue) {
        dialogues.add(dialogue);
    }

    public void deleteTalk(){
        ArrayList<Dialogue> selectedDialogues = new ArrayList<>();
        for(Dialogue dialogue : dialogues){
            if(dialogue.getType() == DialogueType.talk){
                selectedDialogues.add(dialogue);
            }
        }
        dialogues.removeAll(selectedDialogues);
    }

    public void deleteMarriage(){
        ArrayList<Dialogue> selectedDialogues = new ArrayList<>();
        for(Dialogue dialogue : dialogues){
            if(dialogue.getType() == DialogueType.Marriage){
                selectedDialogues.add(dialogue);
            }
        }
        dialogues.removeAll(selectedDialogues);
    }

    public void deleteTrades(){
        ArrayList<Dialogue> selectedDialogues = new ArrayList<>();
        for(Dialogue dialogue : dialogues){
            if(dialogue.getType() == DialogueType.Trade){
                selectedDialogues.add(dialogue);
            }
        }
        dialogues.removeAll(selectedDialogues);
    }

    public boolean isMarried(){
        return spouse != null;
    }

    public java.util.Map<Player, Relation> getRelations() {
        return relations;
    }

    public java.util.Map<Player, Boolean> getPlayerMetToday() {
        return playerMetToday;
    }

    public java.util.Map<Player, Boolean> getPlayerGiftToday() {
        return playerGiftToday;
    }

    public java.util.Map<Player, Boolean> getPlayerHuggedToday() {
        return playerHuggedToday;
    }

    public java.util.Map<Player, Boolean> getPlayerTradeToday() {
        return playerTradeToday;
    }

    public ToolType getTrashCan() {
        for (Stacks slot : backpack.getItems()) {
            if (slot.getItem() instanceof ToolType toolType) {
                if (ToolType.isTrashCan(toolType))
                    return toolType;
            }
        }
        return null;
    }

    public ToolType getBackpackType() {
        return switch (backpack.getCapacity()) {
            case 12 -> ToolType.BasicBackpack;
            case 24 -> ToolType.LargeBackpack;
            case -1 -> ToolType.DeluxeBackpack;
            default -> null;
        };
    }
}
