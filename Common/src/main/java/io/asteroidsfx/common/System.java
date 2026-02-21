package io.asteroidsfx.common;

import java.util.HashSet;

public abstract class System {
    public abstract void tick(float dt, HashSet<Entity> entities);
}
