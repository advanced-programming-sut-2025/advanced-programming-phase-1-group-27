package org.example.client.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.example.client.model.enums.Emoji;
import org.example.common.models.GameAssetManager;

public class Reaction {

    private final boolean isEmoji;
    private Image emojiImage;
    private Label textLabel;
    private float reactionTimer = 5f;

    public Reaction(Emoji emoji) {

        this.isEmoji = true;
        this.emojiImage = emoji.getEmojiImage();

    }

    public Reaction(String text) {

        this.isEmoji = false;
        this.textLabel = new Label(text, GameAssetManager.getGameAssetManager().getSkin());

    }

    public void updateReaction(float delta){
        this.reactionTimer -= delta;
        ///  DRAW HERE
    }


}
