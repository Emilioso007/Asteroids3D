package io.asteroidsjaylib.common.render.shapes3d;

import com.raylib.Raylib.Color;
import io.asteroidsjaylib.common.ecs.BaseComponent;

public class Cube3DComponent extends BaseComponent {
    public float width, height, length;
    public Color color, wireColor;

    public Cube3DComponent(float width, float height, float length, Color color, Color wireColor) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.color = color;
        this.wireColor = wireColor;
    }

}
