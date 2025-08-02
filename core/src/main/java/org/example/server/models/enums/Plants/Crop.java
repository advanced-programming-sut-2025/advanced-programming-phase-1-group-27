package org.example.server.models.enums.Plants;

import com.badlogic.gdx.graphics.Texture;
import org.example.common.models.GameAssetManager;

public class Crop extends Plant {

    public Crop(PlantType type) {
        super(type);
    }

    public Texture getTexture() {
        return GameAssetManager.getGameAssetManager().getCropTexture((CropType) type, currentStage);
    }
}
