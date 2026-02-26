package io.asteroidsfx.common.event;

import io.asteroidsfx.common.Component;

import java.util.HashSet;
import java.util.Set;

public abstract class Event {
    public Set<Component> components = new HashSet<>();

    public <T extends Component> T getComponent(Class<T> componentType) {

        for (Component c : components){
            if (componentType.isAssignableFrom(c.getClass())){
                return componentType.cast(c);
            }
        }

        return null;
    }
}
