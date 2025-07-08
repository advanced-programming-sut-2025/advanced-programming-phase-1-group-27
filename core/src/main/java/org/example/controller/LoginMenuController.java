package org.example.controller;

import org.example.Main;
import org.example.models.*;
import org.example.models.enums.Menu;
import org.example.view.menu.ForgetPasswordMenuView;
import org.example.view.menu.LoginMenuView;
import org.example.view.menu.MainMenuView;
import org.example.view.menu.WelcomeMenuView;
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

    public GraphicalResult loginViaGraphics() {

        if (hasEmptyField())
            return new GraphicalResult(
                    "Please fill all the required fields",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        GraphicalResult loginAttempt = login(view.getUsernameField().getText(), view.getPasswordField().getText(), view.getStayLoggedInCheckBox().isChecked());

        if (loginAttempt.hasError())
            return loginAttempt;

        App.setCurrentMenu(Menu.MainMenu);
        App.setLoggedInUser(App.getUserByUsername(view.getUsernameField().getText()));
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
        return loginAttempt;
    }

    private boolean hasEmptyField() {

        return view.getUsernameField().getText().isEmpty() || view.getPasswordField().getText().isEmpty();

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

    public void goToForgetPassword() {
        App.setCurrentMenu(Menu.ForgetPasswordMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ForgetPasswordMenuView());
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.WelcomeMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new WelcomeMenuView());
        return new Result(true, "Redirecting to welcome menu ...");
    }

    public GraphicalResult login(String username, String password, boolean stayLoggedIn) {
        User user = App.getUserByUsername(username);
        if (user == null)
            return new GraphicalResult(
                    "Username not found!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        if (!user.passwordEquals(password))
            return new GraphicalResult(
                    "Incorrect password!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        if (stayLoggedIn)
            updateFile(user);
        else
            App.deleteLoginUserFile();
        App.setLoggedInUser(user);
        App.setCurrentMenu(Menu.MainMenu);
        return new GraphicalResult(
                "You have successfully logged in.",
                GameAssetManager.getGameAssetManager().getAcceptColor(), false
        );
    }

    public Result forgetPassword(String username, Scanner scanner) {
        User user = App.getUserByUsername(username);
        if (user == null)
            return new Result(false, "Username not found!");
        SecurityQuestion recoveryQuestion = user.getRecoveryQuestion();
        view.printString(recoveryQuestion.getQuestion());
        Matcher matcher = view.getAnswer(scanner);
        if (matcher == null)
            return new Result(false, "Answer format is invalid!");
        String answer = matcher.group("answer").trim();
        if (!answer.equals(recoveryQuestion.getAnswer()))
            return new Result(false, "Incorrect answer. Recovery failed!");
//        view.printString("");
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
        jsonObject.put("recoveryQuestion", user.getRecoveryQuestion().getQuestion());
        jsonObject.put("recoveryAnswer", user.getRecoveryQuestion().getAnswer());
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
