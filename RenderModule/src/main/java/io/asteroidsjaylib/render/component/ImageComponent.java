package io.asteroidsjaylib.render.component;

import com.raylib.Raylib;
import com.raylib.Raylib.Texture;
import io.asteroidsjaylib.render.RenderComponent;

import java.io.IOException;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class ImageComponent extends RenderComponent {

    private Texture image;
    public float scale;

    public ImageComponent(String path){
        this(path, 1);
    }
    public ImageComponent(String path, float scale){
        try {
            // 1. Load from the classpath (works inside JARs and IDEs)
            var is = getClass().getResourceAsStream("/" + path);
            if (is == null) throw new RuntimeException("Could not find stars.png!");

            byte[] data = is.readAllBytes();

            // 2. Load into an Image (RAM) first
            // Note: ".png" tells Raylib how to decode the byte array
            Raylib.Image img = LoadImageFromMemory(".png", data, data.length);

            // 3. Convert to Texture (VRAM)
            image = LoadTextureFromImage(img);

            // 4. Clean up the RAM copy
            UnloadImage(img);

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.scale = scale;
    }

    @Override
    public void draw(Vector2 position, float angle) {
        DrawTextureEx(image, position, angle, scale, WHITE);
    }
}
