package org.example.client.model;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class RoundedRectangleTexture {
    public static Texture createRoundedRectangle(int width, int height, int cornerRadius, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);

        pixmap.fillRectangle(cornerRadius, 0, width - 2 * cornerRadius, height);
        pixmap.fillRectangle(0, cornerRadius, width, height - 2 * cornerRadius);

        pixmap.fillCircle(cornerRadius, cornerRadius, cornerRadius);
        pixmap.fillCircle(width - cornerRadius - 1, cornerRadius, cornerRadius);
        pixmap.fillCircle(cornerRadius, height - cornerRadius - 1, cornerRadius);
        pixmap.fillCircle(width - cornerRadius - 1, height - cornerRadius - 1, cornerRadius);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    public static Texture createCreamRoundedRect(int width, int height, int cornerRadius) {
        Color cream = new Color(255 / 255f, 253 / 255f, 208 / 255f, 0.8f);
        return createRoundedRectangle(width, height, cornerRadius, cream);
    }

    public static Texture createBrownRoundedRect(int width, int height, int cornerRadius) {
        Color brown = new Color(210/255f, 180/255f, 140/255f, 0.8f);
        return createRoundedRectangle(width, height, cornerRadius, brown);
    }
}

