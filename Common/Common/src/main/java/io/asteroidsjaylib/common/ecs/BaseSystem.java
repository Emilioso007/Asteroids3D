package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

import java.util.List;

public abstract sealed class BaseSystem permits BulkSystem, IntervalIteratingSystem, IteratingSystem {

    private int priority;
    public boolean running = true;
    public BaseSystem(){
        this(0);
    }
    public BaseSystem(int priority){
        this.setPriority(priority);
    }

    /// Called once at system startup.
    /// @param world the world the system operates in.
    public abstract void start(IWorld world);

    public abstract List<Class<? extends BaseComponent>> getSignature();
    public abstract void update(IWorld world, List<BaseEntity> entities, float deltaTime);

    /// Gets the priority of this system.
    /// Systems with lower values are processed first.
    ///
    /// @return the priority value
    public int getPriority() {
        return priority;
    }

    /// Sets the priority of this system.
    /// Systems with lower values are processed first.
    ///
    /// @param priority the priority value to set
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
