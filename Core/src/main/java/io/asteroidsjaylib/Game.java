package io.asteroidsjaylib;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyReleasedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static com.raylib.Raylib.*;

import java.util.List;

@Component
public class Game {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public World world;

    private final List<BaseSystem> systems;
    private final List<EntitySpi> entitySpis;

    @Autowired
    public Game(List<BaseSystem> systems, List<EntitySpi> entitySpis) {
        this.systems = systems;
        this.entitySpis = entitySpis;
    }

    public void start() {

        int screenWidth = 800;
        int screenHeight = 800;

        initWindow(screenWidth, screenHeight, "Asteroids3D");
        setTargetFPS(60);

        world = new World();

        world.setScreenWidth(screenWidth);
        world.setScreenHeight(screenHeight);

        addSystems(world);
        addEntities(world);

        while(!windowShouldClose()) {

            processInput();

            beginDrawing();
            clearBackground(BLACK);

            world.tick(getFrameTime());

            drawFPS(50, 50);

            endDrawing();

            if (isKeyPressed(KeyboardKey.KEY_C)){
                takeScreenshot("screenshot_" + getTime() + ".png");
            }
        }

        closeWindow();
    }

    public void processInput() {
        for (int i = 1; i <= 348; i++) {
            if (isKeyPressed(i)) {
                eventPublisher.publishEvent(new KeyPressedEvent(i));
            }
            if (isKeyReleased(i)) {
               eventPublisher.publishEvent(new KeyReleasedEvent(i));
            }
        }
    }

    private void addSystems(IWorld world) {
        for (BaseSystem system : systems){
            system.start(world);
            world.addSystem(system);
        }
    }

    private void addEntities(IWorld world) {
        for (EntitySpi entitySpi : entitySpis){
            entitySpi.start(world);
        }
    }

}
