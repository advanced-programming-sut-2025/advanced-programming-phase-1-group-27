package org.example.common.models.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ToolCommands {
    ToolsEquip("\\s*tools\\s+equip\\s+(?<toolName>.+)\\s*"),
    ToolsShowCurrent("\\s*tools\\s+show\\s+current\\s*"),
    ToolsShowAvailable("\\s*tools\\s+show\\s+available\\s*"),
    ToolsUpgrade("\\s*tools\\s+upgrade\\s+(?<toolName>.+)\\s*"),
    ToolsUse("\\s*tools\\s+use\\s+-d\\s+(?<direction>[0-7])\\s*");

    private final String pattern;

    ToolCommands(String pattern) {
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
