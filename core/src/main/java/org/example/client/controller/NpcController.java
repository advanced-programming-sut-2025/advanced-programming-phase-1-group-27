package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.model.ClientApp;
import org.example.client.view.InteractionMenus.NpcMenuView;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.InfoWindow;
import org.example.server.models.Cell;
import org.example.server.models.NPCs.NPC;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class NpcController {
    private final NPC npc;
    private final int[] leftBound, rightBound, upperBound, lowerBound;
    private final Cell homeStartCell, wonderStartCell;
    private final Float walkTime = 0.4f;
    private final Sprite sprite;

    private Camera camera;
    private Animation<Texture> currentAnimation;

    private final ArrayList<InfoWindow> infoWindows = new ArrayList<>();

    private float x, y, destX, destY, time = 0f, animationTime = 0f;
    private int i, j, phase = -1;
    private boolean walking = false;

    public NpcController(NPC npc) {
        this.npc = npc;

        int i = npc.getHome().getTopLeftCell().getPosition().getX();
        int j = npc.getHome().getTopLeftCell().getPosition().getY();
        float upperLeftCellX = OutsideView.getGraphicalPositionInNPCMap(i, j).getX(),
                upperLeftCellY = OutsideView.getGraphicalPositionInNPCMap(i, j).getY();
        leftBound = new int[]{j, j};
        rightBound = new int[]{j + 3, j + 3};
        upperBound = new int[]{i + 4, i - 4};
        lowerBound = new int[]{i + 7, i - 1};

        homeStartCell = ClientApp.getCurrentGame().getNpcMap().getCell(i + 4, j + 1);
        wonderStartCell = ClientApp.getCurrentGame().getNpcMap().getCell(i - 2, j + 1);
//        x = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
//                npc.getCurrentCell().getPosition().getY()).getX() + 20;
//        y = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
//                npc.getCurrentCell().getPosition().getY()).getY() + 40;


        sprite = new Sprite(GameAssetManager.getGameAssetManager().getNpcWalkDownAnimation(npc.getType()).
                getKeyFrames()[0]);
        //sprite.setScale(2.5f);
        sprite.setSize(40, 80);
        sprite.setCenter(x, y);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private void startWalkingRandomly() {
        int random = new Random().nextInt(60);
        if (random != 1)
            return;
        random = new Random().nextInt(4);
        Cell dest;
        switch (random) {
            case 0:
                dest = ClientApp.getCurrentGame().getNpcMap().getCell(i, j + 1);
                if (dest != null && dest.isPassable() && dest.getObject() == null
                        && j + 1 <= rightBound[phase]) {
                    j++;

                    walking = true;
                    time = 0;
                    animationTime = 0;
                    destX = x + 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getNpcWalkRightAnimation(npc.getType());
                    npc.getCurrentCell().setObject(null);
                    dest.setObject(npc);
                    npc.setCurrentCell(dest);
                }
                break;
            case 1:
                dest = ClientApp.getCurrentGame().getNpcMap().getCell(i + 1, j);
                if (dest != null && dest.isPassable() && dest.getObject() == null
                        && i + 1 <= lowerBound[phase]) {
                    i++;

                    walking = true;
                    time = 0;
                    animationTime = 0;
                    destX = x;
                    destY = y - 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getNpcWalkDownAnimation(npc.getType());
                    npc.getCurrentCell().setObject(null);
                    dest.setObject(npc);
                    npc.setCurrentCell(dest);
                }
                break;
            case 2:
                dest = ClientApp.getCurrentGame().getNpcMap().getCell(i, j - 1);
                if (dest != null && dest.isPassable() && dest.getObject() == null
                && j - 1 >= leftBound[phase]) {
                    j--;

                    walking = true;
                    time = 0;
                    animationTime = 0;
                    destX = x - 40;
                    destY = y;
                    currentAnimation = GameAssetManager.getGameAssetManager().getNpcWalkLeftAnimation(npc.getType());
                    npc.getCurrentCell().setObject(null);
                    dest.setObject(npc);
                    npc.setCurrentCell(dest);
                }
                break;
            case 3:
                dest = ClientApp.getCurrentGame().getNpcMap().getCell(i - 1, j);
                if (dest != null && dest.isPassable() && dest.getObject() == null &&
                i - 1 >= upperBound[phase]) {
                    i--;

                    walking = true;
                    time = 0;
                    animationTime = 0;
                    destX = x;
                    destY = y + 40;
                    currentAnimation = GameAssetManager.getGameAssetManager().getNpcWalkUpAnimation(npc.getType());
                    npc.getCurrentCell().setObject(null);
                    dest.setObject(npc);
                    npc.setCurrentCell(dest);
                }
                break;
    }
}

    private void updateAnimation() {

        if (currentAnimation == null)
            return;

        if (currentAnimation.isAnimationFinished(animationTime)) {
            animationTime = 0f;
        }

        sprite.setRegion(currentAnimation.getKeyFrame(animationTime));
    }

    private void updatePosition() {
        float modif = time * 2.5f;
        sprite.setCenter(
                modif * destX + (1 - modif) * x,
                modif * destY + (1 - modif) * y);
    }

    private void handleClicks() {
        Rectangle npcBody = new Rectangle((int) sprite.getX(), (int) sprite.getY(),
                (int) sprite.getWidth(), (int) sprite.getHeight());

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        if (Gdx.input.justTouched() && npcBody.contains(touchPos.x,
                touchPos.y)) {

            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new NpcMenuView(npc.getName()));
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && npcBody.contains(touchPos.x,
                touchPos.y)) {
            //right click on npc
        }

        Rectangle signBody = new Rectangle((int) sprite.getX() + 40, (int) sprite.getY() + 76,
                32, 32);
        if (Gdx.input.justTouched() && signBody.contains(touchPos.x,
                touchPos.y)) {
            if (npc.getDialogue() != null) {
                npc.setDialogue(npc.getDialogue().replace('—', ' '));
                npc.setDialogue(npc.getDialogue().replace('’', '\''));
                InfoWindow infoWindow = new InfoWindow(
                        GameAssetManager.getGameAssetManager().getSkin().getFont("font"),
                        npc.getDialogue(),
                        Color.BLACK,
                        200,
                        Align.left,
                        true
                );
                infoWindow.setPosition(x + 40, y + 76);
                infoWindow.setFontScale(0.7f);
                infoWindow.setMaxTime(6.5f);
                infoWindows.add(infoWindow);


                npc.setDialogue(null);

                InteractionsWithNPCController controller = new InteractionsWithNPCController();
                controller.meetNPC(npc.getName());
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && signBody.contains(touchPos.x,
                touchPos.y)) {
            //right click on sign
        }
    }

    public void update() {
        if (!walking) {
            int clock = ClientApp.getCurrentGame().getTime().getHour();
            if (clock >= npc.getType().getStartDayTime() && phase == -1) {
                if (npc.getCurrentCell() != null)
                    npc.getCurrentCell().setObject(null);
                npc.setCurrentCell(homeStartCell);
                homeStartCell.setObject(npc);
                phase = 0;
                x = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
                        npc.getCurrentCell().getPosition().getY()).getX();
                y = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
                        npc.getCurrentCell().getPosition().getY()).getY() + 10;
                sprite.setCenter(x, y);
                this.i = npc.getCurrentCell().getPosition().getX();
                this.j = npc.getCurrentCell().getPosition().getY();
            }
            if (clock >= npc.getType().getWonderOffTime() && phase == 0) {
                if (npc.getCurrentCell() != null)
                    npc.getCurrentCell().setObject(null);
                npc.setCurrentCell(wonderStartCell);
                wonderStartCell.setObject(npc);
                phase = 1;
                x = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
                        npc.getCurrentCell().getPosition().getY()).getX();
                y = OutsideView.getGraphicalPositionInNPCMap(npc.getCurrentCell().getPosition().getX(),
                        npc.getCurrentCell().getPosition().getY()).getY() + 10;
                sprite.setCenter(x, y);
                this.i = npc.getCurrentCell().getPosition().getX();
                this.j = npc.getCurrentCell().getPosition().getY();
            }
            if (clock < npc.getType().getStartDayTime()) {
                phase = -1;
                if (npc.getCurrentCell() != null)
                    npc.getCurrentCell().setObject(null);
                npc.setCurrentCell(null);
            }
        }

        time += Gdx.graphics.getDeltaTime();
        animationTime += Gdx.graphics.getDeltaTime();



        if (walking) {
            updateAnimation();
            updatePosition();
            if (time >= 0.4f) {
                walking = false;
                x = destX;
                y = destY;
            }
        } else {
            if (phase >= 0)
                startWalkingRandomly();
        }

        handleClicks();

    }

    public void render() {
        if (phase >= 0) {
            sprite.draw(Main.getBatch());
            if (npc.getDialogue() != null) {
                Main.getBatch().draw(
                        GameAssetManager.getGameAssetManager().getNPCDialogueSign(),
                        sprite.getX() + 40, sprite.getY() + 76, 32, 32
                );
            }
        }
        for (InfoWindow infoWindow : infoWindows) {
            infoWindow.setPosition(sprite.getX() + 40, sprite.getY() + 76);
            infoWindow.draw(Main.getBatch());
        }
        infoWindows.removeIf(InfoWindow::isFinished);
    }
}
