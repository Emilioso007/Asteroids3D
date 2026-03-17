package io.asteroidsjaylib.bullet;

import io.asteroidsjaylib.common.bullet.BulletTag;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.lifetime.LifetimeComponent;
import io.asteroidsjaylib.common.ownership.OwnershipComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.physics.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.render.ShapeComponent;
import io.asteroidsjaylib.common.render.shapes.BaseShape;
import io.asteroidsjaylib.common.render.shapes.Ellipse;
import io.asteroidsjaylib.common.outofbounds.BoundsAction;
import io.asteroidsjaylib.common.outofbounds.OutOfBoundsComponent;

import static com.raylib.Colors.*;

import java.time.Duration;

public class BulletEntity extends BaseEntity{

    public BulletEntity(BaseEntity owner, Vector2D startPosition, Vector2D velocity) {

        OwnershipComponent ownershipComponent = new OwnershipComponent();
        ownershipComponent.owner = owner;
        this.addComponent(ownershipComponent);

        this.addComponent(new BulletTag());

        PositionComponent positionComponent = new PositionComponent(startPosition);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = velocity;
        this.addComponent(velocityComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 2.5f;
        this.addComponent(circleColliderComponent);

        RenderTag renderTag = new RenderTag(10);

        BaseShape shape = new Ellipse(5, 5, LIGHTGRAY);
        ShapeComponent shapeComponent = new ShapeComponent(shape);
        renderTag.addRenderComponent(shapeComponent, 0);

        this.addComponent(renderTag);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        LifetimeComponent lifetimeComponent = new LifetimeComponent(Duration.ofSeconds(2));
        this.addComponent(lifetimeComponent);

    }
}