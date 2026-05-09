package io.asteroidsjaylib.common.physics3d;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.util.Vector3D;

public final class Velocity extends BaseComponent {
    private final Vector3D vector;
    private float terminal;

    public Velocity() {
        this.vector = new Vector3D();
        this.terminal = Float.POSITIVE_INFINITY;
    }

    public Vector3D vector() {
        return this.vector;
    }

    public Velocity vector(Vector3D vector) {
        this.vector.x = vector.x;
        this.vector.y = vector.y;
        this.vector.z = vector.z;
        return this;
    }

    public Velocity vector(float x, float y, float z) {
        this.vector.x = x;
        this.vector.y = y;
        this.vector.z = z;
        return this;
    }

    public float terminal() {
        return this.terminal;
    }

    public Velocity terminal(float terminal) {
        this.terminal = terminal;
        return this;
    }
}
