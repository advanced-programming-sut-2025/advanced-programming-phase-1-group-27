package org.example.client.model.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import org.example.common.models.GameAssetManager;

public enum Emoji {

    CRY(new ImageButton(GameAssetManager.getGameAssetManager().getCryEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getCryEmoji() ),
    LOVE(new ImageButton(GameAssetManager.getGameAssetManager().getLoveEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getLoveEmoji() ),
    PASHM(new ImageButton(GameAssetManager.getGameAssetManager().getPashmEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getPashmEmoji()),
    SKULL(new ImageButton(GameAssetManager.getGameAssetManager().getSkullEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getSkullEmoji()),
    SMILE(new ImageButton(GameAssetManager.getGameAssetManager().getSmileEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getSmileEmoji() ),
    GALB(new ImageButton(GameAssetManager.getGameAssetManager().getGalbEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getGalbEmoji() ),
    MUSIC(new ImageButton(GameAssetManager.getGameAssetManager().getMusicEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getMusicEmoji() ),
    BONE(new ImageButton(GameAssetManager.getGameAssetManager().getBoneEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getBoneEmoji()),
    NIMROO(new ImageButton(GameAssetManager.getGameAssetManager().getAmoAbbasEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getAmoAbbasEmoji() ),
    KADO(new ImageButton(GameAssetManager.getGameAssetManager().getKadoEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getKadoEmoji() ),
    CUTE(new ImageButton(GameAssetManager.getGameAssetManager().getAwwwEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getAwwwEmoji() ),
    KING(new ImageButton(GameAssetManager.getGameAssetManager().getKingEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getKingEmoji() ),
    UMBRELLA(new ImageButton(GameAssetManager.getGameAssetManager().getKhisEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getKhisEmoji() ),
    CRAB(new ImageButton(GameAssetManager.getGameAssetManager().getCrabEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getCrabEmoji()  ),
    PANTS(new ImageButton(GameAssetManager.getGameAssetManager().getShalvarEmoji().getDrawable()),
            GameAssetManager.getGameAssetManager().getShalvarEmoji() );


    private final ImageButton emojiButton;
    private final Image emojiImage;

    Emoji(ImageButton emojiButton, Image emojiImage) {
        this.emojiButton = emojiButton;
        this.emojiImage = emojiImage;
    }

    public Image getEmojiImage() {
        return emojiImage;
    }

    public ImageButton getEmojiButton() {
        return emojiButton;
    }
}
