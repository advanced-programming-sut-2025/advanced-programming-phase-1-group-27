package controller;

import model.Result;
import model.User;

public class LoginMenuMenuController extends MenuController {
    public Result register(String username, String password, String reEnteredPassword, String nickname, String email, String gender) {}

    public Result pickQuestion(int questionId, String answer, String reEnteredAnswer) {}

    public Result login(String username, String password, boolean stayLoggedIn) {}

    public Result recoverPassword(String username) {}

    public Result answerQuestion(String answer) {}

    private String generatePassword() {}
}
