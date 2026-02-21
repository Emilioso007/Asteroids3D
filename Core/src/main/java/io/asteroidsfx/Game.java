package io.asteroidsfx;

import io.asteroidsfx.movementsystem.MovementSystem;
import io.asteroidsfx.renderingsystem.RenderingSystem;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game {

    Canvas canvas;
    public GraphicsContext gc;

    public void start(Stage window) {
        World.init(this);

        World.getInstance().width = 800;
        World.getInstance().height = 800;

        canvas = new Canvas(World.getInstance().width, World.getInstance().height);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group(canvas);
        window.setScene(new Scene(root));
        window.setTitle("AsteroidsFX");
        window.show();

        // SETUP ENTITIES AND SYSTEMS
        World.getInstance().addSystem(new RenderingSystem(gc));
        World.getInstance().addSystem(new MovementSystem());

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
