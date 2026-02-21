package io.asteroidsfx.inputcomponent;

import io.asteroidsfx.common.Entity;

@FunctionalInterface
public interface InputAction{
    void execute(Entity entity, float dt);
}
