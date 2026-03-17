package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.input.KeyDownEvent;
import io.asteroidsjaylib.common.event.input.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.KeyReleasedEvent;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.ServiceLoader;

public class Game {

    public World world;

    public void start() {

        int screenWidth = 800;
        int screenHeight = 800;
        int worldWidth = 1600;
        int worldHeight = 1600;

        InitWindow(screenWidth, screenHeight, "AsteroidsJaylib");
        SetTargetFPS(60);

        world = new World();

        world.setWidth(worldWidth);
        world.setHeight(worldHeight);
        world.setScreenWidth(screenWidth);
        world.setScreenHeight(screenHeight);

        // ADD ENTITIES
        addEntities();

        // START SYSTEMS
        addSystems();

        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);

        while(!WindowShouldClose()){
            processInput();

            BeginDrawing();
            ClearBackground(BLACK);

            world.tick(GetFrameTime());

            EndDrawing();
        }

        CloseWindow();
    }

    public void processInput() {
        for (int i = 1; i <= 366; i++) { // Should be all keys, hopefully
            if (IsKeyPressed(i)) {
                world.getEventBus().publish(world, new KeyPressedEvent(i));
            }
            if (IsKeyReleased(i)) {
                world.getEventBus().publish(world, new KeyReleasedEvent(i));
            }
            if (IsKeyDown(i)) {
                world.getEventBus().publish(world, new KeyDownEvent(i));
            }
        }
    }

    private void addSystems() {
        ServiceLoader<BaseSystem> systems = ServiceLoader.load(BaseSystem.class);
        for (BaseSystem system : systems){
            system.start(world);
            world.addSystem(system);
        }
    }

    private void addEntities() {
        ServiceLoader<EntitySpi> entitySpis = ServiceLoader.load(EntitySpi.class);
        for (EntitySpi entitySpi : entitySpis){
            entitySpi.start(world);
        }
    }

    private void keyPressed(IWorld world, KeyPressedEvent event) {
        if(event.keyCode == KEY_R){
            world.clearEntities();
            world.clearSystems();
            world.getEventBus().clear();
            addEntities();
            addSystems();
            world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        }
    }
}
