package io.asteroidsjaylib.render;


import com.raylib.*;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.Position;
import io.asteroidsjaylib.common.physics3d.Rotation;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.LightManager;
import io.asteroidsjaylib.common.render.Render3D;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.Base3DShape;
import io.asteroidsjaylib.common.util.ResourceLoader;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.MaterialMapIndex.MATERIAL_MAP_ALBEDO;
import static com.raylib.Raylib.ShaderUniformDataType.SHADER_UNIFORM_FLOAT;
import static com.raylib.Raylib.ShaderUniformDataType.SHADER_UNIFORM_VEC3;

public class RenderSystem extends BulkSystem {

    private static final Vector3 RL_VEC_SCRATCHPAD = new Vector3();
    private Vector3D smoothedCameraPos = null;
    private Vector3D smoothedCameraTarget = null;
    private Vector3D smoothedCameraUp = null;

    private final float posLerpSpeed = 8.0f;
    private final float targetLerpSpeed = 10.0f;
    private final float upLerpSpeed = 2.0f;

    private Model skyboxModel;

    private static final float[] timeArr = new float[1];

    @Override
    public void start(IWorld world) {
        this.priority(100);

        String texPath = ResourceLoader.getAsAbsolutePath("/stars.png");
        Texture starsTexture = loadTexture(texPath);

        Mesh skyMesh = genMeshSphere(10f, 32, 32);
        skyboxModel = loadModelFromMesh(skyMesh);

        skyboxModel.materials().getArrayElement(0).maps().getArrayElement(MATERIAL_MAP_ALBEDO).texture(starsTexture);
    }

    @Override
    public List<Class<? extends BaseComponent>> signature() {
        return List.of(Render3D.class, Position.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        Camera3D camera = world.getCamera();

        if(!world.getEntitiesWith(PlayerTag.class).isEmpty()){
            BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
            Position playerPosition = player.get(Position.class);
            Rotation playerRotation = player.get(Rotation.class);
            assert playerPosition != null;
            assert playerRotation != null;

            Vector3D forwardVector = playerRotation.quaternion().rotateVector(new Vector3D(1, 0, 0));
            Vector3D localUp = playerRotation.quaternion().rotateVector(new Vector3D(0, 0, 1));

            Vector3D desiredCameraPos = playerPosition.vector().copy().subtract(forwardVector.copy().multiply(400)).add(localUp.copy().multiply(100));
            Vector3D desiredCameraTarget = playerPosition.vector().copy().add(forwardVector.copy().multiply(1500));
            Vector3D desiredCameraUp = localUp;

            // Initialize smoothed vectors instantly on the very first frame to prevent massive snapping
            if (smoothedCameraPos == null) {
                smoothedCameraPos = desiredCameraPos.copy();
                smoothedCameraTarget = desiredCameraTarget.copy();
                smoothedCameraUp = desiredCameraUp.copy();
            } else {

                // Smoothly interpolate (Lerp) from current position to the desired position
                // Formula: current = current + (desired - current) * lerpSpeed * deltaTime
                smoothedCameraPos.add(desiredCameraPos.copy().subtract(smoothedCameraPos).multiply(posLerpSpeed * deltaTime));
                smoothedCameraTarget.add(desiredCameraTarget.copy().subtract(smoothedCameraTarget).multiply(targetLerpSpeed * deltaTime));
                smoothedCameraUp.add(desiredCameraUp.copy().subtract(smoothedCameraUp).multiply(upLerpSpeed * deltaTime)).normalize();
            }

            camera.position(smoothedCameraPos.toVector3(RL_VEC_SCRATCHPAD));
            camera.target(smoothedCameraTarget.toVector3(RL_VEC_SCRATCHPAD));
            camera.up(smoothedCameraUp.toVector3(RL_VEC_SCRATCHPAD));
        }

        ShaderManager.setGlobalShaderValue("viewPos", camera.position(), SHADER_UNIFORM_VEC3);
        LightManager.applyLights();

        Vector3 sunDirection = new Vector3(-1.0f, -1.0f,-1.0f);
        ShaderManager.setGlobalShaderValue("lightDirection", sunDirection, SHADER_UNIFORM_VEC3);


        timeArr[0] = (float) getTime();
        ShaderManager.setGlobalShaderValue("time", timeArr, SHADER_UNIFORM_FLOAT);

        rlSetClipPlanes(1.0, 5000);
        beginMode3D(camera);

        drawSkybox(camera);

        for(BaseEntity entity : entities){

            long start = System.nanoTime();

            // ---

            Position position = entity.get(Position.class);
            assert position != null;

            float angle = 0.0f;
            Vector3D axis = null;

            Rotation rotComp = entity.get(Rotation.class);
            if(rotComp != null){
                angle = rotComp.quaternion().getAngleDegrees();
                axis = rotComp.quaternion().getAxis();
            }

            Render3D render3D = entity.get(Render3D.class);
            assert render3D != null;

            for (Base3DShape shape : render3D.getActiveShapes()){
                drawShape(shape, position.vector(), angle, axis);
            }

            // ---

            long ms = (System.nanoTime() - start) / 1000000;
            if (ms > 1) {
                System.out.println(entity.getClass().getSimpleName() + " took " + ms + "ms");
            }

        }

        endMode3D();

    }

    private void drawSkybox(Camera3D camera) {
        rlDisableDepthMask();
        rlDisableBackfaceCulling();
        drawModel(skyboxModel, camera.position(), 1.0f, WHITE);
        rlEnableBackfaceCulling();
        rlEnableDepthMask();
    }

    private static void drawShape(Base3DShape shape, Vector3D pos, float angle, Vector3D axis) {
        rlPushMatrix();

        rlTranslatef(pos.x, pos.y, pos.z);

        if (shape.offset != null){
            rlTranslatef(shape.offset.x, shape.offset.y, shape.offset.z);
        }

        if (axis != null) {
            rlRotatef(angle, axis.x, axis.y, axis.z);
        }

        shape.draw(pos.magnitudeSquared());

        rlPopMatrix();
    }
}
