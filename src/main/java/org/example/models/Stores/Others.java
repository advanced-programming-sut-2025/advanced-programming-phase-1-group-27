package org.example.models.Stores;

import org.example.models.NPCs.NPC;

import java.util.ArrayList;
import java.util.Stack;

public class Others extends Store{
    private ArrayList<Stack> products;

    public Others(NPC manager, int start, int end, ArrayList<Stack> products) {
        super(manager, start, end);
        this.products = products;
    }
}
