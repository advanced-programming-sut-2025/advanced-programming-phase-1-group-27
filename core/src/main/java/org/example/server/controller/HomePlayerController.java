package org.example.server.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.example.client.Main;
import org.example.client.view.GameView;
import org.example.client.view.HomeView;
import org.example.server.models.GameAssetManager;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HomePlayerController {
    private final HomeView homeView;
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f;
    private final float speed = 3f;
    private  Sprite characterSprite = GameAssetManager.getGameAssetManager().getCharacterAtlas()
            .createSprite("standing_1");
    private float time = 0f;

    public HomePlayerController(HomeView homeView) {
        this.homeView = homeView;
        characterSprite.setScale(2f);
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

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed;
            updateAnimation(GameAssetManager.getGameAssetManager().getWalkLeft());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed;
            updateAnimation(GameAssetManager.getGameAssetManager().getWalkRight());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed;
            updateAnimation(GameAssetManager.getGameAssetManager().getWalkUp());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed;
            updateAnimation(GameAssetManager.getGameAssetManager().getWalkDown());
        }

        x = min(x, Gdx.graphics.getWidth() / 2f + 95);
        x = max(x, Gdx.graphics.getWidth() / 2f - 95);
        y = min(y, Gdx.graphics.getHeight() / 2f + 50);
        y = max(y, Gdx.graphics.getHeight() / 2f - 70);


        characterSprite.setCenter(x, y);

        characterSprite.draw(Main.getBatch());
    }

    public void render() {

    }


}
