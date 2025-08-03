package org.example.client.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Player;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Tool;

import java.util.Spliterator;

public class ToolGraphicalController {
    private float x, y;
    private final OutsideView view;
    private Camera camera;

    private final Sprite toolSprite = new Sprite(GameAssetManager.getGameAssetManager().getItemTexture(
            ToolType.BasicPickaxe
    ));

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
        if (player.getBackpack().get(player.getCurrentInventorySlotIndex()).getItem() instanceof ToolType toolType) {
            toolSprite.setRegion(GameAssetManager.getGameAssetManager().getItemTexture(toolType));
            toolSprite.setScale(0.6f);
            toolSprite.setCenter(x + 15, y - 16);

            System.out.println(toolType.getName());

        }
    }

    public void render() {
        if (toolSprite.getTexture() != null)
            toolSprite.draw(Main.getBatch());
    }
}
