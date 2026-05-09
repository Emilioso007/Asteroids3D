package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector3D;

public final class Acceleration extends BaseComponent {
    private final Vector3D vector;

    public Acceleration() {
        this.vector = new Vector3D();
    }

    public Vector3D vector() {
        return vector;
    }

    public Acceleration vector(Vector3D vector) {
        this.vector.x = vector.x;
        this.vector.y = vector.y;
        this.vector.z = vector.z;
        return this;
    }

    public Acceleration vector(float x, float y, float z) {
        this.vector.x = x;
        this.vector.y = y;
        this.vector.z = z;
        return this;
    }
}
