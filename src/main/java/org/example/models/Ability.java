package org.example.models;

public class Ability {
    private int xp = 0, level = 0;

    public void addXp() {
        this.xp += xp;
        if (this.level < 4 && xp >= this.level * 100 + 150) {
            this.levelUp();
        }
    }

    private void levelUp() {
        this.xp -= this.level * 100 + 150;
        this.level++;
        // TODO: adding new recipes to user's HashMap
    }

    public int getLevel(){
        return level;
    }
}
