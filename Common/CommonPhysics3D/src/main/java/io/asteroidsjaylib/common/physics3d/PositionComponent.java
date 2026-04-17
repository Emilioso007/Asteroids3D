package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector3D;

public class PositionComponent extends BaseComponent {
    public Vector3D pos;

    public PositionComponent(Vector3D pos){
        this.pos = pos;
    }
}
