package org.example.models;

import org.example.enums.items.CropType;
import org.example.enums.items.Item;

public class Crop implements Item {
    CropType cropType;
    int currentStage, remainingTime;
    //...

    public void harvest() {

    }
}
