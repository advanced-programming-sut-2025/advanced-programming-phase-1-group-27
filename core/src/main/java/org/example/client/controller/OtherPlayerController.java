package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.client.Main;
import org.example.client.view.GameView;
import org.example.client.view.OutsideView;
import org.example.common.models.Direction;
import org.example.common.models.GameAssetManager;
import org.example.server.controller.GameMenuController;
import org.example.server.models.App;
import org.example.server.models.Result;

import static java.lang.Math.min;

public class OtherPlayerController {
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f,
            lastX, lastY, destX, destY;
    private Animation<TextureRegion> currentAnimation;
    private Sprite characterSprite = new Sprite(GameAssetManager.getGameAssetManager().getStandingSprite());
    private float time = 0f, animationTime = 0f;
    private boolean walking = false;

    private boolean sobhan = false;

    private final String username;

    public OtherPlayerController(String username, int i, int j) {
        this.username = username;
        characterSprite.setScale(2f);
        x = OutsideView.getGraphicalPositionInNPCMap(i, j).getX();
        y = OutsideView.getGraphicalPositionInNPCMap(i, j).getY();
    }


    private void updateAnimation() {

        if (currentAnimation.isAnimationFinished(animationTime)) {
            animationTime = 0f;
        }

        characterSprite.setRegion((currentAnimation.getKeyFrame(animationTime)));
        characterSprite.setScale(2f);

        currentAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void walk(Direction direction) {
        if (!sobhan) {
            sobhan = true;
            return;
        }
        if (walking) {
            x = destX;
            y = destY;
        }
        switch (direction) {
            case Direction.Up:
                lastX = x;
                lastY = y;
                walking = true;
                destX = x;
                destY = y + 40;
                currentAnimation = GameAssetManager.getGameAssetManager().getWalkUp();
                break;
            case Direction.Down:
                lastX = x;
                lastY = y;
                walking = true;
                destX = x;
                destY = y - 40;
                currentAnimation = GameAssetManager.getGameAssetManager().getWalkDown();
                break;
            case Direction.Right:
                lastX = x;
                lastY = y;
                walking = true;
                destX = x + 40;
                destY = y;
                currentAnimation = GameAssetManager.getGameAssetManager().getWalkRight();
                break;
            case Direction.Left:
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

        characterSprite.setRegion(GameAssetManager.getGameAssetManager().getStandingSprite());
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
            updateAnimation();
        }

        if (time >= 0.125f)
            walking = false;

        characterSprite.setCenter(x, y);

        System.out.println("MANAMAMAM " + x + " , " + y);
    }

    public void render() {
        characterSprite.draw(Main.getBatch());
    }

    public String getUsername() {
        return username;
    }
}
