package io.asteroidsjaylib.render;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.ecs.BaseComponent;

public abstract class RenderComponent extends BaseComponent {
    public abstract void draw(Raylib.Vector2 position, float angle);
}
