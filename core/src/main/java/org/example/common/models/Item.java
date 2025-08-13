package org.example.common.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.example.client.model.GameAssetManager;

public interface Item {
    public abstract Integer getPrice();

    default Texture getTexture() {
        return GameAssetManager.getGameAssetManager().getItemTexture(this);
    }

    default Image getItemImage() {
        return GameAssetManager.getGameAssetManager().getItemImage(this);
    }

    default boolean isEdible() {
        if (this instanceof Edible edible)
            return edible.isEdible();
        return false;
    }

    default String getName() {
//        return this.toString().replaceAll("([A-Z])", " $1").trim();
        return this.toString();
    }
}
