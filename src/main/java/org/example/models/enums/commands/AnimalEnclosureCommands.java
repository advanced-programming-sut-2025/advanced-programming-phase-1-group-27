package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AnimalEnclosureCommands {
    PetAnimal("pet\\s+-n\\s+(?<name>.+)"),
    ShowAnimalDetail("animals"),
    ShepherdAnimal("shepherd\\s+animals\\s+-n\\s+(?<name>.+)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)"),
    Feed("feed\\s+hay\\s+-n\\s+(?<name>.+)"),
    ShowProduces("produces"),
    CollectProduces("collect\\s+produce\\s+-n\\s+(?<name>.+)"),
    SellAnimal("sell\\s+animal\\s+-n\\s+(?<name>.+)");

    private final String pattern;

    AnimalEnclosureCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
