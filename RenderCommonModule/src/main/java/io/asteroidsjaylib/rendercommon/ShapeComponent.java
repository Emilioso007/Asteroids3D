package io.asteroidsjaylib.rendercommon;

import com.raylib.Raylib;
import io.asteroidsjaylib.rendercommon.shapes.BaseShape;

public class ShapeComponent extends RenderComponent {

    public BaseShape shape;

    public ShapeComponent(BaseShape shape){
        this.shape = shape;
    }

    @Override
    public void draw(Raylib.Vector2 position, float angle) {
        Raylib.rlPushMatrix();

        Raylib.rlTranslatef(position.x(), position.y(), 0);
        Raylib.rlRotatef(angle, 0, 0, 1);

        shape.draw();

        Raylib.rlPopMatrix();
    }
}
