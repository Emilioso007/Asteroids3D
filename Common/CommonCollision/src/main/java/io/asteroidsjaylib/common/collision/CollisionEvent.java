package io.asteroidsjaylib.common.collision;

import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.event.BaseEvent;

public class CollisionEvent extends BaseEvent {
    public BaseEntity a;
    public BaseEntity b;

    public CollisionEvent(BaseEntity a, BaseEntity b){
        this.a = a;
        this.b = b;
    }

    public <T extends BaseComponent> BaseEntity getEntityWith(Class<T> componentType){
        if (a.hasAll(componentType)){
            return a;
        }
        if(b.hasAll(componentType)){
            return b;
        }

        return null;
    }

    public <T extends BaseComponent> boolean hasEntityWith(Class<T> componentType){
        return a.hasAll(componentType) || b.hasAll(componentType);
    }

    public BaseEntity getOther(BaseEntity known){
        if (known == a) return b;
        if (known == b) return a;
        return null;
    }
}
