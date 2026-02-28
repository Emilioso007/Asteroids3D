package io.asteroidsfx.common.system;

import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.World;

import java.util.List;

public abstract class SystemECS {
    public int priority;
    public SystemECS(){
        this(0);
    }
    public SystemECS(int priority){
        this.priority = priority;
    }

    public abstract List<Class<? extends Component>> getSignature();
    public abstract void update(List<Entity> entities, double deltaTime);

    public abstract void start(World world);
}
