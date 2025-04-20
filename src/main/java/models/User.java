package models;

import java.util.ArrayList;
import java.util.HashMap;

import enums.AbilityType;
import enums.Gender;
import enums.Item;
import enums.MachineTypes;
import enums.product.CraftingProduct;

public class User {

    private String username, password, nickname, email;
    private final Gender gender;
    // shows the amount of each item in stock
    private HashMap<Item, Integer> itemAmount = new HashMap<>();
    private ArrayList<CraftingProduct> availableRecipes = new ArrayList<>();
    // can contain either of items
    private ArrayList<Item> inventory = new ArrayList<>();
    // items which are place in the fridge
    private ArrayList<Item> refrigerator = new ArrayList<>();
    private ArrayList<MachineTypes> availableMachines = new ArrayList<>();
    // maps ability type to user's ability
    private HashMap<AbilityType, Ability> abilityFinder = new HashMap<>(); // TODO: this hashmap should be initialized before
    private int energy, maxEnergy = 200;
    private Ability farming, mining, foraging, fishing;
    private Cell currentCell;
    private Map currentMap;
    private int money;

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

    public static boolean isValidUsername(String username) {}

    public static boolean isValidEmail(String email) {}

    public static boolean isValidPassword(String password) {}

    public static boolean isStrongPassword(String password) {}

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

    public int getMoney() {
        return money;
    }
}
