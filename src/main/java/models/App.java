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

    public static User getUserByUsername(String username) {}
}
