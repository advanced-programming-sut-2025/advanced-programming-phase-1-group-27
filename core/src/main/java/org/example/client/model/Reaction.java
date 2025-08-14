package org.example.client.model;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.enums.Emoji;

import java.util.HashMap;

public class Reaction {

    private final boolean isEmoji;
    private Emoji emoji = null;
    private String text = null;
    private float reactionTimer = 5f;

    public Reaction(Emoji emoji) {
        this.isEmoji = true;
        this.emoji = emoji;
    }

    public Reaction(String text) {
        this.isEmoji = false;
        this.text = text;
    }

    public Reaction(LinkedTreeMap<String, Object> info) {
        this.isEmoji = (boolean) info.get("isEmoji");
        if (isEmoji)
            this.emoji = Emoji.getEmoji((String) info.get("emoji"));
        else
            this.text = (String) info.get("text");
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("isEmoji", isEmoji);
        info.put("emoji", emoji == null ? null : emoji.name());
        info.put("text", text);
        return info;
    }

    public boolean isEmoji() {
        return isEmoji;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    public String getText() {
        return text;
    }

    public float getReactionTimer() {
        return reactionTimer;
    }

    public void updateReaction(float delta) {
        this.reactionTimer -= delta;
        ///  DRAW HERE
    }


}
