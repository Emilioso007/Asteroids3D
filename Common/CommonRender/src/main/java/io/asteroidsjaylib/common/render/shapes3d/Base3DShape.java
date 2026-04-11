package io.asteroidsjaylib.common.render.shapes3d;

import io.asteroidsjaylib.common.util.Vector3D;

public abstract class Base3DShape {
    public Vector3D offset;
    public abstract void draw();
}
