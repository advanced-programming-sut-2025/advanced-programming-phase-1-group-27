package org.example.models;

import org.example.enums.CropType;
import org.example.enums.Item;

public class Crop implements Item {
    CropType cropType;
    int currentStage, remainingTime;
    //...

    public void harvest() {

    }
}
