package io.asteroidsjaylib.common.physics;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector2D;

public class PositionComponent extends BaseComponent {
    public Vector2D pos;

    public PositionComponent(Vector2D pos){
        this.pos = pos;
    }
}
