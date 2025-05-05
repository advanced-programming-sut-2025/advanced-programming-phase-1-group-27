package org.example.models;

public class Poll {
    private int numberOfParticipants;
    private int votes, opposes;

    public Poll(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
        this.votes = 0;
        this.opposes = 0;
    }

    public void vote() {
        ++votes;
    }

    public void oppose() {
        ++opposes;
    }

    public boolean isPollComplete() {
        return votes + opposes == numberOfParticipants;
    }

    public boolean requestIsAccepted() {
        return votes == numberOfParticipants;
    }
}
