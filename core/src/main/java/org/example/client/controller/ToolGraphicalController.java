package org.example.client.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Player;
import org.example.server.models.tools.Tool;

import java.util.Spliterator;

public class ToolGraphicalController {
    private float x, y;
    private final OutsideView view;
    private Camera camera;

    private final Sprite toolSprite = new Sprite();

    private Player player = ClientApp.getCurrentGame().getCurrentPlayer();

    public ToolGraphicalController(OutsideView view) {
        this.view = view;
    }

    public void setCamera(Camera camera) {
        x = camera.position.x;
        y = camera.position.y;
        this.camera = camera;
    }

    public void update() {
        x = camera.position.x;
        y = camera.position.y;
        if (player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem() instanceof Tool tool) {
            toolSprite.setTexture(GameAssetManager.getGameAssetManager().getItemTexture(tool.getToolType()));
            toolSprite.setCenter(x, y);
            System.out.println("ASFDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDdd");
        }
    }

    public void render() {
        if (toolSprite.getTexture() != null)
            toolSprite.draw(Main.getBatch());
    }
}
