package io.asteroidsjaylib.common.ecs;

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

        // Exact key match first
        BaseComponent component = components.get(componentType);
        if (component != null) {
            return componentType.cast(component);
        }

        // Fall back to assignability search (handles subclass components)
        for (BaseComponent c : components.values()) {
            if (componentType.isAssignableFrom(c.getClass())) {
                return componentType.cast(c);
            }
        }

        return null;
    }

    public Collection<BaseComponent> getComponents(){
        return components.values();
    }

    public <T extends BaseComponent> boolean hasComponent(Class<T> componentType) {
        if (components.containsKey(componentType)) return true;

        for (BaseComponent c : components.values()) {
            if (componentType.isAssignableFrom(c.getClass())) return true;
        }

        return false;
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(boolean toBeRemoved) {
        this.toBeRemoved = toBeRemoved;
    }
}
