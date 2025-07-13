package org.example.common.database;

import org.example.server.models.Game;
import org.example.server.models.SecurityQuestion;
import org.example.server.models.ServerApp;
import org.example.server.models.User;
import org.example.server.models.enums.Gender;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseHelper {
    public class DatabaseHelper {
        private static final String DB_URL = "jdbc:sqlite:game.db";

        public static void createDatabase() {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                DatabaseMetaData meta = conn.getMetaData();
                try (ResultSet tables = meta.getTables(null, null, "users",
                        null)) {
                    if (!tables.next()) {
                        createUserTable();
                    }
                }
            } catch (SQLException e) {
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
                    "numberOfGames INTEGER DEFAULT 0,";

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void changePassword(String username, String newPassword) {
            String sql = "UPDATE users SET password = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, newPassword);
                pstmt.setString(2, username);
                pstmt.executeUpdate();
            } catch (SQLException e) {
            }
        }

        public static void changeUsername(String currentUsername, String newUsername) {
            String sql = "UPDATE users SET username = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, newUsername);
                pstmt.setString(2, currentUsername);
                pstmt.executeUpdate();
            } catch (SQLException e) {
            }
        }

        public static void deleteUser(String username) {
            String sql = "DELETE FROM users WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, username);
                pstmt.executeUpdate();
            } catch (SQLException e) {
            }
        }

        public static void getAllUsers() {
            ArrayList<User> users = new ArrayList<>();
            String sql = "SELECT * FROM users";

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
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
                }
            } catch (SQLException e) {
            }
            ServerApp.setUsers(users);
        }
    }
}
