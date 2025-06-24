package org.example.controller;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.SecurityQuestion;
import org.example.models.User;
import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.example.view.menu.RegisterMenuView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenuController extends MenuController {
    private RegisterMenuView view;
    private User suggestedUser = null;

    public RegisterMenuController(RegisterMenuView view) {
        this.view = view;
    }

    public void handleRegisterMenuButtons() {}


    @Override
    public Result enterMenu(String menuName) {
        return new Result(false, "You can't enter any menu from this menu!");
    }

    @Override
    public Result exitMenu() {
        App.setCurrentMenu(Menu.WelcomeMenu);
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
        String securityQuestions = App.getSecurityQuestions();
        view.printString(securityQuestions);
        Result result = view.pickQuestion(scanner);
        if (!result.success())
            return result;
        // successful registration ...
        suggestedUser.addRecoveryQuestions();
        App.addUser(suggestedUser);
        App.setCurrentMenu(Menu.LoginMenu);
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
}
