package org.example.server.models;

import com.badlogic.gdx.graphics.Texture;
import org.example.common.models.GameAssetManager;

public interface Item {
    public abstract Integer getPrice();

    default Texture getTexture() {
        return GameAssetManager.getGameAssetManager().getItemTexture(this);
    }

    default String getName() {
//        return this.toString().replaceAll("([A-Z])", " $1").trim();
        return this.toString();
    }
}
