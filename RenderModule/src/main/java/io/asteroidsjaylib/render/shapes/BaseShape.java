package io.asteroidsjaylib.render.shapes;

import com.raylib.Raylib.Color;

public abstract class BaseShape {
    public double xOffset;
    public double yOffset;
    public Color fillColor;
    public Color strokeColor;
    public double strokeWeight;
    public abstract void draw();
}
