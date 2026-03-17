package io.asteroidsjaylib.common.render.shapes;

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

        Raylib.DrawEllipse(0, 0, (float) (width/2), (float) (height/2), strokeColor);
        Raylib.DrawEllipse(0, 0, (float) (width/2-strokeWeight), (float) (height/2-strokeWeight), fillColor);

    }
}
