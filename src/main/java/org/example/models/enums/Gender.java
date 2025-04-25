package org.example.models.enums;

public enum Gender {
    Male,
    Female;

    public static Gender getGender(String gender) {
        return switch (gender) {
            case "male" -> Male;
            case "female" -> Female;
            default -> null;
        };
    }
}
