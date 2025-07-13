package org.example.server.models.enums;

public enum Questions {
    FatherName("What is your father's name?"),
    Math("2 * 2 = ?"),
    Turk("Turk?"),
    PassMisham("Pass misham?");

    String question;

    Questions(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
