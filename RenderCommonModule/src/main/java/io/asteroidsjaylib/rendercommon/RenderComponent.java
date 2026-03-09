package io.asteroidsjaylib.rendercommon;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.ecs.BaseComponent;

public abstract class RenderComponent extends BaseComponent {
    public double xoffset;
    public double yoffset;
    public abstract void draw(Raylib.Vector2 position, float angle);
}
