package io.asteroidsjaylib.common.render.shapes3d;

import com.raylib.Raylib;
import com.raylib.Raylib.Color;

import static com.raylib.Raylib.DrawCube;

public class Cube3D extends Base3DShape {
    public float width, height, length;
    public Color color, wireColor;

    public Cube3D(float width, float height, float length, Color color, Color wireColor) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.color = color;
        this.wireColor = wireColor;
    }

    @Override
    public void draw() {
        DrawCube(new Raylib.Vector3(), width, height, length, color);
        //DrawCubeWires(new Raylib.Vector3(), width, height, length, wireColor);
    }
}
