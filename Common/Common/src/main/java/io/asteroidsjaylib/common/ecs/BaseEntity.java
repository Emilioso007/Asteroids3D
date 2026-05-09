package io.asteroidsjaylib.common.ecs;

import java.util.*;

/// Base entity class.
/// Contains a collection of components.
/// Only one of each component type is allowed.
public abstract class BaseEntity {
    private boolean removed = false;
    private final Map<Class<? extends BaseComponent>, BaseComponent> components = new HashMap<>();

    public final <T extends BaseComponent> BaseEntity add(T component){
        components.put(component.getClass(), component);
        return this;
    }

    public final BaseEntity remove(Class<? extends BaseComponent> componentType){
        components.remove(componentType);
        return this;
    }

    @SafeVarargs
    public final BaseEntity remove(Class<? extends BaseComponent>... componentTypes){
        for (Class<? extends BaseComponent> componentType : componentTypes) {
            this.remove(componentType);
        }
        return this;
    }

    public final <T extends BaseComponent> T get(Class<T> componentType) {
        BaseComponent component = components.get(componentType);
        if (component != null) {
            return componentType.cast(component);
        }
        return null;
    }

    public final Iterable<BaseComponent> getAll(){
        return components.values();
    }

    public final boolean has(Class<? extends BaseComponent> componentType) {
        return this.hasAll(componentType);
    }

    public final boolean hasAll(Class<? extends BaseComponent> componentType) {
        return this.components.containsKey(componentType);
    }

    public final boolean hasAll(Class<? extends BaseComponent> componentTypeA,
                                Class<? extends BaseComponent> componentTypeB) {
        return this.hasAll(componentTypeA)
                && this.hasAll(componentTypeB);
    }

    public final boolean hasAll(Class<? extends BaseComponent> componentTypeA,
                                Class<? extends BaseComponent> componentTypeB,
                                Class<? extends BaseComponent> componentTypeC) {
        return this.hasAll(componentTypeA)
                && hasAll(componentTypeB)
                && hasAll(componentTypeC);
    }

    @SafeVarargs
    public final boolean hasAll(Class<? extends BaseComponent>... componentTypes) {
        for (Class<? extends BaseComponent> componentType : componentTypes) {
            if (!this.hasAll(componentType)) {
                return false;
            }
        }
        return true;
    }

    public final boolean hasAll(Iterable<Class<? extends BaseComponent>> componentTypes) {
        for (Class<? extends BaseComponent> componentType : componentTypes) {
            if (!this.hasAll(componentType)) {
                return false;
            }
        }
        return true;
    }

    public final boolean hasAny(Class<? extends BaseComponent> componentType) {
        return this.hasAll(componentType);
    }

    public final boolean hasAny(Class<? extends BaseComponent> componentTypeA,
                                Class<? extends BaseComponent> componentTypeB) {
        return this.hasAny(componentTypeA)
                || this.hasAny(componentTypeB);
    }

    public final boolean hasAny(Class<? extends BaseComponent> componentTypeA,
                                Class<? extends BaseComponent> componentTypeB,
                                Class<? extends BaseComponent> componentTypeC) {
        return this.hasAny(componentTypeA)
                || hasAny(componentTypeB)
                || hasAny(componentTypeC);
    }

    @SafeVarargs
    public final boolean hasAny(Class<? extends BaseComponent>... componentTypes) {
        for (Class<? extends BaseComponent> componentType : componentTypes) {
            if (this.hasAll(componentType)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasAny(Iterable<Class<? extends BaseComponent>> componentTypes) {
        for (Class<? extends BaseComponent> componentType : componentTypes) {
            if (this.hasAll(componentType)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasNone(Class<? extends BaseComponent> componentType) {
        return !this.components.containsKey(componentType);
    }

    public final boolean hasNone(Class<? extends BaseComponent> componentTypeA,
                                Class<? extends BaseComponent> componentTypeB) {
        return this.hasNone(componentTypeA)
                && this.hasNone(componentTypeB);
    }

    public final boolean hasNone(Class<? extends BaseComponent> componentTypeA,
                                Class<? extends BaseComponent> componentTypeB,
                                Class<? extends BaseComponent> componentTypeC) {
        return this.hasNone(componentTypeA)
                && hasNone(componentTypeB)
                && hasNone(componentTypeC);
    }

    @SafeVarargs
    public final boolean hasNone(Class<? extends BaseComponent>... componentTypes) {
        for (Class<? extends BaseComponent> componentType : componentTypes) {
            if (this.hasAll(componentType)) {
                return false;
            }
        }
        return true;
    }

    public final boolean hasNone(Iterable<Class<? extends BaseComponent>> componentTypes) {
        for (Class<? extends BaseComponent> componentType : componentTypes) {
            if (this.hasAll(componentType)) {
                return false;
            }
        }
        return true;
    }

    public boolean removed() {
        return removed;
    }

    public BaseEntity removed(boolean removed) {
        this.removed = removed;
        return this;
    }

}
