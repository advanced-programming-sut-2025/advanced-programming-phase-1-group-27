package controller;

import enums.Menu;
import models.Result;

public class ProfileMenuController extends MenuController {
    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu == Menu.GameMenu)
            return new Result(false, "you should first go to main menu.");

    }

    public Result changeUsername(String username) {}

    public Result changeNickname(String nickname) {}

    public Result changeEmail(String email) {}

    public Result changePassword(String oldPassword, String newPassword) {}

    public Result showInfo() {}
}
