package io.asteroidsfx.common;

import java.util.HashSet;

public abstract class Entity {
    public HashSet<Component> components = new HashSet<>();

    public <T extends Component> T getComponent(Class<T> componentType) {
        return components.stream().filter(componentType::isInstance)
                .map(componentType::cast)
                .findFirst()
                .orElse(null);
    }
}
