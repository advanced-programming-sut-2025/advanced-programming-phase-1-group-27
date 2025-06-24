package org.example.view.menu;

import org.example.controller.LoginMenuController;
import org.example.controller.MainMenuController;
import org.example.controller.ProfileMenuController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuTest {
    private static MainMenuView mainMenu;
    private static MainMenuController mainMenuController;
    private static LoginMenuView loginMenu;
    private static LoginMenuController loginMenuController;
    private static ProfileMenuView profileMenu;
    private static ProfileMenuController profileMenuController;
    private static User[] users = new User[4];

    @BeforeAll
    static void setUp() {
        mainMenu = new MainMenuView();
        mainMenuController = new MainMenuController(mainMenu);
        loginMenu = new LoginMenuView();
        loginMenuController = new LoginMenuController(loginMenu);
        profileMenu = new ProfileMenuView();
        profileMenuController = new ProfileMenuController(profileMenu);
        users[0] = new User("rassa", "R@Ssa!384", "rassa", "rassa@gmail.com", Gender.Male);
        users[1] = new User("parsa", "Parsa!384", "parsa", "parsa@gmail.com", Gender.Male);
        users[2] = new User("sobhan", "Sobhan!384", "sobhan", "sobhan@gmail.com", Gender.Male);
        users[3] = new User("yousof", "Abdi!384", "yousof", "yousof@gmail.com", Gender.Male);
        for (User user : users) {
            registerUser(user);
        }
    }

    @Test
    void enterExitMenuTest() {
        Result result = mainMenuController.enterMenu("login menu");
        assertFalse(result.success());
        assertEquals("you should logout to enter this menu!", result.message());

        result = mainMenuController.enterMenu("profile menu");
        assertTrue(result.success());
        assertEquals("Redirecting to profile menu ...", result.message());
        assertEquals(Menu.ProfileMenu, App.getCurrentMenu());

        result = profileMenuController.exitMenu();
        assertTrue(result.success());
        assertEquals("Redirecting to main menu ...", result.message());
        assertEquals(Menu.MainMenu, App.getCurrentMenu());
    }

    @Test
    void createGameTest() {
        loginUser(users[0]);
        // flag cannot be empty
        Result result = mainMenuController.createNewGame(null, null, null, null, null);
        assertFalse(result.success());
        assertEquals("No username provided!", result.message());

        // cannot play with more than 3 users
        result = mainMenuController.createNewGame("parsa", "sobhan", "yousof", "another", null);
        assertFalse(result.success());
        assertEquals("You can't play with more than 3 players!", result.message());

        // invalid usernames
        result = mainMenuController.createNewGame("parsa", "dige chia", "sobhan", null, null);
        assertFalse(result.success());
        assertEquals("Username not found!", result.message());

        // duplicate usernames
        result = mainMenuController.createNewGame("rassa", null, null, null, null);
        assertFalse(result.success());
        assertEquals("Duplicate username found!", result.message());

        result = mainMenuController.createNewGame("parsa", "parsa", null, null, null);
        assertFalse(result.success());
        assertEquals("Duplicate username found!", result.message());

        // creating game successfully
        String input = "game map 0\ngame map 1\ngame map 2\n game map 3";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        result = mainMenuController.createNewGame("parsa", "sobhan", null, null, scanner);
        assertTrue(result.success());
        assertEquals("Game created! You are now in your House!", result.message());

        // can't create new game if game exists
        result = mainMenuController.createNewGame("parsa", "sobhan", null, null, null);
        assertFalse(result.success());
        assertEquals("You are already in a game!", result.message());

        mainMenuController.logout();
        loginUser(users[3]);
        result = mainMenuController.createNewGame("rassa", null, null, null, null);
        assertFalse(result.success());
        assertEquals("User is already in a game!", result.message());
    }

    private static void registerUser(User user) {
        String input = "pick question -q 1 -a 4 -c 4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);

        Result result = loginMenuController.register(user.getUsername(), user.getPassword(),
                user.getPassword(), user.getNickname(), user.getEmail(), "male", scanner);
        assertTrue(result.success());
    }

    private static void loginUser(User user) {
        Result result = loginMenuController.login(user.getUsername(), user.getPassword(), false);
        assertTrue(result.success());
        assertEquals("You have successfully logged in.", result.message());
        assertEquals(user.getUsername(), App.getLoggedInUser().getUsername());
        assertEquals(Menu.MainMenu, App.getCurrentMenu());
    }
}
