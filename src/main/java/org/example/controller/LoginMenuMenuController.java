package org.example.controller;

import org.example.enums.Menu;
import org.example.models.Result;

public class LoginMenuMenuController extends MenuController {
    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        return new Result(false, "you should first login to enter this menu.");
    }

    public Result register(String username, String password, String reEnteredPassword, String nickname, String email, String gender) {}

    public Result pickQuestion(int questionId, String answer, String reEnteredAnswer) {}

    public Result login(String username, String password, boolean stayLoggedIn) {}

    public Result recoverPassword(String username) {}

    public Result answerQuestion(String answer) {}

    private String generatePassword() {}
}
