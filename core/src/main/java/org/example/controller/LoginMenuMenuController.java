package org.example.controller;

import org.example.models.App;
import org.example.models.SecurityQuestion;
import org.example.models.User;
import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.example.models.Result;
import org.example.view.menu.LoginMenu;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

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

        // checking whether the reentered password matches the password
        while (!password.equals(reEnteredPassword)) {
            reEnteredPassword = view.reTypePassword(scanner);
            if (reEnteredPassword.equals("RANDOM"))
                password = reEnteredPassword;
        }
        // handling random password generator
        while (password.equals("RANDOM")) {
            String newPassword = generatePassword();
            String result = view.isPasswordAccepted(newPassword, scanner);
            if (result.equals("Y") || result.equals("y"))
                password = newPassword;
            else if (result.equals("E"))
                return new Result(false, "Registration failed! Redirecting to login menu ...");
        }

        Result passwordCheck = User.checkPassword(password);
        if (!passwordCheck.success())
            return passwordCheck;

        if (Gender.getGender(gender) == null)
            return new Result(false, "Gender is invalid!");

        // obtaining new username if the username already exists
        suggestedUser = new User(username, User.hashPassword(password), nickname, email, Gender.getGender(gender));
        if (App.getUserByUsername(username) != null) {
            String suggestedUsername = App.generateUsername(username);
            if (suggestedUsername == null)
                return new Result(false, "Username already taken. Registration failed!");
            if (!view.suggestUsername(suggestedUsername, scanner).success())
                return new Result(false, "Username already taken. Registration failed!");
            suggestedUser.setUsername(suggestedUsername);
        }

        // handling security questions ...
        String securityQuestions = App.getSecurityQuestions();
        view.printString(securityQuestions);
        Result result = view.pickQuestion(scanner);
        if (!result.success())
            return result;
        // successful registration ...
        suggestedUser.addRecoveryQuestions();
        App.addUser(suggestedUser);
        return new Result(true, "Registration was successful. you can now login!");
    }

    public Result reRegister(String response) {
        if (response.equals("Y") || response.equals("y"))
            return new Result(true, "suggested username accepted!");
        return new Result(false, "suggested username rejected!");
    }

    public Result checkAnswer(Matcher matcher) { // checking answers for register questions
        if (matcher == null)
            return new Result(false, "Command format is invalid. Registration failed!");
        int questionId = Integer.parseInt(matcher.group("questionId").trim()) - 1;
        SecurityQuestion question = App.getSecurityQuestion(questionId);
        if (question == null)
            return new Result(false, "Question not found. Registration failed");
        String answer = matcher.group("answer").trim();
        String reenteredAnswer = matcher.group("reenteredAnswer").trim();
        if (!answer.equals(reenteredAnswer))
            return new Result(false, "Reentered answer doesn't match answer. Registration failed!");
        if (!answer.equals(question.getAnswer()))
            return new Result(false, "Answer is incorrect. Registration failed!");
        return new Result(true, "Baba benazam!");
    }

    public Result login(String username, String password, boolean stayLoggedIn) {
        User user = App.getUserByUsername(username);
        if (user == null)
            return new Result(false, "Username not found!");
        if (!user.passwordEquals(password))
            return new Result(false, "Incorrect password!");
        if (stayLoggedIn)
            updateFile(user);
        else
            clearFile();
        App.setLoggedInUser(user);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "You have successfully logged in.");
    }

    public Result forgetPassword(String username, Scanner scanner) {
        User user = App.getUserByUsername(username);
        if (user == null)
            return new Result(false, "Username not found!");
        for (SecurityQuestion recoveryQuestion : user.getRecoveryQuestions()) {
            view.printString(recoveryQuestion.getQuestion());
            Matcher matcher = view.getAnswer(scanner);
            if (matcher == null)
                return new Result(false, "Answer format is invalid!");
            String answer = matcher.group("answer").trim();
            if (!answer.equals(recoveryQuestion.getAnswer()))
                return new Result(false, "Incorrect answer. Recovery failed!");
        }
        String newPassword = generatePassword();
        user.setPassword(User.hashPassword(newPassword));
        return new Result(true, "Your new password is: " + newPassword);
    }

    private String generatePassword() {
        Random random = new Random( );
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

    private void updateFile(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("nickname", user.getNickname());
        jsonObject.put("gender", user.getGender());
        jsonObject.put("maxMoneyEarned", user.getMaxMoneyEarned());
        jsonObject.put("numberOfGamesPlayed", user.getNumberOfGamesPlayed());

        File file = new File("data/login_user.json");
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonObject.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFile() {
        try (FileWriter writer = new FileWriter("data/login_user.json")) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
