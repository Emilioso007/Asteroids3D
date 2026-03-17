package io.asteroidsjaylib.common.render.shapes;

import com.raylib.Raylib;
import com.raylib.Raylib.Color;

public class Polygon extends BaseShape {
    public double[] x;
    public double[] y;

    public Polygon(double[] x, double[] y, Color fillColor, Color strokeColor, double strokeWeight){
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWeight = strokeWeight;
    }

    @Override
    public void draw() {
        int count = x.length;
        if (count < 3) return;

        // Pre-calculate positions to avoid repeated math in the loop
        float centerX = (float) (x[0] + xOffset);
        float centerY = (float) (y[0] + yOffset);

        // Reuse these to avoid constant allocation
        try (Raylib.Vector2 v0 = new Raylib.Vector2().x(centerX).y(centerY);
             Raylib.Vector2 v1 = new Raylib.Vector2();
             Raylib.Vector2 v2 = new Raylib.Vector2()) {

            // Fill: fan triangulation
            for (int i = 1; i < count - 1; i++) {
                v1.x((float)(x[i] + xOffset)).y((float)(y[i] + yOffset));
                v2.x((float)(x[i+1] + xOffset)).y((float)(y[i+1] + yOffset));

                // Try v0, v1, v2 first. If it doesn't show, use v0, v2, v1
                Raylib.DrawTriangle(v0, v2, v1, fillColor);
            }
        }

        // Outline
        for (int i = 0; i < count; i++) {
            int next = (i + 1) % count;
            // Lines don't care about winding order
            try (Raylib.Vector2 start = new Raylib.Vector2().x((float)(x[i] + xOffset)).y((float)(y[i] + yOffset));
                 Raylib.Vector2 end = new Raylib.Vector2().x((float)(x[next] + xOffset)).y((float)(y[next] + yOffset))) {
                Raylib.DrawLineEx(start, end, (float) strokeWeight, strokeColor);
            }
        }
    }
}
