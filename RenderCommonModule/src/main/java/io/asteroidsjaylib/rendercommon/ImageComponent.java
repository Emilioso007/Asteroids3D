package io.asteroidsjaylib.rendercommon;

import com.raylib.Colors;
import com.raylib.Raylib;
import com.raylib.Raylib.Texture;

import java.io.IOException;

import static com.raylib.Raylib.*;

public class ImageComponent extends RenderComponent {

    private Texture image;
    public float scale;

    public ImageComponent(String path, Class<?> callerClass){
        this(path, 1, callerClass);
    }
    public ImageComponent(String path, float scale, Class<?> callerClass){

        try {

            Class<?> loaderClass = (callerClass != null) ? callerClass : getClass();
            var is = loaderClass.getResourceAsStream("/" + path);
            if (is == null) throw new RuntimeException("Could not find " + path + "!");

            byte[] data = is.readAllBytes();

            Image img = LoadImageFromMemory(".png", data, data.length);

            image = LoadTextureFromImage(img);

            UnloadImage(img);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.scale = scale;

    }

    @Override
    public void draw(Vector2 position, float angle) {
        Raylib.DrawTextureEx(image, position, angle, scale, Colors.WHITE);
    }
}
