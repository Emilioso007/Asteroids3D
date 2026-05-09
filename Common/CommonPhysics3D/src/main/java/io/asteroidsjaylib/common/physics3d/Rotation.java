package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Quaternion;

public final class Rotation extends BaseComponent {
    private final Quaternion quaternion;

    public Rotation() {
        this.quaternion = new Quaternion();
    }

    public Quaternion quaternion() {
        return quaternion;
    }

    public Rotation quaternion(Quaternion quaternion) {
        this.quaternion.x = quaternion.x;
        this.quaternion.y = quaternion.y;
        this.quaternion.z = quaternion.z;
        this.quaternion.w = quaternion.w;
        return this;
    }
}
