package io.asteroidsjaylib.state.playing;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.ecs.IGameStateProvider;
import io.asteroidsjaylib.common.event.StateChangedEvent;
import io.asteroidsjaylib.common.event.input.key.KeyPressedEvent;

import java.util.ServiceLoader;

import static com.raylib.Raylib.KEY_R;
import static com.raylib.Raylib.SetWindowTitle;

public class Playing implements IGameStateProvider {

    @Override
    public String getStateName() {
        return "PLAYING";
    }

    @Override
    public void onEnter(IWorld world) {
        System.out.println("HELLO FROM PLAYING STATE!!!");
        SetWindowTitle("Playing");

        addSystems(world);
        addEntities(world);

        world.getEventBus().subscribe(KeyPressedEvent.class, this::keyPressed);
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

    private void keyPressed(IWorld world, KeyPressedEvent event) {
        if(event.keyCode == KEY_R){
            world.getEventBus().publish(world, new StateChangedEvent("PLAYING"));
        }
    }
}
