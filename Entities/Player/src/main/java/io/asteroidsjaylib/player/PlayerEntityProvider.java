package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.physics2d.PositionComponent;
import io.asteroidsjaylib.common.util.Vector2D;
import io.asteroidsjaylib.common.IWorld;

public class PlayerEntityProvider implements EntitySpi {
    @Override
    public void start(IWorld world) {
        PlayerEntity player = new PlayerEntity(new Vector2D((float) (world.getWidth() /2.0), (float) (world.getHeight() /2.0)));
        world.addEntity(player);
        world.setCameraLocation(player.getComponent(PositionComponent.class).orElseThrow().pos);
    }
}
