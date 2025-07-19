package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {
    private User admin;
    private ArrayList<User> users = new ArrayList<>();
    private boolean isPublic;
    private boolean isVisible;
    private int id;
    private String name, password;
    private Game game = null;

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
        this.id = (int) info.get("id");
        this.name = (String) info.get("name");
        this.isPublic = (boolean) info.get("isPublic");
        this.isVisible = (boolean) info.get("isVisible");
        this.password = (String) info.get("password");
        this.admin = new User((LinkedTreeMap<String, Object>) info.get("adminInfo"));
        ArrayList<LinkedTreeMap<String, Object>> usersInfo = (ArrayList) info.get("usersInfo");
        for (LinkedTreeMap<String, Object> userInfo : usersInfo) {
            users.add(new User(userInfo));
        }
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean addUser(User user) {
        if (users.size() == 4)
            return false;
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
        info.put("usersInfo", usersInfo);
        return info;
    }
}
