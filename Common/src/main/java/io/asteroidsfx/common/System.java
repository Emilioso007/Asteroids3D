package io.asteroidsfx.common;

import java.util.List;

public abstract class System {
    public abstract List<Class<? extends Component>> getSignature();
    public abstract void tick(float dt, List<Entity> entities);
}
