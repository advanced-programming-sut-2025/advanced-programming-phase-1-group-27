package models;

import enums.product.CraftingProduct;
import enums.Menu;

import java.util.ArrayList;

public class App {
    private User LoggedInUser = null;
    private Game currentGame = null;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<SecurityQuestion> questions = new ArrayList<>();
    private Menu currentMenu = Menu.LoginMenu;
    private ArrayList<CraftingProduct> allRecipes = new ArrayList<>(); // TODO: arraylist should be initialized
    // list of questions in order to recover password
    private ArrayList<SecurityQuestion> recoveryQuestions = new ArrayList<>();

    public static User getUserByUsername(String username) {}

    public User getLoggedInUser() {
        return LoggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        LoggedInUser = loggedInUser;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<SecurityQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<SecurityQuestion> questions) {
        this.questions = questions;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public ArrayList<CraftingProduct> getAllRecipes() {
        return allRecipes;
    }

    public void setAllRecipes(ArrayList<CraftingProduct> allRecipes) {
        this.allRecipes = allRecipes;
    }

    public ArrayList<SecurityQuestion> getRecoveryQuestions() {
        return recoveryQuestions;
    }

    public void setRecoveryQuestions(ArrayList<SecurityQuestion> recoveryQuestions) {
        this.recoveryQuestions = recoveryQuestions;
    }
}
