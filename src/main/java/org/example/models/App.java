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
    private static ArrayList<SecurityQuestion> securityQuestions = new ArrayList<>();

    static  {
        initSecurityQuestions();
    }

    public static void initSecurityQuestions() {
        securityQuestions.add(new SecurityQuestion("2 + 2 = ?", "4"));
        securityQuestions.add(new SecurityQuestion("2 * 3 = ?", "6"));
        securityQuestions.add(new SecurityQuestion("Turk?", "no"));
    }

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

    public static String getSecurityQuestions() {
        StringBuilder result = new StringBuilder("Security Questions:");
        for (int i = 0; i < securityQuestions.size(); i++) {
            result.append("\n");
            result.append("\t").append(i + 1).append(". ");
            result.append(securityQuestions.get(i).getQuestion());
        }
        return result.toString();
    }

    public static SecurityQuestion getSecurityQuestion(int id) {
        if (id >= securityQuestions.size())
            return null;
        return securityQuestions.get(id);
    }

    public static void setSecurityQuestions(ArrayList<SecurityQuestion> securityQuestions) {
        App.securityQuestions = securityQuestions;
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
        for (int i = 1; i <= 20; i++) {
            result = username + i;
            if (getUserByUsername(result) == null)
                return result;
        }
        return null;
    }

    public static void removeUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                users.remove(user);
                return;
            }
        }
    }
}
