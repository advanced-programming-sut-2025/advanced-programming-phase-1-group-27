package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import org.example.client.Main;
import org.example.client.model.GameAssetManager;
import org.example.client.view.OutsideView;
import org.example.common.models.AnimalProperty.Animal;
import org.example.common.models.Cell;

import java.awt.*;
import java.util.Random;

public class AnimalController {
    private final Animal animal;
    private final OutsideView outsideView;
    private final Camera camera;
    private final Sprite sprite;
    private int initI, initJ, i, j;
    private float x, y, destX, destY, time = 0f, animationTime = 0f;
    private boolean flag = true, walking = false;
    private Animation<Texture> currentAnimation;

    public AnimalController(Animal animal, OutsideView outsideView, Camera camera) {
        this.animal = animal;
        this.outsideView = outsideView;
        this.camera = camera;
        sprite = new Sprite(GameAssetManager.getGameAssetManager().getAnimalTexture(animal.getType()));
    }

    public void updateAnimation() {
        if (currentAnimation.isAnimationFinished(animationTime))
            animationTime = 0f;

        sprite.setRegion(currentAnimation.getKeyFrame(animationTime));
        if (destX < x)
            sprite.setFlip(true, false);

    }

    public void updatePosition() {
        float modifier = time * 2.5f;
        sprite.setCenter(
                destX * modifier + x * (1 - modifier),
                destY * modifier + y * (1 - modifier)
        );
    }

    public void handleClicks() {
        float x = sprite.getX(), y = sprite.getY();
        Rectangle rectangle = new Rectangle((int) x, (int) y, (int) sprite.getWidth(), (int) sprite.getHeight());

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        if (Gdx.input.justTouched() && rectangle.contains(touchPos.x, touchPos.y)) {
            outsideView.getHudView().setAnimal(animal);
        }
    }

    public void update() {
        if (flag && animal.isOut()) {
            initI = animal.getCurrentCell().getPosition().getX();
            initJ = animal.getCurrentCell().getPosition().getY();
            i = initI;
            j = initJ;
            x = OutsideView.getGraphicalPositionInFarmMap(initI, initJ).getX();
            y = OutsideView.getGraphicalPositionInFarmMap(initI, initJ).getY();
            flag = false;
        } else if (!animal.isOut()) {
            flag = true;
            return;
        }
        handleClicks();


        if (walking) {
            time += Gdx.graphics.getDeltaTime();
            animationTime += Gdx.graphics.getDeltaTime();
            updateAnimation();
            updatePosition();

            if (time >= 0.4f) {
                walking = false;
                x = destX;
                y = destY;

            }
        } else {
            int random = new Random().nextInt(60);
            if (random != 1)
                return;
            random = new Random().nextInt(4);
            Cell dest;
            switch (random) {
                case 0:
                    dest = animal.getCurrentCell().getMap().getCell(i, j + 1);
                    if (dest != null && dest.isPassable() && dest.getObject() == null
                            && Math.abs(i - initI) + Math.abs(j + 1 - initJ) <= 2) {
                        j++;

                        walking = true;
                        time = 0;
                        animationTime = 0;
                        destX = x + 40;
                        destY = y;
                        currentAnimation = GameAssetManager.getGameAssetManager().getAnimalRightAnimation(animal.getType());
                        animal.getCurrentCell().setObject(null);
                        dest.setObject(animal);
                        animal.setCurrentCell(dest);
                    }
                    break;
                case 1:
                    dest = animal.getCurrentCell().getMap().getCell(i + 1, j);
                    if (dest != null && dest.isPassable() && dest.getObject() == null
                            && Math.abs(i + 1 - initI) + Math.abs(j - initJ) <= 2) {
                        i++;

                        walking = true;
                        time = 0;
                        animationTime = 0;
                        destX = x;
                        destY = y - 40;
                        currentAnimation = GameAssetManager.getGameAssetManager().getAnimalDownAnimation(animal.getType());
                        animal.getCurrentCell().setObject(null);
                        dest.setObject(animal);
                        animal.setCurrentCell(dest);
                    }
                    break;
                case 2:
                    dest = animal.getCurrentCell().getMap().getCell(i, j - 1);
                    if (dest != null && dest.isPassable() && dest.getObject() == null
                            && Math.abs(i - initI) + Math.abs(j - 1 - initJ) <= 2) {
                        j--;

                        walking = true;
                        time = 0;
                        animationTime = 0;
                        destX = x - 40;
                        destY = y;
                        currentAnimation = GameAssetManager.getGameAssetManager().getAnimalRightAnimation(animal.getType());
                        animal.getCurrentCell().setObject(null);
                        dest.setObject(animal);
                        animal.setCurrentCell(dest);
                    }
                    break;
                case 3:
                    dest = animal.getCurrentCell().getMap().getCell(i - 1, j);
                    if (dest != null && dest.isPassable() && dest.getObject() == null
                            && Math.abs(i - 1 - initI) + Math.abs(j - initJ) <= 2) {
                        i--;

                        walking = true;
                        time = 0;
                        animationTime = 0;
                        destX = x;
                        destY = y + 40;
                        currentAnimation = GameAssetManager.getGameAssetManager().getAnimalUpAnimation(animal.getType());
                        animal.getCurrentCell().setObject(null);
                        dest.setObject(animal);
                        animal.setCurrentCell(dest);
                    }
                    break;
            }
        }
    }

    public void render() {
        if (animal.isOut())
            sprite.draw(Main.getBatch());
    }

    public boolean sold() {
        return !animal.getEnclosure().getAnimals().contains(animal);
    }
}
