package org.example.common.models;

public record Result(boolean success, String message) {
    @Override
    public String toString() {
        return message;
    }
}
