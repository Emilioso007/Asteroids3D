package io.asteroidsjaylib.player;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.player.PlayerTag;

import java.util.List;

public class PlayerThreadmillSystem extends BulkSystem {
    @Override
    public void start(IWorld world) {
        this.priority(40);
    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Position.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        if (!world.hasEntitiesWith(PlayerTag.class)) return;

        Position playerPosition = world.getEntitiesWith(PlayerTag.class).getFirst().get(Position.class);
        assert playerPosition != null;

        for (BaseEntity entity : entities){

            if (entity.hasAll(PlayerTag.class)) continue;

            Position position = entity.get(Position.class);
            assert position != null;

            position.vector().subtract(playerPosition.vector());

        }

        playerPosition.vector(0, 0, 0);

    }
}
