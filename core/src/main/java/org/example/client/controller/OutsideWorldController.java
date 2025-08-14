package org.example.client.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import org.example.client.Main;
import org.example.client.model.GameAssetManager;
import org.example.client.view.OutsideView;
import org.example.common.models.Position;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OutsideWorldController {

    private final OutsideView view;

    private final Animation<Texture> thorAnimationFrames = GameAssetManager.getGameAssetManager().getThorAnimationFrames();
    private final Animation<Texture> crowAnimationFrames = GameAssetManager.getGameAssetManager().getCrowAnimationFrames();
    private HashMap<Position, Float> thors = new HashMap<>();
    private HashMap<Position, Float> crows = new HashMap<>();

    public OutsideWorldController(OutsideView view) {
        this.view = view;
    }

    public void update(float delta) {

        Iterator<Map.Entry<Position, Float>> iteratorThor = thors.entrySet().iterator();
        while (iteratorThor.hasNext()) {
            Map.Entry<Position, Float> entry = iteratorThor.next();
            entry.setValue(entry.getValue() - delta);
            if (entry.getValue() < 0) {
                iteratorThor.remove();
            }
        }

        iteratorThor = crows.entrySet().iterator();
        while (iteratorThor.hasNext()) {
            Map.Entry<Position, Float> entry = iteratorThor.next();
            entry.setValue(entry.getValue() - delta);
            if (entry.getValue() < 0) {
                iteratorThor.remove();
            }
        }

//        Iterator<Map.Entry<Position,Float>> iteratorCrow = crows.entrySet().iterator();
//        while (iteratorCrow.hasNext()) {
//            Map.Entry<Position, Float> entry = iteratorCrow.next();
//            entry.setValue(entry.getValue() - delta);
//            if (entry.getValue() < 0) {
//                iteratorCrow.remove();
//            }
//        }

        drawThor();
        drawCrow();

    }

    private void drawThor() {

        for (Position position : thors.keySet()) {

            Main.getBatch().draw(

                    thorAnimationFrames.getKeyFrame(thors.get(position)),
                    position.getX() - thorAnimationFrames.getKeyFrame(thors.get(position)).getWidth() / 2f,
                    position.getY()

            );

        }

    }

    private void drawCrow() {

        for (Position position : crows.keySet()) {

            Main.getBatch().draw(

                    crowAnimationFrames.getKeyFrame(crows.get(position)),
                    position.getX() - crowAnimationFrames.getKeyFrame(crows.get(position)).getWidth() / 2f,
                    position.getY() - 200

            );

        }

    }

    public void setThor(float thorTimer, int i, int j) {
        this.thors.put(OutsideView.getGraphicalPosition(i, j), thorTimer);
    }

    public void setCrowAttack(float crowTimer, int i, int j) {
        this.crows.put(OutsideView.getGraphicalPosition(i, j), crowTimer);
    }

}
