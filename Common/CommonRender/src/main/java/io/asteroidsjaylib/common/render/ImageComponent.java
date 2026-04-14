package io.asteroidsjaylib.common.render;

import com.raylib.Raylib;

import java.io.IOException;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class ImageComponent extends RenderComponent {

    public Texture texture;

    /// Create an image component from a path with a width and height.
    /// @param path the path to the image as seen from the caller class.
    /// @param width the width of the image used in-game.
    /// @param height the height of the image used in-game.
    public ImageComponent(String path, int width, int height){
        try {

            Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

            // getting the resource as if you were in the caller class
            var is = caller.getResourceAsStream("/" + path);

            if(is == null) throw new RuntimeException("Could not find " + path + " in " + caller.getName());

            byte[] data = is.readAllBytes();
            is.close();

            // extracting the file extension
            String extension = path.substring(path.lastIndexOf(".")).toLowerCase();

            Image img = LoadImageFromMemory(extension, data, data.length);

            // if width and height are set (non-negative), resize the image to reduce footprint in vram later.
            if (width > 0 && height > 0) {
                ImageResizeNN(img, width, height);
            }

            texture = LoadTextureFromImage(img);

            UnloadImage(img);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Vector2 position, float angle) {

        float xOffset = switch (horizontalAlign){
            case LEFT -> 0;
            case CENTER -> (float) -texture.width() / 2;
            case RIGHT -> -texture.width();
            default -> 0;
        };

        float yOffset = switch (verticalAlign){
            case TOP -> 0;
            case CENTER -> (float) -texture.height() / 2;
            case BOTTOM -> -texture.height();
            default -> 0;
        };

        rlPushMatrix();
        // Rotate around the entity position, then place sprite relative to that pivot.
        rlTranslatef(position.x(), position.y(), 0);
        rlRotatef(angle, 0 ,0, 1);
        Raylib.DrawTexture(texture, Math.round(xOffset), Math.round(yOffset), WHITE);
        rlPopMatrix();
    }
}
