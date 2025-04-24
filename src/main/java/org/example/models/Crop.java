package org.example.models;

import org.example.models.enums.items.CropType;
import org.example.models.enums.items.Item;

public class Crop implements Item {
    CropType cropType;
    int currentStage, remainingTime;
    //...

    public void harvest() {

    }
}
