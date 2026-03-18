package io.asteroidsjaylib.common.button;

import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.util.Vector2D;

public interface ButtonSPI {
    BaseEntity createButton(String id, Vector2D position, String text);
}
