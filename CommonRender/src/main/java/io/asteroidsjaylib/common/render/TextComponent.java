package io.asteroidsjaylib.common.render;

import com.raylib.Raylib;

import static com.raylib.Raylib.*;

public class TextComponent extends RenderComponent {

    public String text;
    public int fontSize;
    public Color color;

    public TextComponent(String text, int fontSize, Color color){
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
    }

    @Override
    public void draw(Raylib.Vector2 position, float angle) {

        float xOffset = switch (horizontalAlign){
            case LEFT -> 0;
            case CENTER -> (float) -MeasureText(text, fontSize) /2;
            case RIGHT -> -MeasureText(text, fontSize);
            default -> 0;
        };

        float yOffset = switch (verticalAlign){
            case TOP -> 0;
            case CENTER -> (float) -fontSize /2;
            case BOTTOM -> -fontSize;
            default -> 0;
        };

        Raylib.DrawText(text, (int) (position.x() + xOffset), (int) (position.y() + yOffset), fontSize, color);
    }
}
