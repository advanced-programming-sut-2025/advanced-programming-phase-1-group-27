package org.example.enums.commands;

public enum ToolCommands {
    ToolsEquip("\\s*tools\\s+equip\\s+(?<toolName.+)\\s*"),
    ToolsShowCurrent("\\s*tools\\s+show\\s+current\\s*"),
    ToolsShowAvailable("\\s*tools\\s+show\\s+available\\s*"),
    ToolsUpgrade("\\s*tools\\s+upgrade\\s+(?<toolName).+)\\s*"),
    ToolsUse("\\s*tools\\s+use\\+-d\\s+(?<direction>.+)\\s*");

    private final String pattern;

    ToolCommands(String pattern) {
        this.pattern = pattern;
    }


}
