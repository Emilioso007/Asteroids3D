package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.enemy.EnemyTag;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.player.PlayerTag;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import java.util.ServiceLoader;

public class Game {

    private final boolean running = true;

    public World world;

    public void start() {

        int screenWidth = 800;
        int screenHeight = 800;

        InitWindow(screenWidth, screenHeight, "AsteroidsJaylib");
        InitAudioDevice();
        SetTargetFPS(60);

        world = new World();

        world.setScreenWidth(screenWidth);
        world.setScreenHeight(screenHeight);

        addSystems(world);
        addEntities(world);

        while(!WindowShouldClose() && running) {

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

    public void processInput() {
        for (int i = 1; i <= 348; i++) {
            if (IsKeyPressed(i)) {
                world.getEventBus().publish(world, new KeyPressedEvent(i));
            }
            if (IsKeyReleased(i)) {
                world.getEventBus().publish(world, new KeyReleasedEvent(i));
            }
        }
    }

    private void addSystems(IWorld world) {
        ServiceLoader<BaseSystem> systems = ServiceLoader.load(BaseSystem.class);
        for (BaseSystem system : systems){
            system.start(world);
            world.addSystem(system);
        }
    }

    private void addEntities(IWorld world) {
        ServiceLoader<EntitySpi> entitySpis = ServiceLoader.load(EntitySpi.class);
        for (EntitySpi entitySpi : entitySpis){
            entitySpi.start(world);
        }
    }

}
