package io.asteroidsjaylib;

import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.input.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.KeyReleasedEvent;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.Comparator;
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
        world.screenWidth = screenWidth;
        world.screenHeight = screenHeight;


        /*
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();

        world.setGraphicsContext(gc);

        Group root = new Group(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> world.getEventBus().publish(world, new KeyPressedEvent(event.getCode())));
        scene.setOnKeyReleased(event -> world.getEventBus().publish(world, new KeyReleasedEvent(event.getCode())));

        window.setScene(scene);
        window.setTitle("AsteroidsFX");
        window.show();
         */

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
        // 1. Handle "Pressed" events (using the internal queue)
        int key;
        while ((key = GetKeyPressed()) != 0) {
            world.getEventBus().publish(world, new KeyPressedEvent(key));
        }

        // 2. Handle "Released" events
        // Raylib doesn't have a Release Queue, so we check specific keys
        // or track the "down" state manually as shown below.
        if (IsKeyReleased(KEY_SPACE)) {
            world.getEventBus().publish(world, new KeyReleasedEvent(KEY_SPACE));
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
        entitySpis.stream()
            .map(ServiceLoader.Provider::get)
            .sorted(Comparator.comparingInt(EntitySpi::getPriority))
            .forEach(entitySpi -> entitySpi.start(world));
    }

    private void keyPressed(World world, KeyPressedEvent event) {
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
