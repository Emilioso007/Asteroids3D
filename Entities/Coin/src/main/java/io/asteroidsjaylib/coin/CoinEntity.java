package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.outofbounds.BoundsAction;
import io.asteroidsjaylib.common.outofbounds.OutOfBoundsComponent;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.physics.VelocityComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.render.RenderAlign;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.render.ShapeComponent;
import io.asteroidsjaylib.common.render.TextComponent;
import io.asteroidsjaylib.common.render.shapes.Ellipse;

import static com.raylib.Colors.*;

public class CoinEntity extends BaseEntity {

    public CoinEntity(Vector2D startPosition, Vector2D startVelocity, int value) {
        this.addComponent(new PositionComponent(startPosition));
        this.addComponent(new VelocityComponent(startVelocity));
        this.addComponent(new CoinTag(value));

        RenderTag renderTag = new RenderTag(10);
        ShapeComponent shapeComponent = new ShapeComponent(new Ellipse(16, 16, YELLOW));
        renderTag.addRenderComponent(shapeComponent, 0);

        String text = value+"";
        TextComponent textComponent = new TextComponent(text, 12, BLACK);
        textComponent.horizontalAlign = RenderAlign.CENTER;
        textComponent.verticalAlign = RenderAlign.CENTER;
        renderTag.addRenderComponent(textComponent, 1);

        this.addComponent(renderTag);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = 8;
        this.addComponent(circleColliderComponent);

        OutOfBoundsComponent outOfBoundsComponent = new OutOfBoundsComponent();
        outOfBoundsComponent.boundsAction = BoundsAction.WRAP;
        this.addComponent(outOfBoundsComponent);

    }
}