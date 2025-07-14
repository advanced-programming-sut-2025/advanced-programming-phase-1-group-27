package org.example.server.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.server.models.utils.MusicPlayer;
import org.example.server.models.utils.Track;


public class GameAssetManager {


    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private final Texture background = new Texture(Gdx.files.internal("Images/menu_background.png"));
    private final Texture StardewValleyText = new Texture(Gdx.files.internal("Images/stardew_valley_text.png"));

    private final MusicPlayer musicPlayer = new MusicPlayer();

    private final Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/click.wav"));

    private final Color errorColor = new Color(1, 0.31f, 0, 1);
    private final Color acceptColor = new Color(0.216f, 0.831f, 0.255f, 1);

    private final Texture map1 = new Texture(Gdx.files.internal("MapImage/avatar7.png"));
    private final Texture map2 = new Texture(Gdx.files.internal("MapImage/avatar10.png"));
    private final Texture map3 = new Texture(Gdx.files.internal("MapImage/avatar11.png"));
    private final Texture map4 = new Texture(Gdx.files.internal("MapImage/avatar15.png"));

    private final Texture rainyFall = new Texture(Gdx.files.internal("Clock/RainyFall.png"));
    private final Texture stormyFall = new Texture(Gdx.files.internal("Clock/StormyFall.png"));
    private final Texture sunnyFall = new Texture(Gdx.files.internal("Clock/SunnyFall.png"));
    private final Texture snowyFall = new Texture(Gdx.files.internal("Clock/SnowyFall.png"));
    private final Texture rainyWinter = new Texture(Gdx.files.internal("Clock/RainyWinter.png"));
    private final Texture stormyWinter = new Texture(Gdx.files.internal("Clock/StormyWinter.png"));
    private final Texture sunnyWinter = new Texture(Gdx.files.internal("Clock/SunnyWinter.png"));
    private final Texture snowyWinter = new Texture(Gdx.files.internal("Clock/SnowyWinter.png"));
    private final Texture rainySpring = new Texture(Gdx.files.internal("Clock/RainySpring.png"));
    private final Texture stormySpring = new Texture(Gdx.files.internal("Clock/StormySpring.png"));
    private final Texture sunnySpring = new Texture(Gdx.files.internal("Clock/SunnySpring.png"));
    private final Texture snowySpring = new Texture(Gdx.files.internal("Clock/SnowySpring.png"));
    private final Texture rainySummer = new Texture(Gdx.files.internal("Clock/RainySummer.png"));
    private final Texture stormySummer = new Texture(Gdx.files.internal("Clock/StormySummer.png"));
    private final Texture sunnySummer = new Texture(Gdx.files.internal("Clock/SunnySummer.png"));
    private final Texture snowySummer = new Texture(Gdx.files.internal("Clock/SnowySummer.png"));

    private final TextureAtlas characterAtlas = new TextureAtlas("assets/Character/character.atlas");

    private final Animation<Sprite> walkDown = new Animation<>(
            0.1f,
            characterAtlas.createSprite("down_1"),
            characterAtlas.createSprite("down_2"),
            characterAtlas.createSprite("down_3"),
            characterAtlas.createSprite("down_4")
    );
    private final Animation<Sprite> walkRight = new Animation<>(
            0.1f,
            characterAtlas.createSprite("right_1"),
            characterAtlas.createSprite("right_2"),
            characterAtlas.createSprite("right_3"),
            characterAtlas.createSprite("right_4")
    );
    private final Animation<Sprite> walkUp = new Animation<>(
            0.1f,
            characterAtlas.createSprite("up_1"),
            characterAtlas.createSprite("up_2"),
            characterAtlas.createSprite("up_3"),
            characterAtlas.createSprite("up_4")
    );
    private final Animation<Sprite> walkLeft = new Animation<>(
            0.1f,
            characterAtlas.createSprite("left_1"),
            characterAtlas.createSprite("left_2"),
            characterAtlas.createSprite("left_3"),
            characterAtlas.createSprite("left_4")
    );

    private final Texture arrowTexture = new  Texture(Gdx.files.internal("Clock/Arrow.png"));

    {
        musicPlayer.setCurrentTrack(Track.THEME_1);
    }

    public static GameAssetManager getGameAssetManager() {

        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;

    }

    public Texture getRainyFall() {
        return rainyFall;
    }

    public Texture getStormyFall() {
        return stormyFall;
    }

    public Texture getSunnyFall() {
        return sunnyFall;
    }

    public Texture getSnowyFall() {
        return snowyFall;
    }

    public Texture getRainyWinter() {
        return rainyWinter;
    }

    public Texture getStormyWinter() {
        return stormyWinter;
    }

    public Texture getSunnyWinter() {
        return sunnyWinter;
    }

    public Texture getSnowyWinter() {
        return snowyWinter;
    }

    public Texture getRainySpring() {
        return rainySpring;
    }

    public Texture getStormySpring() {
        return stormySpring;
    }

    public Texture getSunnySpring() {
        return sunnySpring;
    }

    public Texture getSnowySpring() {
        return snowySpring;
    }

    public Texture getRainySummer() {
        return rainySummer;
    }

    public Texture getStormySummer() {
        return stormySummer;
    }

    public Texture getSunnySummer() {
        return sunnySummer;
    }

    public Texture getSnowySummer() {
        return snowySummer;
    }

    public Texture getArrowTexture() {
        return arrowTexture;
    }

    public Texture getMap1() {
        return map1;
    }

    public Texture getMap2() {
        return map2;
    }

    public Texture getMap3() {
        return map3;
    }

    public Texture getMap4() {
        return map4;
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getMenuBackground() {
        return background;
    }

    public Image getStardewValleyText() {
        return new Image(StardewValleyText);
    }

    public Sound getClickSound() {
        return clickSound;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public Color getAcceptColor() {
        return acceptColor;
    }

    public TextureAtlas getCharacterAtlas() {
        return characterAtlas;
    }

    public Animation<Sprite> getWalkDown() {
        return walkDown;
    }

    public Animation<Sprite> getWalkRight() {
        return walkRight;
    }

    public Animation<Sprite> getWalkUp() {
        return walkUp;
    }

    public Animation<Sprite> getWalkLeft() {
        return walkLeft;
    }
}
