package io.asteroidsfx;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashSet;

public final class World {

    public int width;
    public int height;
    public ArrayList<Entity> entities;
    public ArrayList<System> systems;

    public HashSet<KeyCode> keysPressed;

    private static World instance = null;

    private World(){
        entities = new ArrayList<>();
        systems = new ArrayList<>();
        keysPressed = new HashSet<>();
    }

    public static World getInstance(){
        if(instance == null) {
            instance = new World();
        }
        return instance;
    }

    public void tick(float dt){
        for(System system : systems){
            system.tick(dt, entities);
        }
    }

    public boolean addEntity(Entity entity){
        return entities.add(entity);
    }

    public boolean addSystem(System system){
        return systems.add(system);
    }

    public <T extends System> T getSystem(Class<T> systemType){
        return systems.stream().filter(systemType::isInstance)
                .map(systemType::cast)
                .findFirst()
                .orElse(null);
    }
}
