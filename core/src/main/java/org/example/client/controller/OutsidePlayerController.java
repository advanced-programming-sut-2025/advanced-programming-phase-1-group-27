package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.GameView;
import org.example.client.view.OutsideView;
import org.example.common.models.Direction;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.controller.GameMenuController;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Result;
import org.example.server.models.enums.InGameMenuType;

import java.util.HashMap;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class OutsidePlayerController {
    private float x = Gdx.graphics.getWidth() / 2f, y = Gdx.graphics.getHeight() / 2f,
    lastX, lastY, destX, destY;
    private Animation<TextureRegion> currentAnimation;
    private final float speed = 160f;
    private Sprite characterSprite = new Sprite(GameAssetManager.getGameAssetManager().getStandingSprite());
    private float time = 0f, animationTime = 0f;
    private Camera camera;
    private final GameMenuController gameMenuController = new GameMenuController(new GameView());

    private boolean walking = false;
    private Direction direction = null;
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

    private void updateAnimation() {

        if (currentAnimation.isAnimationFinished(animationTime)) {
            animationTime = 0f;
        }

        characterSprite.setRegion(currentAnimation.getKeyFrame(animationTime));
        characterSprite.setScale(2f);

        currentAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void update() {
        animationTime += Gdx.graphics.getDeltaTime();

        characterSprite.setRegion(GameAssetManager.getGameAssetManager().getStandingSprite());
        characterSprite.setScale(2f);

        if (!walking /*&& !view.getHudView().getTextInputField().isVisible() && view.getHudView().getCurrentMenu() ==
        InGameMenuType.NONE*/) {
            x = OutsideView.getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getX();
            y = OutsideView.getGraphicalPosition(ClientApp.getCurrentGame().getCurrentPlayer().getPosition()).getY();
            lastX = x;
            lastY = y;

            time = 0;
            direction = null;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                Result result = gameMenuController.walk(
                        OutsideView.getIndices(x - 40, y).getX() + "",
                        OutsideView.getIndices(x - 40, y).getY() + "");
//                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x - 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkLeft();
                }
                direction = Direction.Left;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                Result result = gameMenuController.walk(
                        OutsideView.getIndices(x + 40, y).getX() + "",
                        OutsideView.getIndices(x + 40, y).getY() + "");
//                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x + 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkRight();
                }
                direction = Direction.Right;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                Result result = gameMenuController.walk(
                        OutsideView.getIndices(x, y + 40).getX() + "",
                        OutsideView.getIndices(x, y + 40).getY() + "");
//                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x;
                    destY = y + 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkUp();
                }
                direction = Direction.Up;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                Result result = gameMenuController.walk(
                        OutsideView.getIndices(x, y - 40).getX() + "",
                        OutsideView.getIndices(x, y - 40).getY() + "");
//                System.out.println(result.message());
                if (result.success()) {
                    walking = true;
                    destX = x;
                    destY = y - 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getWalkDown();
                }
                direction = Direction.Down;
            }
            if (direction != null) {
                System.out.println("username: " + ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
                System.out.println("direction: " + direction.name());
                ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                    put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                    put("username", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
                    put("direction", direction.name());
                }}, Message.Type.walk_update));
            }
        }

        time += Gdx.graphics.getDeltaTime();


        if (walking) {
            float modif = min(1f, time * 8f);
            x = modif * (destX - lastX) + lastX;
            y = modif * (destY - lastY) + lastY;
            updateAnimation();
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
