package io.asteroidsfx;

import io.asteroidsfx.common.Entity;
import io.asteroidsfx.common.System;

import java.util.HashSet;

public final class World {

    public int width;
    public int height;
    public HashSet<Entity> entities;
    public HashSet<System> systems;

    public Game game;

    private static World instance = null;

    private World(){
        entities = new HashSet<>();
        systems = new HashSet<>();
    }

    public static void init(Game game){
        instance = new World();
        instance.game = game;
    }

    public static World getInstance(){
        if(instance == null) {
            instance = new World();
        }
        return instance;
    }

    public void tick(float dt){
        for(io.asteroidsfx.common.System system : systems){
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
