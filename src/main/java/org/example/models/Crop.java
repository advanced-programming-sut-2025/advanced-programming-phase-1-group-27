package org.example.models;

import org.example.models.enums.items.CropType;

public class Crop implements Item {
    CropType cropType;
    int currentStage, remainingTime;
    //...

    public void harvest() {

    }
}
