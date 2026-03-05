package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.util.Vector;
import io.asteroidsjaylib.common.World;
import io.asteroidsjaylib.physics.component.PositionComponent;

public class PlayerEntityProvider implements EntitySpi {
    @Override
    public void start(World world) {
        PlayerEntity player = new PlayerEntity(new Vector(world.getWidth() /2.0, world.getHeight() /2.0));
        world.addEntity(player);
        world.cameraLocation = player.getComponent(PositionComponent.class).pos;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
