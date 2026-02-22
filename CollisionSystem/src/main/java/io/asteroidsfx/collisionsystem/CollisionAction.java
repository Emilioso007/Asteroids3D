package io.asteroidsfx.collisionsystem;

import io.asteroidsfx.common.Entity;

@FunctionalInterface
public interface CollisionAction {
    public void execute(Entity collider, Entity target);
}
