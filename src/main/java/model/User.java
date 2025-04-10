package model;

import enums.*;
import enums.product.CraftingProduct;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username, password, nickname, email;
    private final Gender gender;
    // list of questions in order to recover password
    private ArrayList<SecurityQuestion> recoveryQuestions = new ArrayList<>();
    // shows the amount of each material in stock
    private HashMap<Item, Integer> materialAmount = new HashMap<>();
    private ArrayList<CraftingProduct> availableRecipes = new ArrayList<>();
    // can contain either of Material or any product
    private ArrayList<Object> inventory = new ArrayList<>();
    private ArrayList<Object> inHandItems = new ArrayList<>();
    private ArrayList<MachineTypes> availableMachines = new ArrayList<>();

    public static boolean isValidUsername(String username) {}

    public static boolean isValidEmail(String email) {}

    public static boolean isValidPassword(String password) {}

    public static boolean isStrongPassword(String password) {}
}
