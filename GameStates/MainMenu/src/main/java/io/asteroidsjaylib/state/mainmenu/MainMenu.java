package io.asteroidsjaylib.state.mainmenu;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.button.ButtonSPI;
import io.asteroidsjaylib.common.button.ButtonTag;
import io.asteroidsjaylib.common.button.ClickedEvent;
import io.asteroidsjaylib.common.ecs.BaseSystem;
import io.asteroidsjaylib.common.ecs.IGameStateProvider;
import io.asteroidsjaylib.common.event.StateChangedEvent;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.ServiceLoader;

public class MainMenu implements IGameStateProvider {

    @Override
    public String getStateName() {
        return "MAIN_MENU";
    }

    @Override
    public void onEnter(IWorld world) {
        System.out.println("HELLO FROM MAIN MENU STATE!!!");

        ServiceLoader<BaseSystem> systems = ServiceLoader.load(BaseSystem.class);
        for (BaseSystem system : systems){
            system.start(world);
            world.addSystem(system);
        }

        world.addEntity(ServiceLoader.load(ButtonSPI.class).findFirst().orElseThrow().createButton("START_GAME_BUTTON", new Vector2D((float) world.getScreenWidth() /2, (float) world.getScreenHeight() /2), "Start game!"));

        world.getEventBus().subscribe(ClickedEvent.class, this::clicked);
    }

    private void clicked(IWorld world, ClickedEvent clickedEvent) {
        if (!clickedEvent.clickedEntity.hasComponent(ButtonTag.class)) return;

        ButtonTag buttonTag = clickedEvent.clickedEntity.getComponent(ButtonTag.class).orElseThrow();
        if (buttonTag.id.equals("START_GAME_BUTTON")) {
            world.getEventBus().publish(world, new StateChangedEvent("PLAYING"));
        }
    }
}
