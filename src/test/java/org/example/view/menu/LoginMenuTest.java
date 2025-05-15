package org.example.view.menu;

import org.example.controller.LoginMenuMenuController;
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

public class LoginMenuTest {
    private static LoginMenu loginMenu;
    private static LoginMenuMenuController loginMenuController;
    private static User userTest;

    @BeforeAll
    static void setUp() {
        loginMenu = new LoginMenu();
        loginMenuController = new LoginMenuMenuController(loginMenu);
    }

    @BeforeEach
    void init() {
         userTest = new User("testUser", "123Test!", "testNickname",
                "t._-est1@gmail.com", Gender.Female);
    }

    @AfterEach
    void tearDown() {
        App.removeUser(userTest.getUsername());
    }

    @Test
    void securityQuestionTest() {
        // invalid command format
        String input = "answer question 1 -a 4 4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        Result result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(), userTest.getPassword(),
                userTest.getNickname(), userTest.getEmail(), "female", scanner);
        assertFalse(result.success());
        assertEquals("Command format is invalid. Registration failed!", result.message());

        // invalid question id
        input = "pick question -q -1 -a -4 c -4";
        in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(), userTest.getPassword(),
                userTest.getNickname(), userTest.getEmail(), "female", scanner);
        assertFalse(result.success());
        assertEquals("Question not found. Registration failed", result.message());

        // answer and confirmed answer mismatch
        input = "pick question -q 1 -a 5 -c 4";
        in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(), userTest.getPassword(),
                userTest.getNickname(), userTest.getEmail(), "female", scanner);
        assertFalse(result.success());
        assertEquals("Reentered answer doesn't match answer. Registration failed!", result.message());

        // incorrect answer
        input = "pick question -q 1 -a 5 -c 5";
        in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(), userTest.getPassword(),
                userTest.getNickname(), userTest.getEmail(), "female", scanner);
        assertFalse(result.success());
        assertEquals("Answer is incorrect. Registration failed!", result.message());
    }

    @Test
    void registerTest() {
        String input = "pick question -q 1 -a 4 -c 4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);

        Result result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(), userTest.getPassword(),
                userTest.getNickname(), userTest.getEmail(), "female", scanner);
        assertTrue(result.success());
        assertEquals("Registration was successful. you can now login!", result.message());

        userCreated();
    }

    @Test
    void duplicateUsernameTest() {
        User otherUser = new User("testUser", "Test123!", "otherNickname",
                "other.test@gmail.com", Gender.Male);
        // reject suggested username
        String input = "N";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);

        registerUser();
        Result result = loginMenuController.register(otherUser.getUsername(), otherUser.getPassword(),
                otherUser.getPassword(), otherUser.getNickname(), otherUser.getEmail(), "male", scanner);
        assertFalse(result.success());
        assertEquals("Username already taken. Registration failed!", result.message());

        // accept suggested username
        input = "Y\npick question -q 1 -a 4 -c 4";
        in = new ByteArrayInputStream(input.trim().getBytes());
        scanner = new Scanner(in);
        result = loginMenuController.register(otherUser.getUsername(), otherUser.getPassword(),
                otherUser.getPassword(), otherUser.getNickname(), otherUser.getEmail(), "male", scanner);
        assertTrue(result.success());
        assertEquals("Registration was successful. you can now login!", result.message());

        User user = App.getUserByUsername("testUser1");
        assertNotNull(user);
    }

    @Test
    void validUsernameTest() {
        // valid username cannot contain special characters
        userTest.setUsername("test@User");
        Result result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Username format is invalid!", result.message());
    }

    @Test
    void validEmailTest() {
        // valid mail should begin and end with numbers or letters
        userTest.setEmail("-test@gmail.com");
        Result result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        userTest.setEmail("test_@gmail.com");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // valid mail cannot contain two consecutive dots
        userTest.setEmail("te..st@gmail.com");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // valid domain should begin and end with digits or letters
        userTest.setEmail("test@-gmail.com");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        userTest.setEmail("test@gmail-.com");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // at least one dot should exist after @
        userTest.setEmail("test@gmailcom");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // TLD length is at least 2
        userTest.setEmail("test@-gmail.c");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // email contains exactly one @
        userTest.setEmail("test@gm@ail.com");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        userTest.setEmail("test.gmail.com");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());

        // email cannot contain special characters except @
        userTest.setEmail("te?st@gmail.com");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Email format is invalid!", result.message());
    }

    @Test
    void passwordAndreEnteredPasswordEqualityTest() {
        String input = "123Test!\npick question -q 1 -a 4 -c 4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);

        // password and confirmed password should be equal
        Result result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                "another password", userTest.getNickname(), userTest.getEmail(),
                "female", scanner);
        assertTrue(result.success());
        userCreated();
        App.removeUser(userTest.getUsername());

        // generate random password
        input = "RANDOM\nY\npick question -q 1 -a 4 -c 4";
        in = new ByteArrayInputStream(input.trim().getBytes());
        scanner = new Scanner(in);
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                "another password", userTest.getNickname(), userTest.getEmail(),
                "female", scanner);
        assertTrue(result.success());
        userCreated();
    }

    @Test
    void strongPasswordCheck() {
        // password length should be at least 8
        userTest.setPassword("1Te!");
        Result result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Password must be at least 8 characters!", result.message());

        // password must contain uppercase letters
        userTest.setPassword("123test!");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Password must contain uppercase letters!", result.message());

        // password must contain lowercase letters
        userTest.setPassword("123TEST!");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Password must contain lowercase letters!", result.message());

        // password must contain digits
        userTest.setPassword("weakPassword!");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Password must contain numbers!", result.message());

        // password must contain at least one special character other than @
        userTest.setPassword("123Test@");
        result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", null);
        assertFalse(result.success());
        assertEquals("Password must contain special characters!", result.message());
    }

    @Test
    void randomPasswordTest() {
        String input = "Y\npick question -q 1 -a 4 -c 4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);

        Result result = loginMenuController.register(userTest.getUsername(), "RANDOM", "RANDOM",
                userTest.getNickname(), userTest.getEmail(), "female", scanner);
        assertTrue(result.success());
    }

    @Test
    void loginTest() {
        registerUser();
        Result result = loginMenuController.login(userTest.getUsername(), userTest.getPassword(), false);
        assertTrue(result.success());
        assertEquals("You have successfully logged in.", result.message());
        assertEquals(userTest, App.getLoggedInUser());
        assertEquals(Menu.MainMenu, App.getCurrentMenu());
    }

    @Test
    void loginErrorTest() {
        registerUser();
        Result result = loginMenuController.login("anotherUsername", userTest.getPassword(), false);
        assertFalse(result.success());
        assertEquals("Username not found!", result.message());

        result = loginMenuController.login(userTest.getUsername(), "anotherPassword", false);
        assertFalse(result.success());
        assertEquals("Incorrect password!", result.message());
    }

    @Test
    void recoverPassword() {
        registerUser();
        Result result = loginMenuController.forgetPassword("anotherUsername", null);
        assertFalse(result.success());
        assertEquals("Username not found!", result.message());

        String input = "answer -a t._-est1@gmail.com\nanswer -a testNickname";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);
        result = loginMenuController.forgetPassword(userTest.getUsername(), scanner);
        assertTrue(result.success());
        assertTrue(result.message().startsWith("Your new password is:"));
    }

    private void userCreated() {
        User user = App.getUserByUsername(userTest.getUsername());
        assertNotNull(user);
        assertEquals(user.getUsername(), userTest.getUsername());
        assertEquals(user.getNickname(), userTest.getNickname());
        assertEquals(user.getEmail(), userTest.getEmail());
    }

    private void registerUser() {
        String input = "pick question -q 1 -a 4 -c 4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.trim().getBytes());
        Scanner scanner = new Scanner(in);

        Result result = loginMenuController.register(userTest.getUsername(), userTest.getPassword(),
                userTest.getPassword(), userTest.getNickname(), userTest.getEmail(), "female", scanner);
        assertTrue(result.success());
    }
}
