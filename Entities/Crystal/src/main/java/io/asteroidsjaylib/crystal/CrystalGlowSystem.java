package io.asteroidsjaylib.crystal;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.crystal.CrystalTag;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.IteratingSystem;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.render.LightManager;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

import static com.raylib.Raylib.getTime;

public class CrystalGlowSystem extends IteratingSystem {
    @Override
    public void update(IWorld world, BaseEntity crystal, float deltaTime) {
        Position position = crystal.get(Position.class);
        assert position != null;
        
        Vector3D pos = position.vector();

        float pulse = (float) (Math.sin(getTime() * 10) * 0.2 + 1.0);
        float red = 0.0f;
        float green = 0.5f * pulse * 1.0f;
        float blue = 1.0f * pulse * 10.0f;

        LightManager.addLightSphere(pos.x, pos.y, pos.z, 10f, red, green, blue);
    }

    @Override
    public void start(IWorld world) {
        this.priority(99);
    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(CrystalTag.class, Position.class);
    }
}
