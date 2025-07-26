package org.example.server.models.enums;


import com.badlogic.gdx.graphics.Texture;
import org.example.common.models.GameAssetManager;


public enum ClockType {

    RainyFall(GameAssetManager.getGameAssetManager().getRainyFall()),
    SnowyFall(GameAssetManager.getGameAssetManager().getSnowyFall()),
    SunnyFall(GameAssetManager.getGameAssetManager().getSunnyFall()),
    StormyFall(GameAssetManager.getGameAssetManager().getStormyFall()),
    RainySpring(GameAssetManager.getGameAssetManager().getRainySpring()),
    SnowySpring(GameAssetManager.getGameAssetManager().getSnowySpring()),
    SunnySpring(GameAssetManager.getGameAssetManager().getSunnySpring()),
    StormySpring(GameAssetManager.getGameAssetManager().getStormySpring()),
    RainySummer(GameAssetManager.getGameAssetManager().getRainySummer()),
    SnowySummer(GameAssetManager.getGameAssetManager().getSnowySummer()),
    SunnySummer(GameAssetManager.getGameAssetManager().getSunnySummer()),
    StormySummer(GameAssetManager.getGameAssetManager().getStormySummer()),
    RainyWinter(GameAssetManager.getGameAssetManager().getRainyWinter()),
    SnowyWinter(GameAssetManager.getGameAssetManager().getSnowyWinter()),
    SunnyWinter(GameAssetManager.getGameAssetManager().getSunnyWinter()),
    StormyWinter(GameAssetManager.getGameAssetManager().getStormyWinter());

    private final Texture texture;

    ClockType(Texture texture){
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
