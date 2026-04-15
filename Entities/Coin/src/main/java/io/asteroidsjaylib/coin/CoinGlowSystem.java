package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.render.LightManager;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

import static com.raylib.Raylib.GetTime;

public class CoinGlowSystem extends IteratingSystem {
    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        Vector3D pos = entity.getComponent(PositionComponent.class).pos;

        float pulse = (float) (Math.sin(GetTime() * 10) * 0.2 + 1.0);
        float red = 0.0f;
        float green = 0.5f * pulse * 1.0f;
        float blue = 1.0f * pulse * 10.0f;

        LightManager.addLightSphere(pos.x, pos.y, pos.z, 10f, red, green, blue);
    }

    @Override
    public void start(IWorld world) {
        this.setPriority(99);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(CoinTag.class, PositionComponent.class);
    }
}
