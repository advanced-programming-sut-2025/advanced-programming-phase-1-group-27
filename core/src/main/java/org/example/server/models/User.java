package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.ClientApp;
import org.example.client.model.ClientGame;
import org.example.server.models.enums.Gender;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username, password, nickname, email;
    private Gender gender;
    // security question to recover forgotten password
    private SecurityQuestion recoveryQuestion;
    private int maxMoneyEarned = 0;
    private int numberOfGamesPlayed = 0;
    private int avatarId = 0;

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

    public User(String username, String password, String nickname, String email, String gender ,
                String question, String answer , int maxMoneyEarned, int numberOfGamesPlayed , int avatarId) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        if(gender.equals("male")){
            this.gender =  Gender.Male;
        }else {
            this.gender =  Gender.Female;
        }
        this.recoveryQuestion = new SecurityQuestion(question, answer);
        this.maxMoneyEarned = maxMoneyEarned;
        this.numberOfGamesPlayed = numberOfGamesPlayed;
        this.avatarId = avatarId;
    }

    public User(LinkedTreeMap<String, Object> data) {
        username = (String) data.get("username");
        password = (String) data.get("password");
        nickname = (String) data.get("nickname");
        email = (String) data.get("email");
        gender = Gender.getGender((String) data.get("gender"));
        recoveryQuestion = new SecurityQuestion((String) data.get("question"), (String) data.get("answer"));
        maxMoneyEarned = ((Number) data.get("maxMoneyEarned")).intValue();
        numberOfGamesPlayed = ((Number) data.get("numberOfGamesPlayed")).intValue();
        avatarId = ((Number) data.get("avatarId")).intValue();
    }

    public User() {}

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("username", username);
        info.put("password", password);
        info.put("nickname", nickname);
        info.put("email", email);
        info.put("gender", gender);
        info.put("question", recoveryQuestion.getQuestion());
        info.put("answer", recoveryQuestion.getAnswer());
        info.put("maxMoneyEarned", maxMoneyEarned);
        info.put("numberOfGamesPlayed", numberOfGamesPlayed);
        info.put("avatarId", avatarId);
        return info;
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

    public SecurityQuestion getRecoveryQuestion() {
        return recoveryQuestion;
    }

    public void setRecoveryQuestion(SecurityQuestion securityQuestion) {
        recoveryQuestion = securityQuestion;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }
}
