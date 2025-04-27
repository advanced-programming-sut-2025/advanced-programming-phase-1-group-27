package org.example.models.NPCs;

import org.example.models.Time;
import org.example.models.enums.items.Item;

import java.util.ArrayList;

public class Quest {
    private ArrayList<Item> Reward;
    private ArrayList<Item> Request;
    private Time start;
    private boolean condition;
}
