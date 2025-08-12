package org.example.common.database;

import org.example.common.models.Message;
import org.example.common.models.Time;
import org.example.common.utils.JSONUtils;
import org.example.server.models.Lobby;
import org.example.server.models.User;
import org.example.server.models.enums.Weathers.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseHelper {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private static final String DB_SAVE = "jdbc:sqlite:saves.db";

    public static void createDatabaseForUser() {
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

    public static void createDatabaseForSave() {
        try (Connection conn = DriverManager.getConnection(DB_SAVE)) {
            DatabaseMetaData meta = conn.getMetaData();

            try (ResultSet tables = meta.getTables(null, null, "saves", null)) {
                if (!tables.next()) {
                    createSaveTable();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void createSaveTable() {
        String sql = "CREATE TABLE IF NOT EXISTS saves (" +
                "lobbyId INTEGER DEFAULT 0," +
                "lobby TEXT NOT NULL," +
                "Time TEXT DEFAULT NULL," +
                "Weather TEXT DEFAULT NULL," +
                "Trades TEXT DEFAULT NULL," +
                "Gifts TEXT DEFAULT NULL," +
                "NumberOfUsers INTEGER DEFAULT 0," +
                "User1 TEXT DEFAULT NULL," +
                "User2 TEXT DEFAULT NULL," +
                "User3 TEXT DEFAULT NULL," +
                "User4 TEXT DEFAULT NULL" +
                ")";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Save table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating saves table: " + e.getMessage());
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
                "numberOfGamesPlayed INTEGER DEFAULT 0," +
                "avatarId INTEGER DEFAULT 0" +
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
                        rs.getInt("numberOfGamesPlayed"),
                        rs.getInt("avatarId")
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

    public static void changeUsername(String username, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newUsername);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Username changed from '" + username + "' to '" + newUsername + "' successfully.");
            } else {
                System.out.println("User '" + username + "' not found, username not changed.");
            }
        } catch (SQLException e) {
            System.err.println("Error changing username from '" + username + "' to '" + newUsername + "': " + e.getMessage());
        }
    }

    public static void changeNickname(String username, String newNickname) {
        String sql = "UPDATE users SET nickname = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newNickname);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Nickname for user '" + username + "' changed to '" + newNickname + "' successfully.");
            } else {
                System.out.println("User '" + username + "' not found, nickname not changed.");
            }
        } catch (SQLException e) {
            System.err.println("Error changing nickname for user '" + username + "': " + e.getMessage());
        }
    }

    public static void  changeEmail(String username, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Email for user '" + username + "' changed to '" + newEmail + "' successfully.");
            } else {
                System.out.println("User '" + username + "' not found, email not changed.");
            }
        } catch (SQLException e) {
            System.err.println("Error changing email for user '" + username + "': " + e.getMessage());
        }
    }

    public static void changeAvatar(String username, int avatarId) {
        String sql = "UPDATE users SET avatarId = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, avatarId);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("AvatarID for user '" + username + "' changed to '" + avatarId + "' successfully.");
            } else {
                System.out.println("User '" + username + "' not found, nickname not changed.");
            }
        } catch (SQLException e) {
            System.err.println("Error changing avatarId for user '" + username + "': " + e.getMessage());
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
                            rs.getInt("numberOfGamesPlayed"),
                            rs.getInt("avatarId")
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

    // Save

    public static void saveLobby(Lobby lobby) {
        String sql = "INSERT INTO saves(lobbyId, lobby) VALUES(?, ?)";
        int lobbyId = lobby.getId();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String lobbyGson = JSONUtils.toJson(new Message(new HashMap<>(){{
                put("lobby", lobby.getInfo());
            }} , Message.Type.save));

            pstmt.setInt(1, lobbyId);
            pstmt.setString(2, lobbyGson);

            pstmt.executeUpdate();
            System.out.println("Lobby " + lobbyId + " saved successfully.");
        } catch (SQLException e) {
            System.err.println("Error saving lobby " + lobbyId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveTimeAndWeather(Lobby lobby, Time time, Weather weather) {
        String sql = "UPDATE saves SET Time = ?, Weather = ? WHERE lobbyId = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, time.toString());
            pstmt.setString(2, weather.toString());
            pstmt.setInt(3, lobby.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Time and weather for lobbyId '" + lobby.getId() +
                        "' updated successfully.");
            } else {
                System.out.println("LobbyId '" + lobby.getId() + "' not found, update skipped.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating time and weather for lobbyId '" +
                    lobby.getId() + "': " + e.getMessage());
        }
    }


}

