package io.asteroidsfx.collisionsystem;

import io.asteroidsfx.common.Component;
import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.event.Event;

public class CollisionEvent extends Event{
    public Entity a;
    public Entity b;

    public CollisionEvent(Entity a, Entity b){
        this.a = a;
        this.b = b;
    }

    public Entity getEntityWith(Class<? extends Component> componentType){
        if (a.hasComponent(componentType)){
            return a;
        }
        if(b.hasComponent(componentType)){
            return b;
        }

        return null;
    }

}
