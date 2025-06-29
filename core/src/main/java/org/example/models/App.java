package org.example.models;

import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.example.models.enums.Questions;
import org.example.models.enums.items.products.CraftingProduct;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class App {

    private static final String loggedInUserFilePath = "data/login_user.json";
    private static User LoggedInUser = null;
    private static Game currentGame = null;
    private static ArrayList<User> users = new ArrayList<>();
    private static Menu currentMenu = Menu.WelcomeMenu;
    private static ArrayList<CraftingProduct> allRecipes = new ArrayList<>(); // TODO: arraylist should be initialized


    static {
        User savedUser = getSavedUser();
        if (savedUser != null) {
            LoggedInUser = savedUser;
            users.add(savedUser);
            currentMenu = Menu.MainMenu;
        }
        users.add(new User("yusof","yusof@1384","joe","yusof@gmail.com",Gender.Male));

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

    public static String getQuestionsString() {
        StringBuilder result = new StringBuilder("Security Questions:");
        for (int i = 0; i < Questions.values().length; i++) {
            result.append("\n");
            result.append("\t").append(i + 1).append(". ");
            result.append(Questions.values()[i].getQuestion());
        }
        return result.toString();
    }

    public static String getSecurityQuestion(int id) {
        if (id >= Questions.values().length)
            return null;
        return Questions.values()[id].getQuestion();
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

    public static User getSavedUser() {
        File file = new File(loggedInUserFilePath);
        if (!file.exists() || file.length() == 0)
            return null;

        try (FileReader reader = new FileReader(file)) {
            JSONObject json = new JSONObject(new JSONTokener(reader));
            User result = new User();
            result.setUsername(json.optString("username"));
            result.setPassword(json.optString("password"));
            result.setNickname(json.optString("nickname"));
            result.setEmail(json.optString("email"));
            result.setRecoveryQuestion(new SecurityQuestion(
                    json.optString("recoveryQuestion"),
                    json.optString("recoveryAnswer")
            ));
            result.setGender(Gender.getGender(json.optString("gender")));
            result.setMaxMoneyEarned(json.optInt("maxMoneyEarned"));
            result.setNumberOfGamesPlayed(json.optInt("numberOfGamesPlayed"));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static void deleteLoginUserFile() {
        File file = new File(loggedInUserFilePath);
        if (!file.exists() || file.length() == 0)
            return;
        file.delete();
    }

    public static String generatePassword() {
        Random random = new Random();
        int passwordLen = 8 + random.nextInt(5);
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialCharacters = "?><,\"';:\\/|][}{+=)(*&~%$#!";
        ArrayList<Character> passwordCharacters = new ArrayList<>();
        passwordCharacters.add(lowercase.charAt(random.nextInt(lowercase.length())));
        passwordCharacters.add(uppercase.charAt(random.nextInt(uppercase.length())));
        passwordCharacters.add(numbers.charAt(random.nextInt(numbers.length())));
        passwordCharacters.add(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        String allCharacters = lowercase + uppercase + numbers + specialCharacters;
        for (int i = 4; i < passwordLen; i++)
            passwordCharacters.add(allCharacters.charAt(random.nextInt(allCharacters.length())));

        // shuffling selected characters (the first four characters are not random)
        Collections.shuffle(passwordCharacters, random);

        StringBuilder password = new StringBuilder();
        for (Character c : passwordCharacters) {
            password.append(c);
        }
        return password.toString();
    }
}
