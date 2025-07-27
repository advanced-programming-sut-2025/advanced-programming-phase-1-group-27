package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lobby {
    private User admin;
    private final ArrayList<User> users = new ArrayList<>();
    private final boolean isPublic;
    private final boolean isVisible;
    private final int id;
    private final String name;
    private final String password;
    private HashMap<String, Integer> usernameToMap = new HashMap<>();
    private Game game = null;
    private boolean active;

    public Lobby(User admin, boolean isPublic, String password, boolean isVisible, int id, String name) {
        this.admin = admin;
        this.isPublic = isPublic;
        this.password = password;
        this.isVisible = isVisible;
        this.id = id;
        this.name = name;
        users.add(admin);
    }

    public Lobby(LinkedTreeMap<String, Object> info) {
        this.id = ((Number) info.get("id")).intValue();
        this.name = (String) info.get("name");
        this.isPublic = (boolean) info.get("isPublic");
        this.isVisible = (boolean) info.get("isVisible");
        this.password = (String) info.get("password");
        this.admin = new User((LinkedTreeMap<String, Object>) info.get("adminInfo"));
        ArrayList<LinkedTreeMap<String, Object>> usersInfo = (ArrayList) info.get("usersInfo");
        for (LinkedTreeMap<String, Object> userInfo : usersInfo) {
            users.add(new User(userInfo));
        }
        LinkedTreeMap<String, Object> mapSelection = (LinkedTreeMap<String, Object>) info.get("usernameToMap");
        this.usernameToMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : mapSelection.entrySet()) {
            this.usernameToMap.put(entry.getKey(), ((Number) entry.getValue()).intValue());
        }
    }

    public HashMap<String, Integer> getUsernameToMap() {
        return usernameToMap;
    }

    public void removeUsernameMap(String username) {
        usernameToMap.remove(username);
    }

    public void setMap(String username, int mapIndex) {
        usernameToMap.put(username, mapIndex);
    }

    public int getMapIndex(User user) {
        if (usernameToMap.containsKey(user.getUsername())) return usernameToMap.get(user.getUsername());
        return -1;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean addUser(User user) {
        if (users.size() == 4) return false;
        users.add(user);
        return true;
    }

    public int getId() {
        return id;
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("id", id);
        info.put("name", name);
        info.put("isPublic", isPublic);
        info.put("isVisible", isVisible);
        info.put("password", password);
        info.put("adminInfo", admin.getInfo());
        ArrayList<HashMap<String, Object>> usersInfo = new ArrayList<>();
        for (User user : users) {
            usersInfo.add(user.getInfo());
        }
        info.put("usernameToMap", usernameToMap);
        info.put("usersInfo", usersInfo);
        return info;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean condition) {
        active = condition;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
