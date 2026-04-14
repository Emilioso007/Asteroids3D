package io.asteroidsjaylib.common.render;

import com.raylib.Raylib;

import java.io.IOException;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class AnimationImageComponent extends RenderComponent{
    public Texture texture;
    private final float frameWidth;
    private final float frameHeight;
    private final int frameCount;
    public int frameIndex = 0;

    /// Create an image component from a path with a width and height.
    /// @param path the path to the image as seen from the caller class.
    /// @param frameWidth the width of a singular frame used in-game.
    /// @param frameHeight the height of a singular frame used in-game.
    /// @param frameCount the amount of frames in the spritesheet.
    public AnimationImageComponent(String path, int frameWidth, int frameHeight, int frameCount){

        if (frameWidth <= 0 || frameHeight <= 0 || frameCount <= 0) {
            throw new IllegalArgumentException("frameWidth, frameHeight and frameCount must be positive");
        }

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;

        try {

            Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

            // getting the resource as if you were in the caller class
            var is = caller.getResourceAsStream("/" + path);

            if(is == null) throw new RuntimeException("Could not find " + path + " in " + caller.getName());

            byte[] data = is.readAllBytes();
            is.close();

            // extracting the file extension
            String extension = path.substring(path.lastIndexOf(".")).toLowerCase();

            Raylib.Image img = LoadImageFromMemory(extension, data, data.length);

            ImageResizeNN(img, frameWidth*frameCount, frameHeight);

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
            case CENTER -> -frameWidth / 2;
            case RIGHT -> -frameWidth;
            default -> 0;
        };

        float yOffset = switch (verticalAlign){
            case TOP -> 0;
            case CENTER -> -frameHeight / 2;
            case BOTTOM -> -frameHeight;
            default -> 0;
        };

        int safeFrameIndex = Math.floorMod(frameIndex, frameCount);
        Rectangle rec = new Rectangle().x(safeFrameIndex * frameWidth).y(0).width(frameWidth).height(frameHeight);

        rlPushMatrix();
        // Rotate around the entity position, then place sprite relative to that pivot.
        rlTranslatef(position.x(), position.y(), 0);
        rlRotatef(angle, 0 ,0, 1);

        Vector2 offset = new Vector2().x(Math.round(xOffset)).y(Math.round(yOffset));
        DrawTextureRec(texture, rec, offset, WHITE);

        rlPopMatrix();
    }
}
