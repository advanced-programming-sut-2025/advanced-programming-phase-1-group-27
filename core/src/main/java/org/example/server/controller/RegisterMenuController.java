package org.example.server.controller;

import com.badlogic.gdx.graphics.Color;
import org.example.client.Main;
import org.example.server.models.*;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.Menu;
import org.example.client.view.menu.RegisterMenuView;
import org.example.client.view.menu.SecurityQuestionMenuView;
import org.example.client.view.menu.WelcomeMenuView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenuController extends MenuController {
    private RegisterMenuView view;
    private User suggestedUser = null;
    private String suggestedUsername = null;

    public RegisterMenuController(RegisterMenuView view) {
        this.view = view;
    }


    public GraphicalResult registerViaGraphics() {

        GraphicalResult registerAttempt = checkAllFieldsAreFilled();

        if (registerAttempt.hasError())
            return registerAttempt;

        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        String email = view.getEmailField().getText();
        String nickname = view.getNicknameField().getText();

        GraphicalResult registerAttemptResult = checkRegistrationAttempt(username, password, email, nickname);

        if (registerAttemptResult.hasError())
            return registerAttemptResult;

        App.setCurrentMenu(Menu.ForgetPasswordMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SecurityQuestionMenuView(new User(username, User.hashPassword(password), nickname, email, Gender.values()[view.getGenderBox().getSelectedIndex()])));
        return new GraphicalResult("Successful registration!", Color.GREEN);
    }

    private GraphicalResult checkAllFieldsAreFilled() {

        if (view.getUsernameField().getText().isEmpty() ||
                view.getPasswordField().getText().isEmpty() ||
                view.getEmailField().getText().isEmpty() ||
                view.getNicknameField().getText().isEmpty()) {

            return new GraphicalResult(
                    "Please fill all the required fields",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }

        return new GraphicalResult("", GameAssetManager.getGameAssetManager().getAcceptColor(), false);
    }

    public GraphicalResult checkRegistrationAttempt(String username, String password, String email, String nickname) {

        if (!User.isValidUsername(username))
            return new GraphicalResult(
                    "Username format is invalid!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        if (App.getUserByUsername(username) != null) {

            suggestedUsername = App.generateUsername(username);
            view.setReRegister(true);
            GraphicalResult result = new GraphicalResult(
                    "Username already exists! Suggested username: " + suggestedUsername,
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
            result.setDisplayTime(Float.MAX_VALUE);
            return result;
        }

        if (!User.isValidEmail(email))
            return new GraphicalResult(
                    "Email format is invalid!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        Result passwordCheck = User.checkPassword(password);
        if (!passwordCheck.success())
            return new GraphicalResult(passwordCheck.message(), GameAssetManager.getGameAssetManager().getErrorColor());

        return new GraphicalResult(
                "Successful registration!",
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public void setRandomPassword() {

        view.getPasswordField().setText(App.generatePassword());

    }

    public GraphicalResult acceptSuggestedUsername() {

        view.setReRegister(false);
        view.getUsernameField().setText(suggestedUsername);
        return new GraphicalResult(
                "Suggested username accepted!",
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );

    }

    public GraphicalResult declineSuggestedUsername() {
        view.setReRegister(false);
        view.getUsernameField().setText("");
        return new GraphicalResult(
                "Suggested username declined!",
                GameAssetManager.getGameAssetManager().getErrorColor(),
                false
        );

    }

    @Override
    public Result enterMenu(String menuName) {
        return new Result(false, "You can't enter any menu from this menu!");
    }

    @Override
    public Result exitMenu() {
        playClickSound();
        App.setCurrentMenu(Menu.WelcomeMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new WelcomeMenuView());
        return new Result(true, "Redirecting to welcome menu ...");
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
            String newPassword = App.generatePassword();
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
        String securityQuestions = App.getQuestionsString();
        view.printString(securityQuestions);
        Result result = view.pickQuestion(scanner);
        if (!result.success())
            return result;
        // successful registration ...
        App.addUser(suggestedUser);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "Registration was successful. you can now login!");
    }

    public Result reRegister(String response) {
        if (response.equals("Y") || response.equals("y"))
            return new Result(true, "Suggested username accepted!");
        return new Result(false, "suggested username rejected!");
    }

    public Result checkAnswer(Matcher matcher) { // checking answers for register questions
        if (matcher == null)
            return new Result(false, "Command format is invalid. Registration failed!");
        int questionId = Integer.parseInt(matcher.group("questionId").trim()) - 1;
        String question = App.getSecurityQuestion(questionId);
        if (question == null)
            return new Result(false, "Question not found. Registration failed");
        String answer = matcher.group("answer").trim();
        String reenteredAnswer = matcher.group("reenteredAnswer").trim();
        if (!answer.equals(reenteredAnswer))
            return new Result(false, "Reentered answer doesn't match answer. Registration failed!");
        SecurityQuestion securityQuestion = new SecurityQuestion(question, answer);
        suggestedUser.setRecoveryQuestion(securityQuestion);
        return new Result(true, "Baba benazam!");
    }
}
