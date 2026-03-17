package io.asteroidsjaylib.enemy;

import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics.AngleComponent;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.physics.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.render.ShapeComponent;
import io.asteroidsjaylib.common.render.shapes.BaseShape;
import io.asteroidsjaylib.common.render.shapes.Ellipse;
import io.asteroidsjaylib.common.outofbounds.BoundsAction;
import io.asteroidsjaylib.common.outofbounds.OutOfBoundsComponent;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class EnemyEntity extends BaseEntity {

    public EnemyEntity(World world){

        this.addComponent(new EnemyTag());

        PositionComponent positionComponent = new PositionComponent(new Vector2D(world.getWidth() * 0.25f, world.getHeight() * 0.5f));
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = new Vector2D(50, 0);
        this.addComponent(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.addComponent(angleComponent);

        RenderTag renderTag = new RenderTag(20);

        BaseShape shape = new Ellipse(60, 40, GetColor(0x8b0000ff), RED, 4);
        ShapeComponent shapeComponent = new ShapeComponent(shape);
        renderTag.addRenderComponent(shapeComponent, 0);

        this.addComponent(renderTag);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 25;
        this.addComponent(circleColliderComponent);
    }

}
