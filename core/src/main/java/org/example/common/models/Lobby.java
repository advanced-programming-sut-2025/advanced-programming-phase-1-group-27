package org.example.common.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.ServerApp;
import org.example.server.models.ServerGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Lobby {
    private User admin;
    private final ArrayList<User> users = new ArrayList<>();
    private final boolean isPublic;
    private final boolean isVisible;
    private final int id;
    private final String name;
    private final String password;
    private HashMap<String, Integer> usernameToMap = new HashMap<>();
    private ServerGame serverGame = null;
    private boolean active;
    private AtomicLong lastChange = new AtomicLong(System.currentTimeMillis());
    private AtomicInteger votesToTerminate = new AtomicInteger(0);
    private AtomicInteger numberOfVotes = new AtomicInteger(0);

    public Lobby () {
        this.admin = new User();
        this.isPublic = true;
        this.isVisible = true;
        this.id = 0;
        this.name = "";
        this.password = "";
    }

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
        lastChange.set(System.currentTimeMillis());
        users.add(user);
        return true;
    }

    public void removeUser(User user) {
        users.remove(user);
        usernameToMap.remove(user.getUsername());
        lastChange.set(System.currentTimeMillis());
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

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
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

    public ServerGame getGame() {
        return serverGame;
    }

    public void setGame(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    public AtomicLong getLastChange() {
        return lastChange;
    }

    public void vote(boolean vote) {
        if (vote)
            votesToTerminate.incrementAndGet();
        numberOfVotes.incrementAndGet();
    }

    public void startVote() {
        votesToTerminate.set(0);
        numberOfVotes.set(0);
    }

    public boolean hasPollWon() {
        return numberOfVotes.get() == users.size() && votesToTerminate.get() > numberOfVotes.get() / 2;
    }

    public void kickPlayer(String playerName) {
        User user = getUserByUsername(playerName);
        users.remove(user);
        usernameToMap.remove(playerName);
        if (admin.getUsername().equals(playerName)) {
            admin = users.getFirst();
            serverGame.setAdmin(admin);
        }
        serverGame.kickPlayer(playerName);
    }

    public void notifyAll(Message message) {
        for (User user : users) {
            try {
                ServerApp.getClientConnectionThreadByUsername(user.getUsername()).sendMessage(message);
            } catch (Exception e) {
                System.err.println("Error notifying user " + user.getUsername());
                e.printStackTrace();
            }
        }
    }

    public void notifyExcept(String username, Message message) {
        for (User user : users) {
            if (!user.getUsername().equals(username))
                ServerApp.getClientConnectionThreadByUsername(user.getUsername()).sendMessage(message);
        }
    }

    public void notifyUser(User user, Message message) {
        ServerApp.getClientConnectionThreadByUsername(user.getUsername()).sendMessage(message);
    }

    public void createBasicGame() {
        ArrayList<Player> players = new ArrayList<>();
        for (User user : users) {
            players.add(new Player(user));
        }
        ServerGame serverGame;
        setGame(serverGame = new ServerGame(this, players));
        serverGame.init();
        for (Player player : players) {
            int mapIndex = getUsernameToMap().get(player.getUsername());
            player.setFarmMap(serverGame.getFarmMap(mapIndex));
            player.setCurrentCell(serverGame.getFarmMap(mapIndex).getCell(8, 70));
        }
    }

    public boolean hasUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return  false;
    }
}
