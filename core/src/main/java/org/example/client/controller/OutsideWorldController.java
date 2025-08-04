package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.client.Main;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OutsideWorldController {

    private final OutsideView view;

    private final Animation<Texture> thorAnimationFrames = GameAssetManager.getGameAssetManager().getThorAnimationFrames();
    private HashMap<Position, Float> thors = new HashMap<>();

    public OutsideWorldController(OutsideView view) {
        this.view = view;
    }

    public void update(float delta) {

        Iterator<Map.Entry<Position,Float>> iterator = thors.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Position, Float> entry = iterator.next();
            entry.setValue(entry.getValue() - delta);
            if (entry.getValue() < 0) {
                iterator.remove();
            }
        }

        drawThor();

    }

    private void drawThor() {

        for ( Position position : thors.keySet() ) {

            Main.getBatch().draw(

                    thorAnimationFrames.getKeyFrame(thors.get(position)),
                    position.getX() - thorAnimationFrames.getKeyFrame(thors.get(position)).getWidth()/2f ,
                    position.getY()

            );

        }

    }

    public void setThor(float thorTimer, int i, int j) {
        this.thors.put(OutsideView.getGraphicalPosition(i,j), thorTimer);
    }

}
