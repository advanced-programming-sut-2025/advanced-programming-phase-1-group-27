package org.example.models.Relations;

public class Relation {
    private int level;
    private int xp;

    public Relation() {
        this.level = 0;
        this.xp = 0;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
