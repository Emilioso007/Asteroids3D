package io.asteroidsfx;

import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.common.World;
import io.asteroidsfx.common.system.SystemECS;
import io.asteroidsfx.renderingsystem.RenderingSystem;
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

        World.getInstance().width = (int)(800);
        World.getInstance().height = (int)(800);

        Canvas canvas = new Canvas(World.getInstance().width, World.getInstance().height);
        gc = canvas.getGraphicsContext2D();

        World.getInstance().graphicsContext = gc;

        Group root = new Group(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> World.getInstance().keysPressed.add(event.getCode()));

        scene.setOnKeyReleased(event -> World.getInstance().keysPressed.remove(event.getCode()));

        window.setScene(scene);
        window.setTitle("AsteroidsFX");
        window.show();

        // SETUP SYSTEMS
        ServiceLoader<SystemECS> systems = ServiceLoader.load(SystemECS.class);
        for (SystemECS system : systems){
            World.getInstance().addSystem(system);
            system.start(World.getInstance());
        }

        ServiceLoader<EntitySpi> entitySpis = ServiceLoader.load(EntitySpi.class);

        entitySpis.stream()
        .map(ServiceLoader.Provider::get)
        .sorted(Comparator.comparingInt(EntitySpi::getPriority))
        .forEach(entitySpi -> entitySpi.start(World.getInstance()));

        // LOOP
        new AnimationTimer() {
            private long lastFrameTime = 0;
            @Override
            public void handle(long now){
                double deltaTime = (lastFrameTime == 0) ? 0 : (now - lastFrameTime) / 1_000_000_000f;
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
