package io.asteroidsjaylib.common.shapes;

import com.raylib.Raylib;
import com.raylib.Raylib.Color;
import static com.raylib.Colors.*;

public class Ellipse extends BaseShape{

    public double width;
    public double height;

    public Ellipse(double width, double height, Color fillColor) {
        this(width, height, fillColor, BLANK, 0);
    }
    public Ellipse(double width, double height, Color fillColor, Color strokeColor, double strokeWeight){
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWeight = strokeWeight;
    }


    @Override
    public void draw() {

        Raylib.DrawEllipse(0, 0, (float) (height/2), (float) (width/2), strokeColor);
        Raylib.DrawEllipse(0, 0, (float) (height/2-strokeWeight), (float) (width/2-strokeWeight), fillColor);

        /*
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        graphicsContext.setLineWidth(strokeWeight);

        double x = -width/2;
        double y = -height/2;
        graphicsContext.fillOval(x, y, width, height);
        graphicsContext.strokeOval(x, y, width, height);
         */
    }
}
