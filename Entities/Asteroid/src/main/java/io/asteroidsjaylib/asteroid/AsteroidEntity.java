package io.asteroidsjaylib.asteroid;

import io.asteroidsjaylib.common.asteroid.AsteroidSize;
import io.asteroidsjaylib.common.asteroid.AsteroidSizeComponent;
import io.asteroidsjaylib.common.asteroid.AsteroidTag;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.render.ShapeComponent;
import io.asteroidsjaylib.common.render.shapes.BaseShape;
import io.asteroidsjaylib.common.render.shapes.Polygon;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.physics.*;import io.asteroidsjaylib.common.outofbounds.BoundsAction;import io.asteroidsjaylib.common.outofbounds.OutOfBoundsComponent;

import static com.raylib.Colors.*;

import java.util.Random;

public class AsteroidEntity extends BaseEntity {

    public AsteroidEntity(Vector2D startPosition, Vector2D startVelocity, AsteroidSize size){

        this.addComponent(new AsteroidTag());

        AsteroidSizeComponent asteroidSizeComponent = new AsteroidSizeComponent();
        asteroidSizeComponent.size = size;
        this.addComponent(asteroidSizeComponent);

        Random random = new Random();

        PositionComponent positionComponent = new PositionComponent(startPosition);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent(startVelocity);
        this.addComponent(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.angle = 0;
        this.addComponent(angleComponent);

        RotationComponent rotationComponent = new RotationComponent();
        rotationComponent.dAngle = random.nextInt(45, 135);
        this.addComponent(rotationComponent);

        int points = random.nextInt(4, 13);
        float[] xs = new float[points];
        float[] ys = new float[points];

        double angleBetween = Math.toRadians(360f/points);

        int min = 15 + size.ordinal() * 10;
        int max = 35 + size.ordinal() * 20;

        for(int i = 0; i < points; i++){
            xs[i] = (float) (Math.cos(i*angleBetween)*random.nextInt(min, max));
            ys[i] = (float) (Math.sin(i*angleBetween)*random.nextInt(min, max));
        }

        RenderTag renderTag = new RenderTag(30);

        BaseShape shape = new Polygon(xs, ys, DARKGRAY, GRAY, 2);
        ShapeComponent shapeComponent = new ShapeComponent(shape);
        renderTag.addRenderComponent(shapeComponent, 0);

        this.addComponent(renderTag);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = (35 + size.ordinal() * 20);
        this.addComponent(circleColliderComponent);

    }

}