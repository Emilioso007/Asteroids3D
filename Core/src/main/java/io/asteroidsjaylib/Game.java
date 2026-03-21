package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.IGameStateProvider;
import io.asteroidsjaylib.common.event.StateChangedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyDownEvent;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyUpEvent;
import io.asteroidsjaylib.common.event.input.mouse.*;
import io.asteroidsjaylib.common.util.Vector2D;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.ServiceLoader;

public class Game {

    private boolean running = true;

    public World world;

    public void start() {

        int screenWidth = 800;
        int screenHeight = 800;
        int worldWidth = 1600;
        int worldHeight = 1600;

        InitWindow(screenWidth, screenHeight, "AsteroidsJaylib");
        //SetTargetFPS(60);
        SetExitKey(KEY_NULL);

        world = new World();

        world.setWidth(worldWidth);
        world.setHeight(worldHeight);
        world.setScreenWidth(screenWidth);
        world.setScreenHeight(screenHeight);

        world.getEventBus().subscribe(StateChangedEvent.class, this::onStateChanged);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);

        world.getEventBus().publish(world, new StateChangedEvent("MAIN_MENU"));

        while(!WindowShouldClose() && running) {
            processInput();

            BeginDrawing();
            ClearBackground(BLACK);

            world.tick(GetFrameTime());

            DrawFPS(50, 50);
            EndDrawing();
        }

        CloseWindow();
    }

    private void keyPressed(IWorld world, KeyPressedEvent keyPressedEvent) {
        switch (keyPressedEvent.keyCode){
            case KEY_ESCAPE:
                world.getEventBus().publish(world, new StateChangedEvent("MAIN_MENU"));
                break;
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

    private void onStateChanged(IWorld world, StateChangedEvent event){

        if (event.newState.equalsIgnoreCase("QUIT")){
            running = false;
            return;
        }

        world.clearEntities();
        world.clearSystems();
        world.getEventBus().clear();
        world.getEventBus().subscribe(StateChangedEvent.class, this::onStateChanged);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);


        ServiceLoader<IGameStateProvider> providers = ServiceLoader.load(IGameStateProvider.class);
        for (IGameStateProvider provider : providers){
            if (provider.getStateName().equalsIgnoreCase(event.newState)){
                provider.onEnter(world);

                return;
            }
        }

    }

}
