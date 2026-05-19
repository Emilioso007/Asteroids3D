package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.ecs.EntitySPI;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.util.Vector3D;

public class PlayerEntityProvider implements EntitySPI {
    @Override
    public void start(IWorld world) {
        world.addEntity(new PlayerEntity(new Vector3D(0, 0, 0)));
    }
}
