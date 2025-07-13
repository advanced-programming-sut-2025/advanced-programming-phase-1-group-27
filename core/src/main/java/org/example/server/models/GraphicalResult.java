package org.example.server.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GraphicalResult {
    private Label message;
    private float displayTime;
    private boolean isError = true;

    public GraphicalResult() {
        this.message = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        this.message.setVisible(false);
        this.message.setFontScale(1f);
    }

    public GraphicalResult(String message, Color color) {
        this.message = new Label(message, GameAssetManager.getGameAssetManager().getSkin());
        this.message.setColor(color);
        this.message.setVisible(true);
        this.message.setFontScale(1f);
        this.displayTime = 3f;
        this.isError = true;
    }

    public GraphicalResult(String message, Color color, boolean isError) {
        this(message, color);
        this.isError = isError;
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
}
