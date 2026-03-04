package io.asteroidsfx;

import io.asteroidsfx.common.ecs.EntitySpi;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.ecs.BaseSystem;
import io.asteroidsfx.common.event.input.KeyPressedEvent;
import io.asteroidsfx.common.event.input.KeyReleasedEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.ServiceLoader;

public class Game {

    public World world;
    public GraphicsContext gc;

    public void start(Stage window) {

        int screenWidth = 800;
        int screenHeight = 800;
        int worldWidth = 1600;
        int worldHeight = 1600;

        world = new World();

        world.setWidth(worldWidth);
        world.setHeight(worldHeight);
        world.screenWidth = screenWidth;
        world.screenHeight = screenHeight;

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

        // ADD ENTITIES
        addEntities();

        // START SYSTEMS
        addSystems();

        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);

        // LOOP
        new AnimationTimer() {
            private long lastFrameTime = 0;
            @Override
            public void handle(long now){
                double deltaTime = (lastFrameTime == 0) ? 0 : (now - lastFrameTime) / 1_000_000_000d;
                lastFrameTime = now;

                // clear screen
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // tick all systems
                world.tick(deltaTime);
            }
        }.start();
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
        if(event.keyCode == KeyCode.R){
            world.clearEntities();
            world.clearSystems();
            world.getEventBus().clear();
            addEntities();
            addSystems();
            world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        }
    }
}
