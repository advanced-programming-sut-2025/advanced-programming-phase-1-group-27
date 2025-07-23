package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lobby {
    private User admin;
    private ArrayList<User> users = new ArrayList<>();
    private boolean isPublic;
    private boolean isVisible;
    private int id;
    private String name, password;
    private HashMap<Integer,String> usersAndChosenMaps = new HashMap<>();
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
        usersAndChosenMaps.put(0 , "");
        usersAndChosenMaps.put(1 , "");
        usersAndChosenMaps.put(2 , "");
        usersAndChosenMaps.put(3 , "");
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
        LinkedTreeMap<String, Object> users = (LinkedTreeMap<String, Object>) info.get("maps");
        this.usersAndChosenMaps = new HashMap<>();
        for (Map.Entry<String, Object> entry : users.entrySet()) {
            int key = Integer.parseInt(entry.getKey());
            String value = entry.getValue() != null ? entry.getValue().toString() : "";
            this.usersAndChosenMaps.put(key, value);
        }
    }

    public HashMap<Integer, String> getUsersAndChosenMaps() {
        return usersAndChosenMaps;
    }

    public void setMaps(int num , String  username){
        usersAndChosenMaps.put(num , username);
    }

    public int getIndexMap(User user){
        if(usersAndChosenMaps.get(0).equals(user.getUsername())){
            return 0;
        }else if(usersAndChosenMaps.get(1).equals(user.getUsername())){
            return 1;
        }else  if(usersAndChosenMaps.get(2).equals(user.getUsername())){
            return 2;
        }else  if(usersAndChosenMaps.get(3).equals(user.getUsername())){
            return 3;
        }else {
            return -1;
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
        info.put("maps" ,  usersAndChosenMaps);
        info.put("usersInfo", usersInfo);
        return info;
    }

    public String toString() {
        // Add a lock icon for private lobbies
        String privateIndicator = isPublic ? " 🔒" : "";

        // Format: "Lobby Name 🔒 (Players: X/Y)"
        return name + privateIndicator + " (Players: " + users.size() + "/" + 4 + ")";
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

    public void setActive(boolean condition){
        if(condition){
            active = true;
        }else {
            active = false;
        }
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public boolean isActive() {
        return active;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
