package org.example.server.models.NPCs;

import org.example.server.models.Stacks;

public class Quest {
    private Stacks Reward;
    private Stacks Request;
    private boolean isDone;

    public Quest(Stacks request, Stacks reward) {
        Reward = reward;
        Request = request;
        this.isDone = false;
    }

    public Stacks getReward() {
        return Reward;
    }

    public Stacks getRequest() {
        return Request;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
