package io.asteroidsfx;

import io.asteroidsfx.asteroidentity.AsteroidEntity;
import io.asteroidsfx.inputsystem.InputSystem;
import io.asteroidsfx.movementsystem.MovementSystem;
import io.asteroidsfx.renderingsystem.RenderingSystem;
import io.asteroidsfx.rotatesystem.RotateSystem;
import io.asteroidsfx.wraparoundsystem.WraparoundSystem;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game {

    public GraphicsContext gc;

    public void start(Stage window) {

        World.getInstance().width = 800;
        World.getInstance().height = 800;

        Canvas canvas = new Canvas(World.getInstance().width, World.getInstance().height);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            World.getInstance().keysPressed.add(event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            World.getInstance().keysPressed.remove(event.getCode());
        });

        window.setScene(scene);
        window.setTitle("AsteroidsFX");
        window.show();

        // SETUP SYSTEMS
        World.getInstance().addSystem(new RenderingSystem(gc));
        World.getInstance().addSystem(new MovementSystem());
        World.getInstance().addSystem(new WraparoundSystem(World.getInstance().width, World.getInstance().height));
        World.getInstance().addSystem(new RotateSystem());
        World.getInstance().addSystem(new InputSystem(World.getInstance().keysPressed));


        // SETUP ENTITIES
        World.getInstance().addEntity(new AsteroidEntity(World.getInstance().width, World.getInstance().height));
        World.getInstance().addEntity(new AsteroidEntity(World.getInstance().width, World.getInstance().height));
        World.getInstance().addEntity(new AsteroidEntity(World.getInstance().width, World.getInstance().height));
        World.getInstance().addEntity(new AsteroidEntity(World.getInstance().width, World.getInstance().height));
        World.getInstance().addEntity(new AsteroidEntity(World.getInstance().width, World.getInstance().height));


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
