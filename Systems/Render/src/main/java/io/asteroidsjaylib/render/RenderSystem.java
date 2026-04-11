package io.asteroidsjaylib.render;


import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.shapes3d.Base3DShape;
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
        return List.of(Render3DComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        Camera3D camera = world.getCamera();

        if(!world.getEntitiesWith(PlayerTag.class).isEmpty()){
            BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
            Vector3D playerPos = player.getComponent(PositionComponent.class).orElseThrow().pos;
            Quaternion playerRot = player.getComponent(RotationComponent.class).orElseThrow().quaternion;

            Vector3D forwardVector = playerRot.rotateVector(new Vector3D(1, 0, 0));

            Vector3D localUp = playerRot.rotateVector(new Vector3D(0, 0, 1));

            Vector3D cameraPos = playerPos.copy().sub(forwardVector.copy().mult(400));

            cameraPos.add(localUp.copy().mult(100));

            camera._position(cameraPos.toVector3());

            camera.target(playerPos.copy().add(forwardVector.copy().mult(1500)).toVector3());

            camera.up(localUp.toVector3());
        }

        rlSetClipPlanes(0.01, 5000);
        BeginMode3D(camera);

        for(BaseEntity entity : entities){

            Vector3D pos = entity.getComponent(PositionComponent.class).map(c -> c.pos).orElse(new Vector3D());

            RotationComponent rotComp = entity.getComponent(RotationComponent.class).orElse(null);
            float angle = 0.0f;
            Vector3D axis = new Vector3D(0, 0, 1);

            if(rotComp != null){
                angle = rotComp.quaternion.getAngleDegrees();
                axis = rotComp.quaternion.getAxis();
            }

            List<Base3DShape> shapes = entity.getComponent(Render3DComponent.class).get().shapes;

            for (var shape : shapes){
                rlPushMatrix();

                rlTranslatef(pos.x, pos.y, pos.z);

                if (shape.offset != null){
                    rlTranslatef(shape.offset.x, shape.offset.y, shape.offset.z);
                }

                rlRotatef(angle, axis.x, axis.y, axis.z);

                shape.draw();

                rlPopMatrix();
            }

        }

        EndMode3D();

    }
}
