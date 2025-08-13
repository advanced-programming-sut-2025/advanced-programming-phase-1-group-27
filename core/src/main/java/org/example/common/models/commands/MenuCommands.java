package org.example.common.models.commands;

import java.util.regex.Matcher;

public interface MenuCommands {
    public Matcher getMatcher(String input);
}
