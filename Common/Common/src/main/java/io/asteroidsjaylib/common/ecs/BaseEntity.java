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
        Class<?> currentClass = component.getClass();

        while (currentClass != null && BaseComponent.class.isAssignableFrom(currentClass)){
            components.putIfAbsent((Class<? extends BaseComponent>) currentClass, component);
            currentClass = currentClass.getSuperclass();
        }
    }

    public <T extends BaseComponent> void removeComponent(Class<T> componentType){
        components.remove(componentType);
    }

    public <T extends BaseComponent> T getComponent(Class<T> componentType) {

        BaseComponent component = components.get(componentType);
        if (component != null) {
            return componentType.cast(component);
        }

        return null;
    }

    public Collection<BaseComponent> getComponents(){
        return components.values();
    }

    @SafeVarargs
    public final boolean hasComponents(Class<? extends BaseComponent>... componentTypes) {

        inputLoop:
        for (Class<? extends BaseComponent> componentType : componentTypes){
            if(components.containsKey(componentType)) {
                continue;
            }

            for (BaseComponent component : components.values()){
                if (componentType.isAssignableFrom(component.getClass())){
                    continue inputLoop;
                }
            }

            return false;
        }

        return true;
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(boolean toBeRemoved) {
        this.toBeRemoved = toBeRemoved;
    }

}
