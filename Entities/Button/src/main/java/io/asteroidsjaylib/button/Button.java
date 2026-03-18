package io.asteroidsjaylib.button;

import com.raylib.Raylib;
import io.asteroidsjaylib.common.button.ButtonTag;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.render.RenderAlign;
import io.asteroidsjaylib.common.render.RenderTag;
import io.asteroidsjaylib.common.render.ShapeComponent;
import io.asteroidsjaylib.common.render.TextComponent;
import io.asteroidsjaylib.common.render.shapes.Polygon;
import io.asteroidsjaylib.common.util.Vector2D;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.WHITE;

public class Button extends BaseEntity {

    public Button(String id, Vector2D position, String text){

        this.addComponent(new ButtonTag(id));

        this.addComponent(new PositionComponent(position));

        RenderTag renderTag = new RenderTag(100, true);

        float[] xs = new float[4];
        float[] ys = new float[4];

        float textWidth = Raylib.MeasureText(text, 12);

        xs[0] = - textWidth/2 - 10;
        xs[1] = textWidth/2 + 10;
        xs[2] = textWidth/2 + 10;
        xs[3] = - textWidth/2 - 10;

        ys[0] = (float) -12 /2 - 10;
        ys[1] = (float) -12 /2 - 10;
        ys[2] = (float) 12 /2 + 10;
        ys[3] = (float) 12 /2 + 10;

        Polygon polygon = new Polygon(xs, ys, WHITE, BLACK, 5);

        ShapeComponent shapeComponent = new ShapeComponent(polygon);
        renderTag.addRenderComponent(shapeComponent, 0);

        TextComponent textComponent = new TextComponent(text, 12, BLACK);
        textComponent.horizontalAlign = RenderAlign.CENTER;
        textComponent.verticalAlign = RenderAlign.CENTER;
        renderTag.addRenderComponent(textComponent, 1);

        this.addComponent(renderTag);

        CircleColliderComponent circleColliderComponent = new CircleColliderComponent();
        circleColliderComponent.radius = xs[1];
        this.addComponent(circleColliderComponent);
    }

}
