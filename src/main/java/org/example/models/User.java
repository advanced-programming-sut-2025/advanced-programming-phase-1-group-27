package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.models.enums.AbilityType;
import org.example.models.enums.Gender;
import org.example.models.enums.items.Item;
import org.example.models.enums.items.ArtisanTypes;
import org.example.models.enums.items.RecipeType;
import org.example.models.enums.items.products.CraftingProduct;
import org.example.models.tools.Backpack;
import org.example.models.tools.Tool;

public class User {

    private String username, password, nickname, email;
    private final Gender gender;
    private ArrayList<RecipeType> availableRecipes; // TODO: this should be filled when abilities are upgraded or recipes are purchased from a shop
    // user's inventory
    private Backpack backpack;
    // items which are place in the fridge
    private ArrayList<Stack> refrigerator = new ArrayList<>();
    // maps ability type to user's ability
    private HashMap<AbilityType, Ability> abilityFinder = new HashMap<>(); // TODO: this hashmap should be initialized before
    private int energy, maxEnergy = 200;
    private Ability farming, mining, foraging, fishing;
    private Cell currentCell;
    private Map currentMap;
    private int money;
    private Tool currentTool;

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public static boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z0-9-]+$");
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("(?<mail>[\\w.-]+)@(?<domain>[A-Za-z0-9-]+)\\.(?<TLD>[A-Za-z]{2,})");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            return false;
        return isValidMail(matcher.group("mail")) &&
                isValidDomain(matcher.group("domain")) &&
                isValidTLD(matcher.group("TLD"));
    }

    public static boolean isValidPassword(String password) {

    }

    private static boolean isValidMail(String mail) {
        if (!String.valueOf(mail.charAt(0)).matches("[A-Za-z0-9]"))
            return false;
        if (!String.valueOf(mail.charAt(mail.length() - 1)).matches("[A-Za-z0-9]"))
            return false;
        for (int i = 1; i < mail.toCharArray().length; i++) {
            if (mail.charAt(i) == '.' && mail.charAt(i - 1) == '.')
                return false;
        }
        return true;
    }

    private static boolean isValidDomain(String domain) {
        if (!String.valueOf(domain.charAt(0)).matches("[A-Za-z0-9]"))
            return false;
        return String.valueOf(domain.charAt(domain.length() - 1)).matches("[A-Za-z0-9]");
    }

    private static boolean isValidTLD(String TLD) {
        return TLD.length() >= 2;
    }
}
