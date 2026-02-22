package io.asteroidsfx.common;

import java.util.ArrayList;

public abstract class System {
    public abstract void tick(float dt, ArrayList<Entity> entities);
}
