package io.asteroidsjaylib.rendercommon;

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
        Raylib.DrawText(text, (int) position.x(), (int) position.y(), fontSize, color);
    }
}
