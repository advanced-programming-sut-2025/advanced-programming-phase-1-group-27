package models;

import enums.product.CraftingProduct;
import enums.Menu;

import java.util.ArrayList;

public class App {
    private static User LoggedInUser = null;
    private static Game currentGame = null;
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<SecurityQuestion> questions = new ArrayList<>();
    private static Menu currentMenu = Menu.LoginMenu;
    private static ArrayList<CraftingProduct> allRecipes = new ArrayList<>(); // TODO: arraylist should be initialized
    // list of questions in order to recover password
    private static ArrayList<SecurityQuestion> recoveryQuestions = new ArrayList<>();

    public static User getUserByUsername(String username) {}

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

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
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
}
