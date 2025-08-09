package org.example.client.model.enums;

import com.badlogic.gdx.graphics.Texture;
import org.example.common.models.GameAssetManager;

public enum Emoji {

    CRY(GameAssetManager.getGameAssetManager().getCryEmoji()),
    LOVE(GameAssetManager.getGameAssetManager().getLoveEmoji()),
    PASHM(GameAssetManager.getGameAssetManager().getPashmEmoji()),
    SKULL(GameAssetManager.getGameAssetManager().getSkullEmoji()),
    SMILE(GameAssetManager.getGameAssetManager().getSmileEmoji());


    private final Texture emojiTexture;

    Emoji(Texture emoji) {
        this.emojiTexture = emoji;
    }

    public Texture getEmojiTexture() {
        return emojiTexture;
    }
}
