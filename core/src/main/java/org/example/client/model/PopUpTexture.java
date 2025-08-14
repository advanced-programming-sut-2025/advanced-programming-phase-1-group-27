package org.example.client.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.example.client.Main;

public class PopUpTexture {
    private final Sprite sprite;
    private final float destinationX, destinationY,
            deltaX, deltaY;
    private float originX, originY, time;

    public PopUpTexture(Sprite sprite, float originX, float originY, float destinationX, float destinationY, float time) {
        this.sprite = sprite;
        this.originX = originX;
        this.originY = originY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.time = time;
        deltaX = (destinationX - originX) / time;
        deltaY = (destinationY - originY) / time;
        sprite.setCenter(originX, originY);
    }

    public PopUpTexture(Texture texture, float originX, float originY,
                        float destinationX, float destinationY, float time) {
        this.sprite = new Sprite(texture);
        this.originX = originX;
        this.originY = originY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.time = time;
        deltaX = (destinationX - originX) / time;
        deltaY = (destinationY - originY) / time;
        sprite.setCenter(originX, originY);
    }

    private void updatePosition() {
        originX += deltaX * Gdx.graphics.getDeltaTime();
        originY += deltaY * Gdx.graphics.getDeltaTime();
        System.out.println("updated: " + originX + " " + originY);
        sprite.setCenter(originX, originY);
    }

    public void update() {
        updatePosition();
        time -= Gdx.graphics.getDeltaTime();
    }

    public boolean finished() {
        return time <= 0.01f;
    }

    public void render() {
        if (time <= 1f)
            sprite.setAlpha(time);
        sprite.draw(Main.getBatch());
    }
}
