package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.example.client.Main;
import org.example.client.view.GameView;
import org.example.client.view.OutsideView;
import org.example.common.models.GraphicalResult;
import org.example.server.controller.GameMenuController;
import org.example.server.models.App;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Result;
import org.example.server.models.enums.InGameMenuType;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class OutsidePlayerController {
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f,
    lastX, lastY, destX, destY;
    private Animation<Sprite> currentAnimation;
    private final float speed = 160f;
    private  Sprite characterSprite = GameAssetManager.getGameAssetManager().getStandingSprite();
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

        characterSprite = GameAssetManager.getGameAssetManager().getStandingSprite();
        characterSprite.setScale(2f);

        if (!walking && !view.getHudView().getTextInputField().isVisible() && view.getHudView().getCurrentMenu() ==
        InGameMenuType.NONE) {
            x = OutsideView.getGraphicalPosition(App.getCurrentGame().getCurrentPlayer().getPosition()).getX();
            y = OutsideView.getGraphicalPosition(App.getCurrentGame().getCurrentPlayer().getPosition()).getY();
            lastX = x;
            lastY = y;

            time = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x - 40, y).getX() + "",
                        OutsideView.getIndices(x - 40, y).getY() + "");
                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x - 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkLeft();
                }
                //TODO send walk(clientApp.getGame().getPlayer().getUsername, Left) rassa in 4 ta:
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x + 40, y).getX() + "",
                        OutsideView.getIndices(x + 40, y).getY() + "");
                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x + 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkRight();
                }
                //TODO send walk(clientApp.getGame().getPlayer().getUsername, Right)
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x, y + 40).getX() + "",
                        OutsideView.getIndices(x, y + 40).getY() + "");
                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x;
                    destY = y + 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkUp();
                }
                //TODO send walk(clientApp.getGame().getPlayer().getUsername, Up)
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                Result result = new GameMenuController(new GameView()).walk(
                        OutsideView.getIndices(x, y - 40).getX() + "",
                        OutsideView.getIndices(x, y - 40).getY() + "");
                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x;
                    destY = y - 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkDown();
                }
                //TODO send walk(clientApp.getGame().getPlayer().getUsername, Down)
            }
        }

        time += Gdx.graphics.getDeltaTime();


        if (walking) {
            //System.out.println("WALKING");
            float modif = min(1f, time * 8f);
            x = modif * (destX - lastX) + lastX;
            y = modif * (destY - lastY) + lastY;
            updateAnimation(currentAnimation);
        }

        if (time >= 0.125f)
            walking = false;

        characterSprite.setCenter(x, y);
        camera.position.set(x, y, 0);
        characterSprite.draw(Main.getBatch());
    }

    public void render() {

    }
}
