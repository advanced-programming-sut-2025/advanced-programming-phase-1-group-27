package org.example.models.enums.commands;

import java.util.regex.Matcher;

public interface MenuCommands {
    public Matcher getMatcher(String input);
}
