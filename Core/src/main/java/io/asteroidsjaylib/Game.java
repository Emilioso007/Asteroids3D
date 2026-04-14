package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.IGameStateProvider;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.event.StateChangedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyDownEvent;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyUpEvent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.player.PlayerTag;

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
        InitAudioDevice();
        SetTargetFPS(60);
        SetExitKey(KEY_NULL);

        world = new World();

        world.setWidth(worldWidth);
        world.setHeight(worldHeight);
        world.setScreenWidth(screenWidth);
        world.setScreenHeight(screenHeight);

        world.getEventBus().subscribe(StateChangedEvent.class, this::onStateChanged);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);

        world.getEventBus().publish(world, new StateChangedEvent("PLAYING"));

        while(!WindowShouldClose() && running) {

            //UpdateCamera(world.getCamera(), CAMERA_FIRST_PERSON);

            processInput();

            BeginDrawing();
            ClearBackground(BLACK);

            world.tick(GetFrameTime());

            DrawFPS(50, 50);

            if(world.hasEntitiesWith(PlayerTag.class))
                DrawText(world.getEntitiesWith(PlayerTag.class).getFirst().getComponent(PositionComponent.class).pos.toString(),
                100, 100, 24, WHITE);

            if(world.hasEntitiesWith(EnemyTag.class))
                DrawText(world.getEntitiesWith(EnemyTag.class).getFirst().getComponent(PositionComponent.class).pos.toString(),
                        100, 200, 24, WHITE);


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
        /*
        Vector2D screenPosition = new Vector2D(GetMouseX(), GetMouseY());
        Vector2D worldPosition = new Vector2D(GetScreenToWorld2D(screenPosition, world.getCamera()));

        for(int i = 0; i <= 6; i++){
            if(IsMouseButtonPressed(i)){
                world.getEventBus().publish(world, new MousePressedEvent(i, screenPosition, worldPosition));
            }
            if(IsMouseButtonReleased(i)){
                world.getEventBus().publish(world, new MouseReleasedEvent(i, screenPosition, worldPosition));
            }
            if(IsMouseButtonDown(i)){
                world.getEventBus().publish(world, new MouseDownEvent(i, screenPosition, worldPosition));
            }
            if(IsMouseButtonUp(i)){
                world.getEventBus().publish(world, new MouseUpEvent(i, screenPosition, worldPosition));
            }
        }

        world.getEventBus().publish(world, new MousePositionEvent(screenPosition, worldPosition));

         */
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
