package io.asteroidsjaylib.common.render.shapes3d;

import com.raylib.Raylib;

import static com.raylib.Raylib.DrawPoint3D;

public class Point3D extends Base3DShape{

    public Raylib.Color color;

    public Point3D(Raylib.Color color) {
        this.color = color;
    }

    @Override
    public void draw() {
        DrawPoint3D(new Raylib.Vector3(), color);
    }
}
