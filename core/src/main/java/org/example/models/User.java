package org.example.models;

import org.example.models.enums.Gender;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String username, password, nickname, email;
    private Gender gender;
    // security questions to recover forgotten password
    private ArrayList<SecurityQuestion> recoveryQuestions = new ArrayList<>();
    private Game currentGame = null;
    private int maxMoneyEarned = 0;
    private int numberOfGamesPlayed = 0;

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

    public User() {

    }

    public static boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z0-9-]+$");
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("(?<mail>[\\w.-]+)@(?<domain>[A-Za-z0-9-]+)\\.(?<TLD>[A-Za-z]{2,})");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            return false;
        return isValidMail(matcher.group("mail")) &&
                isValidDomain(matcher.group("domain")) &&
                isValidTLD(matcher.group("TLD"));
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Result checkPassword(String password) {
        if (password.length() < 8)
            return new Result(false, "Password must be at least 8 characters!");
        if (!password.matches("^.*[A-Z].*$"))
            return new Result(false, "Password must contain uppercase letters!");
        if (!password.matches("^.*[a-z].*$"))
            return new Result(false, "Password must contain lowercase letters!");
        if (!password.matches("^.*[0-9].*$"))
            return new Result(false, "Password must contain numbers!");
        if (!password.matches("^.*[?><,\"';:\\\\/|\\]\\[}{+=)(*&~%$#!].*$"))
            return new Result(false, "Password must contain special characters!");
        return new Result(true, "Password is valid!");
    }

    private static boolean isValidMail(String mail) {
        if (!String.valueOf(mail.charAt(0)).matches("[A-Za-z0-9]"))
            return false;
        if (!String.valueOf(mail.charAt(mail.length() - 1)).matches("[A-Za-z0-9]"))
            return false;
        for (int i = 1; i < mail.toCharArray().length; i++) {
            if (mail.charAt(i) == '.' && mail.charAt(i - 1) == '.')
                return false;
        }
        return true;
    }

    private static boolean isValidDomain(String domain) {
        if (!String.valueOf(domain.charAt(0)).matches("[A-Za-z0-9]"))
            return false;
        return String.valueOf(domain.charAt(domain.length() - 1)).matches("[A-Za-z0-9]");
    }

    private static boolean isValidTLD(String TLD) {
        return TLD.length() >= 2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordEquals(String password) {
        String hashedPassword = hashPassword(password);
        return MessageDigest.isEqual(
                Base64.getDecoder().decode(this.password),
                Base64.getDecoder().decode(hashedPassword)
        );
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMaxMoneyEarned() {
        return maxMoneyEarned;
    }

    public void setMaxMoneyEarned(int maxMoneyEarned) {
        this.maxMoneyEarned = maxMoneyEarned;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public void addNumberOfGamesPlayed() {
        numberOfGamesPlayed++;
    }

    public ArrayList<SecurityQuestion> getRecoveryQuestions() {
        return recoveryQuestions;
    }

    public void addRecoveryQuestions() {
        recoveryQuestions.add(new SecurityQuestion("Eneter your email: ", email));
        recoveryQuestions.add(new SecurityQuestion("Enter your nickname: ", nickname));
        // other questions can be added
    }
}
