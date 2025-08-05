package org.example.server.models.Relations;

import org.example.server.models.Item;
import org.example.server.models.Player;
import org.example.server.models.Stacks;
import org.example.server.models.enums.DialogueType;

import java.util.ArrayList;

public class Trade{

    private static int idCounter = 1;
    private String starter , other;
    private ArrayList<Stacks> starterSelected , othersSelected ;
    private int id;

    public Trade(String starter, String other, ArrayList<Stacks> starterSelected, ArrayList<Stacks> othersSelected) {
        this.starter = starter;
        this.other = other;
        this.starterSelected = starterSelected;
        this.othersSelected = othersSelected;
        this.id = idCounter;
        idCounter++;
    }

    public void getInfo(){

    }
}
