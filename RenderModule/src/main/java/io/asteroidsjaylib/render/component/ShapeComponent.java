package io.asteroidsjaylib.render.component;

import com.raylib.Raylib;
import io.asteroidsjaylib.render.RenderComponent;
import io.asteroidsjaylib.render.shapes.BaseShape;

import static com.raylib.Raylib.*;

public class ShapeComponent extends RenderComponent {

    public BaseShape shape;

    public ShapeComponent(BaseShape shape){
        this.shape = shape;
    }

    @Override
    public void draw(Raylib.Vector2 position, float angle) {
        rlPushMatrix();

        rlTranslatef(position.x(), position.y(), 0);
        rlRotatef(angle, 0, 0, 1);

        shape.draw();

        rlPopMatrix();
    }
}
