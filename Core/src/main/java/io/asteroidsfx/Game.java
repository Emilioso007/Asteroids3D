package io.asteroidsfx;

import io.asteroidsfx.common.EntitySpi;
import io.asteroidsfx.common.SystemSpi;
import io.asteroidsfx.common.World;
import io.asteroidsfx.renderingsystem.RenderingSystem;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ServiceLoader;

public class Game {

    public GraphicsContext gc;

    public void start(Stage window) {

        World.getInstance().width = (int)(1920*0.8);
        World.getInstance().height = (int)(1080*0.8);

        Canvas canvas = new Canvas(World.getInstance().width, World.getInstance().height);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> World.getInstance().keysPressed.add(event.getCode()));

        scene.setOnKeyReleased(event -> World.getInstance().keysPressed.remove(event.getCode()));

        window.setScene(scene);
        window.setTitle("AsteroidsFX");
        window.show();

        // SETUP SYSTEMS
        ServiceLoader<SystemSpi> systemSpis = ServiceLoader.load(SystemSpi.class);
        for (SystemSpi systemSpi : systemSpis){
            systemSpi.start(World.getInstance());
        }


        // RENDERING SYSTEM IS ADDED MANUALLY
        World.getInstance().addSystem(new RenderingSystem(gc));


        // SETUP ENTITIES
        ServiceLoader<EntitySpi> entitySpis = ServiceLoader.load(EntitySpi.class);
        for (EntitySpi entitySpi : entitySpis){
            entitySpi.start(World.getInstance());
        }


        // LOOP
        new AnimationTimer() {
            private long lastFrameTime = 0;
            @Override
            public void handle(long now){
                float deltaTime = (lastFrameTime == 0) ? 0 : (now - lastFrameTime) / 1_000_000_000f;
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
