package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.ecs.EntitySpi;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.util.Vector3D;

public class PlayerEntityProvider implements EntitySpi {
    @Override
    public void start(IWorld world) {
        world.addEntity(new PlayerEntity(new Vector3D(-2500, 0, 0)));
    }
}
