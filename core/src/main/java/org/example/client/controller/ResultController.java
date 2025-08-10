package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import org.example.client.Main;
import org.example.common.models.GraphicalResult;
import org.example.server.models.Result;

import java.util.ArrayList;

public class ResultController {
    private final static ArrayList<GraphicalResult> results = new ArrayList<>();
    private static Camera camera;

    public static void setCamera(Camera c) {
        camera = c;
    }

    public static void addError(String string) {
        GraphicalResult error = new GraphicalResult(string, true);
        Vector3 position = new Vector3(Gdx.graphics.getWidth() / 2f - error.getMessage().getWidth() / 2f,
                50, 0);
        if (camera != null)
            camera.unproject(position);
        error.setPosition(position.x, position.y);
        results.add(error);

    }

    public static void addSuccess(String string) {
        GraphicalResult error = new GraphicalResult(string, false);
        Vector3 position = new Vector3(Gdx.graphics.getWidth() / 2f - error.getMessage().getWidth() / 2f,
                50, 0);
        if (camera != null)
            camera.unproject(position);
        error.setPosition(position.x, position.y);
        results.add(error);

    }

    public static void addResult(Result result) {

        GraphicalResult error = new GraphicalResult(result.message(), !result.success());
        Vector3 position = new Vector3(Gdx.graphics.getWidth() / 2f - error.getMessage().getWidth() / 2f,
                50, 0);
        if (camera != null)
            camera.unproject(position);
        error.setPosition(position.x, position.y);
        results.add(error);
    }

    public static void render() {
        for (GraphicalResult result : results) {
            result.update(Gdx.graphics.getDeltaTime());

            Vector3 position = new Vector3(Gdx.graphics.getWidth() / 2f - result.getMessage().getWidth() / 2f,
                    50, 0);
            if (camera != null)
                camera.unproject(position);
            result.setPosition(position.x, position.y);
            result.getMessage().draw(Main.getBatch(), 1);
        }

        results.removeIf(GraphicalResult::finished);
    }
}
