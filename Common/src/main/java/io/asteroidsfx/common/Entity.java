package io.asteroidsfx.common;

import java.util.HashSet;

public abstract class Entity {
    public boolean toBeRemoved = false;
    public HashSet<Component> components = new HashSet<>();

    public <T extends Component> T getComponent(Class<T> componentType) {

        for (Component c : components){
            if (componentType.isAssignableFrom(c.getClass())){
                return componentType.cast(c);
            }
        }

        return null;
    }

    public <T extends Component> boolean hasComponent(Class<T> componentType) {
        for (Component c : components){
            if(componentType.isAssignableFrom(c.getClass())){
                return true;
            }
        }
        return false;
    }

}
