package org.example.client.model.enums;

public enum MiniGameDifficulty {

    RETARD(25, 45),
    BADIHI(50, 90),
    OK_I_GUESS(75, 135),
    HARD(100, 180),
    LEGENDARY(150, 270);

    private final int normalDY;
    private final int dartDY;

    MiniGameDifficulty(int normalDY, int dartDY) {
        this.normalDY = normalDY;
        this.dartDY = dartDY;
    }

    public int getNormalDY() {
        return normalDY;
    }

    public int getDartDY() {
        return dartDY;
    }

}
