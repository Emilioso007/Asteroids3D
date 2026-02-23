package io.asteroidsfx.common;

import io.asteroidsfx.common.event.EventBus;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class World {

    public int width;
    public int height;
    public List<Entity> entities;
    public HashSet<System> systems;
    private final EventBus eventBus;

    public HashSet<KeyCode> keysPressed;

    private static World instance = null;

    private World(){
        entities = new ArrayList<>();
        systems = new HashSet<>();
        keysPressed = new HashSet<>();
        eventBus = new EventBus();
    }

    public static World getInstance(){
        if(instance == null) {
            instance = new World();
        }
        return instance;
    }

    public EventBus getEventBus(){
        return eventBus;
    }

    public void tick(float dt){
        for(System system : systems){
            List<Class<? extends Component>> signature = system.getSignature();

            if(signature == null || signature.isEmpty()){
                system.tick(dt, new ArrayList<>());
                continue;
            }

            List<Entity> filteredEntities = new ArrayList<>();

            for(Entity entity : entities){
                if(matchesSignature(entity, signature)) filteredEntities.add(entity);
            }

            system.tick(dt, filteredEntities);
        }
        entities.removeIf(entity -> entity.toBeRemoved);
    }

    public boolean addEntity(Entity entity){
        return entities.add(entity);
    }

    public boolean addSystem(System system){
        return systems.add(system);
    }

    public List<Entity> getEntitiesWith(Class<? extends Component> requiredComponent){
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities){
            if (entity.getComponent(requiredComponent) != null) result.add(entity);
        }
        return result;
    }

    private boolean matchesSignature(Entity entity, List<Class<? extends Component>> signature){
        for(Class<? extends Component> requiredType : signature){
            boolean hasComponent = false;

            for (Component c : entity.components){
                if (requiredType.isAssignableFrom(c.getClass())){
                    hasComponent = true;
                    break;
                }
            }

            if(!hasComponent) return false;
        }
        return true;
    }
}
