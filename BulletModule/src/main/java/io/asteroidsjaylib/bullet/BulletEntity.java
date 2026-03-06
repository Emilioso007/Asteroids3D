package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.ownership.OwnershipComponent;
import io.asteroidsjaylib.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.lifetime.LifetimeComponent;
import io.asteroidsjaylib.outofbounds.BoundsAction;
import io.asteroidsjaylib.outofbounds.OutOfBoundsComponent;
import io.asteroidsjaylib.physics.component.PositionComponent;
import io.asteroidsjaylib.physics.component.VelocityComponent;
import io.asteroidsjaylib.render.component.ShapeComponent;
import io.asteroidsjaylib.render.shapes.BaseShape;
import io.asteroidsjaylib.render.shapes.Ellipse;

import static com.raylib.Colors.*;

import java.time.Duration;

public class BulletEntity extends BaseEntity {

    public BulletEntity(BaseEntity owner, Vector startPosition, Vector velocity) {

        OwnershipComponent ownershipComponent = new OwnershipComponent();
        ownershipComponent.owner = owner;
        this.addComponent(ownershipComponent);

        this.addComponent(new BulletTag());

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = startPosition;
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = velocity;
        this.addComponent(velocityComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 2.5;
        this.addComponent(circleColliderComponent);

        BaseShape shape = new Ellipse(5, 5, LIGHTGRAY);
        ShapeComponent shapeComponent = new ShapeComponent(shape);
        this.addComponent(shapeComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        //outOfBoundsComponent.leftExtent = -2.5;
        //outOfBoundsComponent.rightExtent = 2.5;
        //outOfBoundsComponent.topExtent = -2.5;
        //outOfBoundsComponent.bottomExtent = 2.5;
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        LifetimeComponent lifetimeComponent = new LifetimeComponent(Duration.ofSeconds(2));
        this.addComponent(lifetimeComponent);

    }

}