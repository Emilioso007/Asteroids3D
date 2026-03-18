package io.asteroidsjaylib.button;

import io.asteroidsjaylib.common.button.ButtonSPI;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public class ButtonProvider implements ButtonSPI {
    @Override
    public BaseEntity createButton(String id, Vector2D position, String text) {
        return new Button(id, position, text);
    }
}
