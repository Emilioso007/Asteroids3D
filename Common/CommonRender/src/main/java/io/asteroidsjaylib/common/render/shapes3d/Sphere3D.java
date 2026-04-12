package io.asteroidsjaylib.common.render.shapes3d;

import com.raylib.Raylib;
import com.raylib.Raylib.Color;

import static com.raylib.Raylib.DrawSphereEx;

public class Sphere3D extends Base3DShape {
    public float radius;
    public Color color, wireColor;

    public Sphere3D(float radius, Color color, Color wireColor) {
        this.radius = radius;
        this.color = color;
        this.wireColor = wireColor;
    }

    @Override
    public void draw() {
        DrawSphereEx(new Raylib.Vector3(), radius, 16, 16, color);
        //DrawSphereWires(new Raylib.Vector3(), radius, 16 ,16, wireColor);
    }
}
