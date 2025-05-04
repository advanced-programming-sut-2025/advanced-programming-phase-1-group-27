package org.example.models.tools;

import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class Scythe extends Tool{

    public Scythe() {
        StackLevel level = ToolType.Scythe.getLevel();
        int energyUsage = 2;
        super(level , energyUsage , ToolType.Scythe.getName());
    }

    public void use() {

    }
}
