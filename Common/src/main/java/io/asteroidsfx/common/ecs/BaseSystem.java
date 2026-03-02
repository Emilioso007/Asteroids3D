package io.asteroidsfx.common.ecs;

import io.asteroidsfx.common.World;

import java.util.List;

public abstract class BaseSystem {

    private int priority;
    public boolean running = true;
    public BaseSystem(){
        this(0);
    }
    public BaseSystem(int priority){
        this.setPriority(priority);
    }

    public abstract List<Class<? extends BaseComponent>> getSignature();
    public abstract void update(List<BaseEntity> entities, double deltaTime);

    /**
     * Called once at system startup.
     * @param world the world the system lives in
     */

    public abstract void start(World world);

    /**
     * Gets the priority of this system.
     * Systems with lower values are processed first.
     *
     * @return the priority value
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of this system.
     * Systems with lower values are processed first.
     *
     * @param priority the priority value to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
