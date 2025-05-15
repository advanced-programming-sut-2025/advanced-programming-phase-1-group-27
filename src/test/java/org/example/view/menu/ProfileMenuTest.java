package org.example.view.menu;

import org.example.controller.LoginMenuMenuController;
import org.example.controller.ProfileMenuController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.User;
import org.example.models.enums.Gender;
import org.example.models.enums.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileMenuTest {
    private static ProfileMenu profileMenu;
    private static ProfileMenuController profileMenuController;
    private static LoginMenu loginMenu;
    private static LoginMenuMenuController loginMenuController;
    private static User otherUser;
    private static User userTest;

    @BeforeAll
    static void setUp() {
        profileMenu = new ProfileMenu();
        profileMenuController = new ProfileMenuController(profileMenu);
        loginMenu = new LoginMenu();
        loginMenuController = new LoginMenuMenuController(loginMenu);
        otherUser = new User("rassa", "123Rassa!", "rassa",
                "rassa.mohammadi@gmail.com", Gender.Male);
        userTest = new User("testUser", "123Test!", "testNickname",
                "t._-est1@gmail.com", Gender.Female);
        registerUser(otherUser);
        loginUser();
        App.setCurrentMenu(Menu.ProfileMenu);
    }

    @AfterEach
    void tearDown() {
        userTest = new User("testUser", "123Test!", "testNickname",
                "t._-est1@gmail.com", Gender.Female);
        App.setLoggedInUser(userTest);
    }

    @Test
    void changeUsernameTest() {
        // invalid new username
        Result result = profileMenuController.changeUsername("test!user");
        assertFalse(result.success());
        assertEquals("Username format is invalid!", result.message());

        // same username
        result = profileMenuController.changeUsername("testUser");
        assertFalse(result.success());
        assertEquals("New username should be different from current username!", result.message());

        // duplicate username
        result = profileMenuController.changeUsername("rassa");
        assertFalse(result.success());
        assertEquals("Username already exists!", result.message());

        // successful change
        result = profileMenuController.changeUsername("newUsername");
        assertTrue(result.success());
        assertEquals("Username successfully changed!", result.message());
    }

    @Test
    void changePasswordTest() {
        // incorrect old password
        Result result = profileMenuController.changePassword("123Pass!", "anything");
        assertFalse(result.success());
        assertEquals("Old password is incorrect!", result.message());

        // new password equivalent to old password
        result = profileMenuController.changePassword("123Test!", "123Test!");
        assertFalse(result.success());
        assertEquals("New password must be different of old password!", result.message());

        // new password length should be at least 8
        result = profileMenuController.changePassword("123Test!", "1Te!");
        assertFalse(result.success());
        assertEquals("Password must be at least 8 characters!", result.message());

        // new password must contain uppercase letters
        result = profileMenuController.changePassword("123Test!", "123test!");
        assertFalse(result.success());
        assertEquals("Password must contain uppercase letters!", result.message());

        // new password must contain lowercase letters
        result = profileMenuController.changePassword("123Test!", "123TEST!");
        assertFalse(result.success());
        assertEquals("Password must contain lowercase letters!", result.message());

        // new password must contain digits
        result = profileMenuController.changePassword("123Test!", "weakPassword!");
        assertFalse(result.success());
        assertEquals("Password must contain numbers!", result.message());

        // new password must contain at least one special character other than @
        result = profileMenuController.changePassword("123Test!", "123Test@");
        assertFalse(result.success());
        assertEquals("Password must contain special characters!", result.message());

        // successful password change
        result = profileMenuController.changePassword("123Test!", "123Pass!");
        assertTrue(result.success());
        assertTrue(App.getLoggedInUser().passwordEquals("123Pass!"));
    }

    @Test
    void changeNicknameTest() {
        // old nickname equivalent to new nickname
        Result result = profileMenuController.changeNickname("testNickname");
        assertFalse(result.success());
        assertEquals("New nickname should be different from current nickname!", result.message());

        // successful nickname change
        result = profileMenuController.changeNickname("newNickname");
        assertTrue(result.success());
        assertEquals("Nickname changed successfully!", result.message());
    }

    @Test
    void changeEmailTest() {
        // new email equivalent to old email
        Result result = profileMenuController.changeEmail("t._-est1@gmail.com");
        assertFalse(result.success());
        assertEquals("New email should be different from current email!", result.message());
        // valid mail should begin and end with numbers or letters
        result = profileMenuController.changeEmail("-test@gmail.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        result = profileMenuController.changeEmail("test_@gmail.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // valid mail cannot contain two consecutive dots
        result = profileMenuController.changeEmail("te..st@gmail.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // valid domain should begin and end with digits or letters
        result = profileMenuController.changeEmail("test@-gmail.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        result = profileMenuController.changeEmail("test@gmail-.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // at least one dot should exist after @
        result = profileMenuController.changeEmail("test@gmailcom");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // TLD length is at least 2
        result = profileMenuController.changeEmail("test@-gmail.c");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // email contains exactly one @
        result = profileMenuController.changeEmail("test@gm@ail.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        result = profileMenuController.changeEmail("test.gmail.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // email cannot contain special characters except @
        result = profileMenuController.changeEmail("te?st@gmail.com");
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // successful email change
        result = profileMenuController.changeEmail("new_email@gmail.com");
        assertTrue(result.success());
        assertEquals("Email changed successfully!", result.message());
    }

    private static void registerUser(User user) {
        String input = "pick question -q 1 -a 4 -c 4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);

        Result result = loginMenuController.register(user.getUsername(), user.getPassword(),
                user.getPassword(), user.getNickname(), user.getEmail(), "female", scanner);
        assertTrue(result.success());
    }

    private static void loginUser() {
        registerUser(userTest);
        Result result = loginMenuController.login(userTest.getUsername(), userTest.getPassword(), false);
        assertTrue(result.success());
        assertEquals("You have successfully logged in.", result.message());
        assertEquals(userTest.getUsername(), App.getLoggedInUser().getUsername());
        assertEquals(Menu.MainMenu, App.getCurrentMenu());
    }
}
