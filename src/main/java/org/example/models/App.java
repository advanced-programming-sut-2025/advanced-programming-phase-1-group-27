package org.example.models;

import org.example.models.enums.items.products.CraftingProduct;
import org.example.models.enums.Menu;

import java.util.ArrayList;
import java.util.Random;

public class App {
    private static User LoggedInUser = null;
    private static Game currentGame = null;
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<SecurityQuestion> questions = new ArrayList<>();
    private static Menu currentMenu = Menu.LoginMenu;
    private static ArrayList<CraftingProduct> allRecipes = new ArrayList<>(); // TODO: arraylist should be initialized
    // list of questions in order to recover password
    private static ArrayList<SecurityQuestion> recoveryQuestions = new ArrayList<>();

    public static User getLoggedInUser() {
        return LoggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        LoggedInUser = loggedInUser;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        App.users.add(user);
    }

    public static ArrayList<SecurityQuestion> getQuestions() {
        return questions;
    }

    public static void setQuestions(ArrayList<SecurityQuestion> questions) {
        App.questions = questions;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static ArrayList<CraftingProduct> getAllRecipes() {
        return allRecipes;
    }

    public static void setAllRecipes(ArrayList<CraftingProduct> allRecipes) {
        App.allRecipes = allRecipes;
    }

    public static ArrayList<SecurityQuestion> getRecoveryQuestions() {
        return recoveryQuestions;
    }

    public static void setRecoveryQuestions(ArrayList<SecurityQuestion> recoveryQuestions) {
        App.recoveryQuestions = recoveryQuestions;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static String generateUsername(String username) {
        String result;
        Random random = new Random();
        do {
            int randomInt = random.nextInt(20);
            result = username + randomInt;
        } while (getUserByUsername(result) != null);
        return result;
    }
}
