package org.example.common.database;

import org.example.server.models.Game;
import org.example.server.models.SecurityQuestion;
import org.example.server.models.ServerApp;
import org.example.server.models.User;
import org.example.server.models.enums.Gender;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseHelper {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(null, null, "users", null)) {
                if (!tables.next()) {
                    createUserTable();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "nickname TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "gender TEXT NOT NULL," +
                "securityQuestion TEXT," +
                "answer TEXT," +
                "maxMoney INTEGER DEFAULT 0," +
                "numberOfGamesPlayed INTEGER DEFAULT 0" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
        }
    }

    public static User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                return new User(
                        username,
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("securityQuestion"),
                        rs.getString("answer"),
                        rs.getInt("maxMoney"),
                        rs.getInt("numberOfGamesPlayed")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing Gender enum for user " + username + ": " + e.getMessage());
        }
        return null;
    }

    public static void registerUser(User user) {
        String sql = "INSERT INTO users(username, password, nickname, email, gender, securityQuestion, answer) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getGender().toString());
            pstmt.setString(6, user.getRecoveryQuestion().getQuestion());
            pstmt.setString(7, user.getRecoveryQuestion().getAnswer());

            pstmt.executeUpdate();
            System.out.println("User '" + user.getUsername() + "' registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering user '" + user.getUsername() + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void changePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password for user '" + username + "' changed successfully.");
            } else {
                System.out.println("User '" + username + "' not found, password not changed.");
            }
        } catch (SQLException e) {
            System.err.println("Error changing password for user '" + username + "': " + e.getMessage());
        }
    }

    public static void changeUsername(String currentUsername, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newUsername);
            pstmt.setString(2, currentUsername);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Username changed from '" + currentUsername + "' to '" + newUsername + "' successfully.");
            } else {
                System.out.println("User '" + currentUsername + "' not found, username not changed.");
            }
        } catch (SQLException e) {
            System.err.println("Error changing username from '" + currentUsername + "' to '" + newUsername + "': " + e.getMessage());
        }
    }

    public static void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User '" + username + "' deleted successfully.");
            } else {
                System.out.println("User '" + username + "' not found, no deletion performed.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user '" + username + "': " + e.getMessage());
        }
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    User user = new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("nickname"),
                            rs.getString("email"),
                            rs.getString("gender"),
                            rs.getString("securityQuestion"),
                            rs.getString("answer"),
                            rs.getInt("maxMoney"),
                            rs.getInt("numberOfGamesPlayed")
                    );
                    users.add(user);
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping user due to invalid Gender or SecurityQuestion data: " + rs.getString("username") + " - " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }
}

