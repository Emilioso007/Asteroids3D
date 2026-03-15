package io.asteroidsjaylib.rendercommon;

import com.raylib.Raylib.Vector2;
import io.asteroidsjaylib.common.ecs.BaseComponent;

import static io.asteroidsjaylib.rendercommon.RenderAlign.LEFT;
import static io.asteroidsjaylib.rendercommon.RenderAlign.TOP;

public abstract class RenderComponent extends BaseComponent {
    public RenderAlign horizontalAlign = LEFT;
    public RenderAlign verticalAlign = TOP;

    public abstract void draw(Vector2 position, float angle);
}
