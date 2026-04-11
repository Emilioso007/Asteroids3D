package io.asteroidsjaylib.render;


import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.shapes3d.Cube3DComponent;
import io.asteroidsjaylib.common.render.shapes3d.Sphere3DComponent;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

import static com.raylib.Raylib.*;

public class RenderSystem extends BulkSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(100);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        Camera3D camera = world.getCamera();

        if(!world.getEntitiesWith(PlayerTag.class).isEmpty()){
            BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
            Vector3D playerPos = player.getComponent(PositionComponent.class).orElseThrow().pos;
            Quaternion playerRot = player.getComponent(RotationComponent.class).orElseThrow().quaternion;

            Vector3D forwardVector = playerRot.rotateVector(new Vector3D(1, 0, 0));

            Vector3D cameraPos = playerPos.copy().sub(forwardVector.mult(400));

            cameraPos.z += 200;

            camera._position(cameraPos.toVector3());

            camera.target(playerPos.toVector3());

            camera.up(new Vector3D(0, 0, 1).toVector3());
        }

        BeginMode3D(camera);

        for(BaseEntity entity : entities){

            Vector3D pos = entity.getComponent(PositionComponent.class).map(c -> c.pos).orElseThrow();

            float angle = entity.getComponent(RotationComponent.class)
                    .map(rot-> rot.quaternion.getZAngleDegrees()).orElse(0f);

            rlPushMatrix();

            rlTranslatef(pos.x, pos.y, pos.z);

            rlRotatef(angle, 0, 0, 1);

            Vector3 localOrigin = new Vector3().x(0).y(0).z(0);

            entity.getComponent(Cube3DComponent.class).ifPresent(cube -> {
                DrawCube(localOrigin, cube.width, cube.height, cube.length, cube.color);
                DrawCubeWires(localOrigin, cube.width, cube.height, cube.length, cube.wireColor);
            });

            entity.getComponent(Sphere3DComponent.class).ifPresent(sphere -> {
                DrawSphereEx(localOrigin, sphere.radius, 16, 16, sphere.color);
                DrawSphereWires(localOrigin, sphere.radius, 16 ,16, sphere.wireColor);
            });

            rlPopMatrix();

        }

        EndMode3D();

    }
}
