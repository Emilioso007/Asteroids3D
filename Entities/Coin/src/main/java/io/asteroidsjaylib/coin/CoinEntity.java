package io.asteroidsjaylib.coin;

import io.asteroidsjaylib.common.coin.CoinTag;
import io.asteroidsjaylib.common.collision.SphereColliderComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.render.*;
import io.asteroidsjaylib.common.render.shapes3d.Model3D;
import io.asteroidsjaylib.common.util.Vector3D;

public class CoinEntity extends BaseEntity {

    public CoinEntity(Vector3D startPosition, int value) {
        this.addComponent(new CoinTag(value));
        this.addComponent(new PositionComponent(startPosition));
        this.addComponent(new RotationComponent());
        Render3DComponent render3DComponent = new Render3DComponent();
        Model3D coin = new Model3D("/LegoCoin.glb", 1, 90, -90, 0);
        coin.applyShader(ShaderManager.getShader("glass"));
        render3DComponent.addShape(coin);
        this.addComponent(render3DComponent);

        this.addComponent(new SphereColliderComponent(10));
    }
}