package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.button.ButtonSPI;
import io.asteroidsjaylib.common.button.ButtonTag;
import io.asteroidsjaylib.common.button.ClickedEvent;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.event.input.key.KeyDownEvent;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyUpEvent;
import io.asteroidsjaylib.common.event.input.mouse.*;
import io.asteroidsjaylib.common.score.IncrementScoreEvent;
import io.asteroidsjaylib.common.util.Vector2D;

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

        // Try adding a button
        world.addEntity(ServiceLoader.load(ButtonSPI.class).findFirst().orElseThrow().createButton("INCREMENT_SCORE_BUTTON", new Vector2D(100, 100), "Hello world!"));

        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
        world.getEventBus().subscribe(ClickedEvent.class, this::clicked);

        while(!WindowShouldClose()){
            processInput();

            BeginDrawing();
            ClearBackground(BLACK);

            world.tick(GetFrameTime());

            EndDrawing();
        }

        CloseWindow();
    }

    private void clicked(IWorld world, ClickedEvent clickedEvent) {
        System.out.println("SOMETHING WAS CLICKED IM SCARED!");

        if (!clickedEvent.clickedEntity.hasComponent(ButtonTag.class)) return;

        ButtonTag buttonTag = clickedEvent.clickedEntity.getComponent(ButtonTag.class).orElseThrow();
        if (buttonTag.id.equals("INCREMENT_SCORE_BUTTON")) {
            world.getEventBus().publish(world, new IncrementScoreEvent(1));
        }
    }

    public void processInput() {
        for (int i = 1; i <= 348; i++) {
            if (IsKeyPressed(i)) {
                world.getEventBus().publish(world, new KeyPressedEvent(i));
            }
            if (IsKeyReleased(i)) {
                world.getEventBus().publish(world, new KeyReleasedEvent(i));
            }
            if (IsKeyDown(i)) {
                world.getEventBus().publish(world, new KeyDownEvent(i));
            }
            if(IsKeyUp(i)){
                world.getEventBus().publish(world, new KeyUpEvent(i));
            }
        }

        Vector2D mousePos = new Vector2D(GetMouseX(), GetMouseY());

        for(int i = 0; i <= 6; i++){
            if(IsMouseButtonPressed(i)){
                world.getEventBus().publish(world, new MousePressedEvent(i, mousePos));
            }
            if(IsMouseButtonReleased(i)){
                world.getEventBus().publish(world, new MouseReleasedEvent(i, mousePos));
            }
            if(IsMouseButtonDown(i)){
                world.getEventBus().publish(world, new MouseDownEvent(i, mousePos));
            }
            if(IsMouseButtonUp(i)){
                world.getEventBus().publish(world, new MouseUpEvent(i, mousePos));
            }
        }

        world.getEventBus().publish(world, new MousePositionEvent(mousePos));
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
