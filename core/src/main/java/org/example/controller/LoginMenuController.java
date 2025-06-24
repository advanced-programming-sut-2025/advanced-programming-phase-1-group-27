package org.example.controller;

import org.example.models.App;
import org.example.models.SecurityQuestion;
import org.example.models.User;
import org.example.models.enums.Menu;
import org.example.models.Result;
import org.example.view.menu.LoginMenuView;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenuController extends MenuController {
    private LoginMenuView view;

    public LoginMenuController(LoginMenuView view) {
        this.view = view;
    }

    public void handleLoginMenuButtons(){




    }


    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu == Menu.MainMenu)
            return new Result(false, "you should first login to enter this menu.");
        else
            return new Result(false, "Can't enter this menu!");
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.WelcomeMenu);
        return new Result(true, "Redirecting to welcome menu ...");
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
            App.deleteLoginUserFile();
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
        String newPassword = App.generatePassword();
        user.setPassword(User.hashPassword(newPassword));
        return new Result(true, "Your new password is: " + newPassword);
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
}
