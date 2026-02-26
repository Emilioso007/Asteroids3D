package io.asteroidsfx.enemyentity;

import io.asteroidsfx.anglecomponent.AngleComponent;
import io.asteroidsfx.collision.CircleColliderComponent;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.Polygon;
import io.asteroidsfx.common.Vector;
import io.asteroidsfx.common.World;
import io.asteroidsfx.outofbounds.BoundsAction;
import io.asteroidsfx.outofbounds.OutOfBoundsComponent;
import io.asteroidsfx.positioncomponent.PositionComponent;
import io.asteroidsfx.rendercomponent.RenderComponent;
import io.asteroidsfx.velocitycomponent.VelocityComponent;
import javafx.scene.paint.Color;

public class EnemyEntity extends Entity{

    public EnemyEntity(){

        this.components.add(new EnemyTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = new Vector(World.getInstance().width * 0.25f, World.getInstance().height * 0.5f);
        this.components.add(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = new Vector(50, 0);
        this.components.add(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.components.add(angleComponent);

        RenderComponent renderComponent = new RenderComponent();
        double[] xs = new double[8];
        double[] ys = new double[8];
        for(int i = 0; i < 8; i++){
            xs[i] = Math.cos(Math.toRadians(i*360f/8)) * 25;
            ys[i] = Math.sin(Math.toRadians(i*360f/8)) * 25;
        }
        renderComponent.polygon = new Polygon(xs, ys, Color.DARKRED, Color.RED, 4);
        this.components.add(renderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.components.add(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 25;
        this.components.add(circleColliderComponent);
    }

}
