package org.example.server.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.HomeView;
import org.example.client.view.OutsideView;
import org.example.server.models.App;
import org.example.common.models.GameAssetManager;
import org.example.server.models.enums.InGameMenuType;
import org.example.server.models.enums.Menu;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HomePlayerController {
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f;
    private final float speed = 3f;
    private Sprite characterSprite = new Sprite(GameAssetManager.getGameAssetManager().getStandingSprite());
    private float time = 0f;
    private final HomeView view;

    public HomePlayerController(HomeView view) {
        characterSprite.setScale(2f);
        this.view = view;
    }

    private void updateAnimation(Animation<TextureRegion> animation) {

        if (animation.isAnimationFinished(time)) {
            time = 0f;
        }

        characterSprite.setRegion(animation.getKeyFrame(time));
        characterSprite.setScale(2f);

        animation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void update() {
        time += Gdx.graphics.getDeltaTime();

        characterSprite.setRegion(GameAssetManager.getGameAssetManager().getStandingSprite());
        characterSprite.setScale(2f);


        if (!view.getHudView().getTextInputField().isVisible() && view.getHudView().getCurrentMenu() ==
        InGameMenuType.NONE) {
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
        }

        x = min(x, Gdx.graphics.getWidth() / 2f + 95);
        x = max(x, Gdx.graphics.getWidth() / 2f - 95);
        y = min(y, Gdx.graphics.getHeight() / 2f + 50);
        y = max(y, Gdx.graphics.getHeight() / 2f - 70);

        if (y == Gdx.graphics.getHeight() / 2f - 70) {
            ClientApp.setCurrentMenu(new OutsideView());
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        }


        characterSprite.setCenter(x, y);

        characterSprite.draw(Main.getBatch());
    }

    public void render() {

    }


}
