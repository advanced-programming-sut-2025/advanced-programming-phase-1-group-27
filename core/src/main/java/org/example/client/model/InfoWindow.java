package org.example.client.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class InfoWindow {
    private BitmapFont font;
    private Color color;
    private boolean wrap = false, finished = false;
    private int alignment = Align.left, width;
    private String text;

    private float x, y;
    private float time = 4f, fontScale = 1f;

    private final GlyphLayout glyphLayout = new GlyphLayout();

    public InfoWindow(BitmapFont font, String text, Color color, int width, int alignment, boolean wrap) {
        this.font = new BitmapFont(font.getData(), (TextureRegion) null, false);
        this.color = color;
        this.width = width;
        this.alignment = alignment;
        this.wrap = wrap;
        this.text = text;
        this.glyphLayout.setText(font, text, color, width, alignment, wrap);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setMaxTime(float time) {
        this.time = time;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFontScale(float scale) {
        font.getData().setScale(scale);
        glyphLayout.setText(font, text, color, width, alignment, wrap);
        fontScale = scale;
        font.getData().setScale(1);
    }

    public void draw(Batch batch) {
        time -= Gdx.graphics.getDeltaTime();
        font.getData().setScale(fontScale);

        if (time <= 0f) {
            finished = true;
        } else if (time <= 1f) {
            batch.setColor(1, 1, 1, time);
            batch.draw(GameAssetManager.getGameAssetManager().getNpcDialogueBox(),
                    x,
                    y,
                    glyphLayout.width + 30,
                    glyphLayout.height + 30);

            glyphLayout.setText(font, text,
                    new Color(color.r, color.g, color.b, time),
                    width, alignment, wrap);

            font.draw(batch, glyphLayout, x + 15, y + glyphLayout.height + 15);
            batch.setColor(Color.WHITE);
        } else {
            batch.draw(GameAssetManager.getGameAssetManager().getNpcDialogueBox(),
                    x,
                    y,
                    glyphLayout.width + 30,
                    glyphLayout.height + 30);

            font.draw(batch, glyphLayout, x + 15, y + glyphLayout.height + 15);
        }

        font.getData().setScale(1);
    }
}
