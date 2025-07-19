package org.example.server.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.example.client.Main;
import org.example.client.view.GameView;
import org.example.client.view.OutsideView;
import org.example.server.models.App;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Result;
import org.example.server.models.enums.Menu;

import java.io.File;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class OutsidePlayerController {
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f,
    lastX, lastY, destX, destY;
    private Animation<Sprite> currentAnimation;
    private final float speed = 160f;
    private  Sprite characterSprite = GameAssetManager.getGameAssetManager().getCharacterAtlas()
            .createSprite("standing_1");
    private float time = 0f, animationTime = 0f;
    private Camera camera;

    private boolean walking = false;
    private int direction = 0;
    private final OutsideView view;

    public OutsidePlayerController(OutsideView view) {
        characterSprite.setScale(2f);
        this.view = view;
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

        if (animation.isAnimationFinished(animationTime)) {
            animationTime = 0f;
        }

        characterSprite = new Sprite(animation.getKeyFrame(animationTime));
        characterSprite.setScale(2f);

        animation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void update() {
        animationTime += Gdx.graphics.getDeltaTime();

        characterSprite = GameAssetManager.getGameAssetManager().getCharacterAtlas().createSprite("standing_1");
        characterSprite.setScale(2f);

        if (!walking && !view.getHudView().isInputFieldVisible()) {
            lastX = x;
            lastY = y;
            time = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x - 40, y).getX() + "",
                        OutsideView.getIndices(x - 40, y).getY() + "");
                if (result.success()) {
                    walking = true;
                    destX = x - 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkLeft();
                }
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x + 40, y).getX() + "",
                        OutsideView.getIndices(x + 40, y).getY() + "");
                if (result.success()) {
                    walking = true;
                    destX = x + 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkRight();
                }
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x, y + 40).getX() + "",
                        OutsideView.getIndices(x, y + 40).getY() + "");
                if (result.success()) {
                    walking = true;
                    destX = x;
                    destY = y + 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkUp();
                }
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x, y - 40).getX() + "",
                        OutsideView.getIndices(x, y - 40).getY() + "");
                if (result.success()) {
                    walking = true;
                    destX = x;
                    destY = y - 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkDown();
                }
            }
        }

        time += Gdx.graphics.getDeltaTime();


        if (walking) {
            //System.out.println("WALKING");
            float modif = min(1f, time * 4f);
            x = modif * (destX - lastX) + lastX;
            y = modif * (destY - lastY) + lastY;
            updateAnimation(currentAnimation);
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
