package io.asteroidsfx.common;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon {
    private double[] x, y;
    private Color fill;
    private Color stroke;
    private double weight;

    public Polygon(double[] x, double[] y, Color fill, Color stroke, double weight){
        this.x = x;
        this.y = y;
        this.fill = fill;
        this.stroke = stroke;
        this.weight = weight;
    }
    public void display(GraphicsContext gc) {
        gc.setFill(fill);
        gc.setStroke(stroke);
        gc.setLineWidth(weight);

        gc.fillPolygon(x, y, x.length);
        gc.strokePolygon(x, y, x.length);
    }
}
