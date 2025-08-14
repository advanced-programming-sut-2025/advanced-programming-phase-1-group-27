package org.example.client.controller;

import com.badlogic.gdx.graphics.Camera;
import org.example.client.Main;
import org.example.client.model.InfoWindow;
import org.example.client.model.PopUpTexture;

import java.util.ArrayList;

public class PopUpController {
    private static ArrayList<PopUpTexture> popUps = new ArrayList<>();
    private static ArrayList<InfoWindow> infoWindows = new ArrayList<>();
    private static Camera camera;


    public static void addPopUp(PopUpTexture popUp) {
        popUps.add(popUp);
    }

    public static void addInfoWindow(InfoWindow infoWindow) {
        infoWindows.add(infoWindow);
    }

    public static void renderPopUps() {
        for (PopUpTexture popUp : popUps) {
            popUp.update();
            popUp.render();
        }
        popUps.removeIf(PopUpTexture::finished);
    }

    public static void renderInfoWindows() {
        for (InfoWindow infoWindow : infoWindows) {
            infoWindow.draw(Main.getBatch());
        }
    }

    public void setcamera(Camera c) {
        camera = c;
    }
}
