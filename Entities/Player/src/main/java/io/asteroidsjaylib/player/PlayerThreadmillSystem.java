package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

public class PlayerThreadmillSystem extends BulkSystem {
    @Override
    public void start(IWorld world) {
        this.setPriority(40);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        if (!world.hasEntitiesWith(PlayerTag.class)) return;

        Vector3D playerPos = world.getEntitiesWith(PlayerTag.class).getFirst().getComponent(PositionComponent.class).pos;

        for (BaseEntity entity : entities){

            entity.getComponent(PositionComponent.class).pos.sub(playerPos);

        }

    }
}
