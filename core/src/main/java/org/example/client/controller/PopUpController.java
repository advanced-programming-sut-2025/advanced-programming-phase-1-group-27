package org.example.client.controller;

import org.example.client.model.popUpTexture;

import java.util.ArrayList;

public class PopUpController {
    private static ArrayList<popUpTexture> popUps = new ArrayList<>();


    public static void addPopUp(popUpTexture popUp) {
        popUps.add(popUp);
    }

    public static void render() {
        for (popUpTexture popUp : popUps) {
            popUp.update();
            popUp.render();
        }
        popUps.removeIf(popUpTexture::finished);
    }
}
