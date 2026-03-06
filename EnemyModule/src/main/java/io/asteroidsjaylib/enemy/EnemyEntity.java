package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.outofbounds.BoundsAction;
import io.asteroidsjaylib.outofbounds.OutOfBoundsComponent;
import io.asteroidsjaylib.physics.component.AngleComponent;
import io.asteroidsjaylib.physics.component.PositionComponent;
import io.asteroidsjaylib.physics.component.VelocityComponent;
import io.asteroidsjaylib.render.component.ShapeComponent;
import io.asteroidsjaylib.render.shapes.BaseShape;
import io.asteroidsjaylib.render.shapes.Ellipse;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class EnemyEntity extends BaseEntity {

    public EnemyEntity(World world){

        this.addComponent(new EnemyTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = new Vector(world.getWidth() * 0.25f, world.getHeight() * 0.5f);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = new Vector(50, 0);
        this.addComponent(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.addComponent(angleComponent);

        BaseShape shape = new Ellipse(60, 40, GetColor(0x8b0000ff), RED, 4);
        ShapeComponent shapeComponent = new ShapeComponent(shape);
        this.addComponent(shapeComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 25;
        this.addComponent(circleColliderComponent);
    }

}
