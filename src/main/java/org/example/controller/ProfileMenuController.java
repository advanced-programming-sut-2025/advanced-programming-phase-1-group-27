package org.example.controller;

import org.example.models.App;
import org.example.models.enums.Menu;
import org.example.models.Result;
import org.example.view.menu.ProfileMenu;

public class ProfileMenuController extends MenuController {
    private ProfileMenu view;

    public ProfileMenuController(ProfileMenu view) {
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

    public Result changeUsername(String username) {}

    public Result changeNickname(String nickname) {}

    public Result changeEmail(String email) {}

    public Result changePassword(String oldPassword, String newPassword) {}

    public Result showInfo() {}
}
