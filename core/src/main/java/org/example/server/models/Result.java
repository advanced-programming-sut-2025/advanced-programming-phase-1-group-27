package org.example.server.models;

public record Result(boolean success, String message) {
    @Override
    public String toString() {
        return message;
    }
}
