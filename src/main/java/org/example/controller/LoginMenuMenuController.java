package org.example.controller;

import org.example.models.App;
import org.example.models.User;
import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.example.models.Result;
import org.example.view.menu.LoginMenu;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class LoginMenuMenuController extends MenuController {
    private LoginMenu view;
    private User suggestedUser = null;

    public LoginMenuMenuController(LoginMenu view) {
        this.view = view;
    }

    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        return new Result(false, "you should first login to enter this menu.");
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.ExitMenu);
        return null;
    }

    public Result register(String username, String password, String reEnteredPassword,
                           String nickname, String email, String gender, Scanner scanner) {
        if (!User.isValidUsername(username))
            return new Result(false, "Username format is invalid!");
        if (!User.isValidEmail(email))
            return new Result(false, "Email format is invalid!");

        // handling random password generator
        while (password.equals("RANDOM")) {
            String newPassword = generatePassword();
            String result = view.isPasswordAccepted(newPassword, scanner);
            if (result.equals("Y") || result.equals("y"))
                password = reEnteredPassword = newPassword;
            else if (result.equals("E"))
                return new Result(false, "Registration failed! Redirecting to login menu ...");
        }

        if (password.length() < 8)
            return new Result(false, "Password must be at least 8 characters!");
        if (!password.matches("^.*[A-Z].*$"))
            return new Result(false, "Password must contain uppercase letters!");
        if (!password.matches("^.*[a-z].*$"))
            return new Result(false, "Password must contain lowercase letters!");
        if (!password.matches("^.*[0-9].*$"))
            return new Result(false, "Password must contain numbers!");
        if (!password.matches("^.*[?><,\"';:\\\\/|\\]\\[}{+=)(*&~%$#!].*$"))
            return new Result(false, "Password must contain special characters!");

        if (Gender.getGender(gender) == null)
            return new Result(false, "Gender is invalid!");

        // obtaining new username if the username already exists
        suggestedUser = new User(username, password, nickname, email, Gender.getGender(gender));
        if (App.getUserByUsername(username) != null) {
            String suggestedUsername = App.generateUsername(username);
            if (!view.suggestUsername(suggestedUsername, scanner).success())
                return new Result(false, "Registration failed!");
            suggestedUser.setUsername(suggestedUsername);
        }
        // checking whether the reentered password matches the password
        while (!password.equals(reEnteredPassword)) {
            reEnteredPassword = view.reTypePassword(scanner);
        }

        // TODO: Security questions
        App.addUser(suggestedUser);
        return new Result(true, "Registration was successful. you can now login!");
    }

    public Result reRegister(String response) {
        if (response.equals("Y") || response.equals("y"))
            return new Result(true, "suggested username accepted!");
        return new Result(false, "suggested username rejected!");
    }

    public Result pickQuestion(int questionId, String answer, String reEnteredAnswer) {}

    public Result login(String username, String password, boolean stayLoggedIn) {}

    public Result answerQuestion(String answer) {}

    private String generatePassword() {
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
