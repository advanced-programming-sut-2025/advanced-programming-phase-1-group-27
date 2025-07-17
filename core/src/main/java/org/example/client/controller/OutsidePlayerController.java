package org.example.server.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.example.client.Main;
import org.example.client.view.OutsideView;
import org.example.server.models.App;
import org.example.server.models.GameAssetManager;
import org.example.server.models.enums.Menu;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class OutsidePlayerController {
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f;
    private final float speed = 160f;
    private  Sprite characterSprite = GameAssetManager.getGameAssetManager().getCharacterAtlas()
            .createSprite("standing_1");
    private float time = 0f;
    private Camera camera;

    private boolean walking = false;
    private int direction = 0;

    public OutsidePlayerController() {
        characterSprite.setScale(2f);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        x = camera.position.x;
        y = camera.position.y;
    }

    private void updateAnimation(Animation<Sprite> animation) {

        if (animation.isAnimationFinished(time)) {
            time = 0f;
        }

        characterSprite = new Sprite(animation.getKeyFrame(time));
        characterSprite.setScale(2f);

        animation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void update() {
        time += Gdx.graphics.getDeltaTime();

        characterSprite = GameAssetManager.getGameAssetManager().getCharacterAtlas().createSprite("standing_1");
        characterSprite.setScale(2f);

        if (walking) {
            System.out.println("WALKING");
            switch (direction) {
                case 0: {
                    System.out.println("RIGHT");
                    x += speed * Gdx.graphics.getDeltaTime();
                    updateAnimation(GameAssetManager.getGameAssetManager().getWalkRight());
                }
                case 1: {
                    y -= speed * Gdx.graphics.getDeltaTime();
                    updateAnimation(GameAssetManager.getGameAssetManager().getWalkDown());
                }
                case 2: {
                    x -= speed * Gdx.graphics.getDeltaTime();
                    updateAnimation(GameAssetManager.getGameAssetManager().getWalkLeft());
                }
                case 3: {
                    y += speed * Gdx.graphics.getDeltaTime();
                    updateAnimation(GameAssetManager.getGameAssetManager().getWalkUp());
                }
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                walking = true;
                direction = 2;
                time = 0;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                walking = true;
                direction = 0;
                time = 0;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                walking = true;
                direction = 3;
                time = 0;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                walking = true;
                direction = 1;
                time = 0;
            }
        }

        if (time >= 0.25f)
            walking = false;

        characterSprite.setCenter(x, y);
        camera.position.set(x, y, 0);
        characterSprite.draw(Main.getBatch());
    }

    public void render() {

    }


}
