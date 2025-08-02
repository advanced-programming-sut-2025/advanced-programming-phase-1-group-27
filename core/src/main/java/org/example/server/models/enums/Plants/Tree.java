package org.example.server.models.enums.Plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.common.models.GameAssetManager;

public class Tree extends Plant {

    public Tree(PlantType type) {
        super(type);
    }

    public TextureRegion getTexture() {
        return GameAssetManager.getGameAssetManager().getTreeTexture((TreeType) type, currentStage);
    }
}
