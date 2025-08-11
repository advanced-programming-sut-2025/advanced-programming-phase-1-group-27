package org.example.server.models.NPCs;

import org.example.server.models.Stacks;

public class Quest {
    private Stacks Reward;
    private Stacks Request;
    private String PlayerName;
    private boolean isDone;
    private int index;

    public Quest(Stacks request, Stacks reward , int index) {
        Reward = reward;
        Request = request;
        this.PlayerName = "";
        this.isDone = false;
        this.index = index;
    }

    public Stacks getReward() {
        return Reward;
    }

    public Stacks getRequest() {
        return Request;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
