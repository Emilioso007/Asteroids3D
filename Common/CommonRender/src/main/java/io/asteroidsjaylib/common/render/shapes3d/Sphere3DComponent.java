package io.asteroidsjaylib.common.render.shapes3d;

import com.raylib.Raylib.Color;
import io.asteroidsjaylib.common.ecs.BaseComponent;

public class Sphere3DComponent extends BaseComponent {
    public float radius;
    public Color color, wireColor;

    public Sphere3DComponent(float radius, Color color, Color wireColor) {
        this.radius = radius;
        this.color = color;
        this.wireColor = wireColor;
    }

}
