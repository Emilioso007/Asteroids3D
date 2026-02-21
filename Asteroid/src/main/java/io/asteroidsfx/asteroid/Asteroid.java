package io.asteroidsfx.asteroid;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.Polygon;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import javafx.scene.paint.Color;

import java.util.Random;

public class Asteroid extends Entity{

    public Asteroid(){
        PositionComponent positionComponent = new PositionComponent();
        positionComponent.x = 100;
        positionComponent.y = 100;

        this.components.add(positionComponent);

        RenderComponent renderComponent = new RenderComponent();

        Random random = new Random();
        int points = random.nextInt(4, 13);
        double[] xs = new double[points];
        double[] ys = new double[points];

        double angleBetween = Math.toRadians(360f/points);

        for(int i = 0; i < points; i++){
            xs[i] = Math.cos(i*angleBetween)*random.nextInt(25, 75);
            ys[i] = Math.sin(i*angleBetween)*random.nextInt(25, 75);
        }

        renderComponent.polygon = new Polygon(xs, ys, Color.DARKGRAY, Color.GRAY, 2);

        this.components.add(renderComponent);
    }

}