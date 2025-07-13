package org.example.server.controller;

import org.example.server.models.App;
import org.example.server.models.Result;
import org.example.server.models.User;
import org.example.server.models.enums.Menu;
import org.example.client.view.menu.ProfileMenuView;

public class ProfileMenuController extends MenuController {
    private final ProfileMenuView view;

    public ProfileMenuController(ProfileMenuView view) {
        this.view = view;
    }

    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu != Menu.MainMenu)
            return new Result(false, "can't enter this menu!");
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public Result changeUsername(String newUsername) {
        if (!User.isValidUsername(newUsername))
            return new Result(false, "Username format is invalid!");
        if (App.getLoggedInUser().getUsername().equals(newUsername))
            return new Result(false, "New username should be different from current username!");
        if (App.getUserByUsername(newUsername) != null)
            return new Result(false, "Username already exists!");
        App.getLoggedInUser().setUsername(newUsername);
        return new Result(true, "Username successfully changed!");
    }

    public Result changeNickname(String nickname) {
        if (App.getLoggedInUser().getNickname().equals(nickname))
            return new Result(false, "New nickname should be different from current nickname!");
        App.getLoggedInUser().setNickname(nickname);
        return new Result(true, "Nickname changed successfully!");
    }

    public Result changeEmail(String email) {
        if (App.getLoggedInUser().getEmail().equals(email))
            return new Result(false, "New email should be different from current email!");
        if (!User.isValidEmail(email))
            return new Result(false, "Email format is invalid!");
        App.getLoggedInUser().setEmail(email);
        return new Result(true, "Email changed successfully!");
    }

    public Result changePassword(String oldPassword, String newPassword) {
        if (!App.getLoggedInUser().passwordEquals(oldPassword))
            return new Result(false, "Old password is incorrect!");
        if (oldPassword.equals(newPassword))
            return new Result(false, "New password must be different of old password!");
        Result result = User.checkPassword(newPassword);
        if (!result.success())
            return result;
        App.getLoggedInUser().setPassword(User.hashPassword(newPassword));
        return new Result(true, "Password changed successfully!");
    }

    public Result showInfo() {
        User user = App.getLoggedInUser();
        String result = "User info:\n" + "username: " + user.getUsername() + "\n" +
                "nickname: " + user.getNickname() + "\n" +
                "maximum money earned in a single game: " + user.getMaxMoneyEarned() + "\n" +
                "number of games played: " + user.getNumberOfGamesPlayed();
        return new Result(true, result);
    }
}
