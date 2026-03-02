package io.asteroidsfx.common.ecs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/// Base entity class.
/// Contains a collection of components.
/// Only one of each component type is allowed.
public abstract class BaseEntity {
    private boolean toBeRemoved = false;
    private final Map<Class<? extends BaseComponent>, BaseComponent> components = new HashMap<>();

    public <T extends BaseComponent> void addComponent(T component){
        components.putIfAbsent(component.getClass(), component);
    }

    public <T extends BaseComponent> void removeComponent(Class<T> componentType){
        components.remove(componentType);
    }

    public <T extends BaseComponent> T getComponent(Class<T> componentType) {

        if(!components.containsKey(componentType)) return null;

        BaseComponent component = components.get(componentType);

        if (componentType.isAssignableFrom(component.getClass())) {
            return componentType.cast(component);
        }

        return null;
    }

    public Collection<BaseComponent> getComponents(){
        return components.values();
    }

    public <T extends BaseComponent> boolean hasComponent(Class<T> componentType) {
        return components.containsKey(componentType);
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(boolean toBeRemoved) {
        this.toBeRemoved = toBeRemoved;
    }
}
