package org.example.common.models;

public enum Direction {
    Up,
    Down,
    Left,
    Right;

    public static Direction getDirection(String directionName) {
        for (Direction direction : Direction.values()) {
            if (direction.name().equalsIgnoreCase(directionName))
                return direction;
        }
        return null;
    }
}
