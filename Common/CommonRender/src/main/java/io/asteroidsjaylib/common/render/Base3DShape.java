package io.asteroidsjaylib.common.render;

import com.raylib.BoundingBox;
import io.asteroidsjaylib.common.util.Vector3D;

public abstract class Base3DShape {
    public Vector3D offset;
    public abstract BoundingBox boundingBox(float x, float y, float z);
    public abstract void draw(float distanceToCameraSquared);
}
