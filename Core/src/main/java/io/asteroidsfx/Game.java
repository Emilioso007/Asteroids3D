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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.ServiceLoader;

public class Game {

    public GraphicsContext gc;

    public void start(Stage window) {

        int screenWidth = 800;
        int screenHeight = 800;
        int worldWidth = 1600;
        int worldHeight = 1600;

        World.getInstance().setWidth(worldWidth);
        World.getInstance().setHeight(worldHeight);
        World.getInstance().screenWidth = screenWidth;
        World.getInstance().screenHeight = screenHeight;

        Canvas canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();

        World.getInstance().setGraphicsContext(gc);

        Group root = new Group(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> World.getInstance().getEventBus().publish(new KeyPressedEvent(event.getCode())));
        scene.setOnKeyReleased(event -> World.getInstance().getEventBus().publish(new KeyReleasedEvent(event.getCode())));

        window.setScene(scene);
        window.setTitle("AsteroidsFX");
        window.show();

        // ADD ENTITIES
        ServiceLoader<EntitySpi> entitySpis = ServiceLoader.load(EntitySpi.class);
        entitySpis.stream()
            .map(ServiceLoader.Provider::get)
            .sorted(Comparator.comparingInt(EntitySpi::getPriority))
            .forEach(entitySpi -> entitySpi.start(World.getInstance()));

        // START SYSTEMS
        ServiceLoader<BaseSystem> systems = ServiceLoader.load(BaseSystem.class);
        for (BaseSystem system : systems){
            World.getInstance().addSystem(system);
            system.start(World.getInstance());
        }

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
                World.getInstance().tick(deltaTime);
            }
        }.start();
    }
}
