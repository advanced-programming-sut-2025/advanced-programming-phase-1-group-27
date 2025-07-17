package org.example.server.models.enums;

public enum Gender {
    Male,
    Female;

    public static Gender getGender(String gender) {
        return switch (gender) {
            case "Male" -> Male;
            case "Female" -> Female;
            default -> null;
        };
    }
}
