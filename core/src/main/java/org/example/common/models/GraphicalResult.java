package org.example.common.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.GameAssetManager;

import java.util.HashMap;

public class GraphicalResult {
    private Label message;
    private float displayTime;
    private boolean isError = true;

    public GraphicalResult() {
        this.message = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        this.message.setVisible(false);
        this.message.setFontScale(1f);
    }

    public GraphicalResult(String message) {
        this.message = new Label(message, GameAssetManager.getGameAssetManager().getSkin());
        this.message.setVisible(true);
        this.message.setFontScale(1f);
        this.displayTime = 3f;
        this.isError = true;
    }

    public GraphicalResult(String message, boolean isError) {
        this(message);
        this.isError = isError;
    }

    public GraphicalResult(String message, Color color) {
        this(message);
        this.message.setColor(color);
    }

    public GraphicalResult(String message, Color color, boolean isError) {
        this(message, color);
        this.isError = isError;
    }

    // builds GraphicalResult from the map which was sent by server/client
    public GraphicalResult(LinkedTreeMap<String, Object> data) {
        isError = (boolean) data.get("isError");
        message = new Label((String) data.get("message text"), GameAssetManager.getGameAssetManager().getSkin());
        message.setColor(isError? GameAssetManager.getGameAssetManager().getErrorColor() : GameAssetManager.getGameAssetManager().getAcceptColor());
        message.setFontScale(1f);
        message.setVisible((boolean) data.get("visibility"));
        displayTime = ((Number) data.get("displayTime")).floatValue();
    }

    public void set(GraphicalResult graphicalResult) {
        this.message.setText(graphicalResult.message.getText());
        this.message.setVisible(true);
        this.message.setColor(graphicalResult.message.getColor());
        this.displayTime = graphicalResult.displayTime;
    }

    public void update(float delta) {
        if (displayTime > 0f) {
            displayTime -= delta;
            if (displayTime < 1f)
                message.setColor(message.getColor().r, message.getColor().g, message.getColor().b, displayTime);
            if (displayTime <= 0f)
                message.setVisible(false);
        }
    }

    public Label getMessage() {
        return message;
    }

    public void setPosition(float x, float y) {
        message.setPosition(x, y);
    }

    public void setColor(Color color) {
        message.setColor(color);
    }

    public void setError(boolean error) {
        isError = error;
    }

    public boolean hasError() {
        return isError;
    }

    public void setDisplayTime(float displayTime) {
        this.displayTime = displayTime;
    }

    public float getDisplayTime() {
        return displayTime;
    }

    // in order to send object's data via network
    public static HashMap<String, Object> getInfo(String message) {
        return new HashMap<>() {{
            put("message text", message);
            put("displayTime", 3f);
            put("isError", true);
            put("visibility", true); // this function can be overloaded in order to have optional visibility
        }};
    }

    public static HashMap<String, Object> getInfo(String message, boolean isError) {
        HashMap<String, Object> info = getInfo(message);
        info.put("isError", isError);
        return info;
    }

    public static HashMap<String, Object> getInfo(String message, float displayTime) {
        HashMap<String, Object> info = getInfo(message);
        info.put("displayTime", displayTime);
        return info;
    }
}
