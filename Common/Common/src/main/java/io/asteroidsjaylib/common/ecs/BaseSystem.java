package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

import java.util.List;

public abstract sealed class BaseSystem permits BulkSystem, IntervalIteratingSystem, IteratingSystem, ResponseSystem {

    private int priority;
    private boolean running;
    public BaseSystem(){
        this.priority = 0;
        this.running = true;
    }

    /// Called once at system startup.
    /// @param world the world the system operates in.
    public abstract void start(IWorld world);

    public abstract List<Class<? extends BaseComponent>> signature();
    public abstract void update(IWorld world, List<BaseEntity> entities, float deltaTime);

    /// Gets the priority of this system.
    /// Systems with lower values are processed first.
    ///
    /// @return the priority value
    public int priority() {
        return priority;
    }

    /// Sets the priority of this system.
    /// Systems with lower values are processed first.
    ///
    /// @param priority the priority value to set
    public BaseSystem priority(int priority) {
        this.priority = priority;
        return this;
    }

    public boolean running() {
        return running;
    }

    public BaseSystem running(boolean running) {
        this.running = running;
        return this;
    }
}
