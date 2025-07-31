package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.example.client.Main;
import org.example.client.view.GameView;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.server.controller.GameMenuController;
import org.example.server.models.App;
import org.example.server.models.Result;

import static java.lang.Math.min;

public class OtherPlayerController {
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f,
            lastX, lastY, destX, destY;
    private Animation<Sprite> currentAnimation;
    private  Sprite characterSprite = GameAssetManager.getGameAssetManager().getStandingSprite();
    private float time = 0f, animationTime = 0f;
    private boolean walking = false;

    private final String username;

    public OtherPlayerController(String username, int i, int j) {
        this.username = username;
        characterSprite.setScale(2f);
        x = OutsideView.getGraphicalPosition(i, j).getX();
        y = OutsideView.getGraphicalPosition(i, j).getY();
    }


    private void updateAnimation(Animation<Sprite> animation) {

        if (animation.isAnimationFinished(animationTime)) {
            animationTime = 0f;
        }

        characterSprite = new Sprite(animation.getKeyFrame(animationTime));
        characterSprite.setScale(2f);

        animation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void walk(String direction) {
        switch (direction) {
            case "Up":
                lastX = x;
                lastY = y;
                walking = true;
                destX = x;
                destY = y + 40;
                currentAnimation = GameAssetManager.getGameAssetManager().getWalkUp();
                break;
            case "Down":
                lastX = x;
                lastY = y;
                walking = true;
                destX = x;
                destY = y - 40;
                currentAnimation = GameAssetManager.getGameAssetManager().getWalkDown();
                break;
            case "Right":
                lastX = x;
                lastY = y;
                walking = true;
                destX = x + 40;
                destY = y;
                currentAnimation = GameAssetManager.getGameAssetManager().getWalkRight();
                break;
            case "Left":
                lastX = x;
                lastY = y;
                walking = true;
                destX = x - 40;
                destY = y;
                currentAnimation = GameAssetManager.getGameAssetManager().getWalkLeft();
                break;
        }
    }

    public void update() {
        animationTime += Gdx.graphics.getDeltaTime();

        characterSprite = GameAssetManager.getGameAssetManager().getStandingSprite();
        characterSprite.setScale(2f);

        time += Gdx.graphics.getDeltaTime();

        if (!walking) {
            time = 0;
        }

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
        characterSprite.draw(Main.getBatch());
    }

    public String getUsername() {
        return username;
    }
}
