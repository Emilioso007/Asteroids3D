package io.asteroidsjaylib.render;


import com.raylib.Raylib;
import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics3d.PositionComponent;
import io.asteroidsjaylib.common.physics3d.RotationComponent;
import io.asteroidsjaylib.common.player.PlayerTag;
import io.asteroidsjaylib.common.render.LightManager;
import io.asteroidsjaylib.common.render.Render3DComponent;
import io.asteroidsjaylib.common.render.ShaderManager;
import io.asteroidsjaylib.common.render.shapes3d.Base3DShape;
import io.asteroidsjaylib.common.util.Quaternion;
import io.asteroidsjaylib.common.util.ResourceLoader;
import io.asteroidsjaylib.common.util.Vector3D;

import java.util.List;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class RenderSystem extends BulkSystem {

    private static final Vector3 RL_VEC_SCRATCHPAD = new Vector3();
    private static final Vector3D GHOST_POS_SCRATCHPAD = new Vector3D();
    private Vector3D smoothedCameraPos = null;
    private Vector3D smoothedCameraTarget = null;
    private Vector3D smoothedCameraUp = null;

    private final float posLerpSpeed = 8.0f;
    private final float targetLerpSpeed = 10.0f;
    private final float upLerpSpeed = 2.0f;

    private Model skyboxModel;
    private float MAP_SIZE;
    private float MAP_BOUNDS;
    private float BORDER_DIST;

    private static final float[] timeArr = new float[1];

    @Override
    public void start(IWorld world) {
        this.setPriority(100);

        MAP_SIZE = world.getWorldSize();
        MAP_BOUNDS = MAP_SIZE / 2.0f;
        BORDER_DIST = 5000;

        String texPath = ResourceLoader.getAsAbsolutePath("/stars.png");
        Texture starsTexture = Raylib.LoadTexture(texPath);

        Mesh skyMesh = GenMeshSphere(10f, 32, 32);
        skyboxModel = LoadModelFromMesh(skyMesh);

        skyboxModel.materials().position(0).maps().position(MATERIAL_MAP_ALBEDO).texture(starsTexture);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(Render3DComponent.class, PositionComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        Camera3D camera = world.getCamera();

        if(!world.getEntitiesWith(PlayerTag.class).isEmpty()){
            BaseEntity player = world.getEntitiesWith(PlayerTag.class).getFirst();
            Vector3D playerPos = player.getComponent(PositionComponent.class).pos;
            Quaternion playerRot = player.getComponent(RotationComponent.class).quaternion;

            Vector3D forwardVector = playerRot.rotateVector(new Vector3D(1, 0, 0));
            Vector3D localUp = playerRot.rotateVector(new Vector3D(0, 0, 1));

            Vector3D desiredCameraPos = playerPos.copy().sub(forwardVector.copy().mult(400)).add(localUp.copy().mult(100));
            Vector3D desiredCameraTarget = playerPos.copy().add(forwardVector.copy().mult(1500));
            Vector3D desiredCameraUp = localUp;

            // Initialize smoothed vectors instantly on the very first frame to prevent massive snapping
            if (smoothedCameraPos == null) {
                smoothedCameraPos = desiredCameraPos.copy();
                smoothedCameraTarget = desiredCameraTarget.copy();
                smoothedCameraUp = desiredCameraUp.copy();
            } else {

                // X Axis Check
                float dx = desiredCameraPos.x - smoothedCameraPos.x;
                if (dx > MAP_BOUNDS) {
                    smoothedCameraPos.x += MAP_SIZE;
                    smoothedCameraTarget.x += MAP_SIZE;
                } else if (dx < -MAP_BOUNDS) {
                    smoothedCameraPos.x -= MAP_SIZE;
                    smoothedCameraTarget.x -= MAP_SIZE;
                }

                // Y Axis Check
                float dy = desiredCameraPos.y - smoothedCameraPos.y;
                if (dy > MAP_BOUNDS) {
                    smoothedCameraPos.y += MAP_SIZE;
                    smoothedCameraTarget.y += MAP_SIZE;
                } else if (dy < -MAP_BOUNDS) {
                    smoothedCameraPos.y -= MAP_SIZE;
                    smoothedCameraTarget.y -= MAP_SIZE;
                }

                // Z Axis Check
                float dz = desiredCameraPos.z - smoothedCameraPos.z;
                if (dz > MAP_BOUNDS) {
                    smoothedCameraPos.z += MAP_SIZE;
                    smoothedCameraTarget.z += MAP_SIZE;
                } else if (dz < -MAP_BOUNDS) {
                    smoothedCameraPos.z -= MAP_SIZE;
                    smoothedCameraTarget.z -= MAP_SIZE;
                }

                // Smoothly interpolate (Lerp) from current position to the desired position
                // Formula: current = current + (desired - current) * lerpSpeed * deltaTime
                smoothedCameraPos.add(desiredCameraPos.copy().sub(smoothedCameraPos).mult(posLerpSpeed * deltaTime));
                smoothedCameraTarget.add(desiredCameraTarget.copy().sub(smoothedCameraTarget).mult(targetLerpSpeed * deltaTime));
                smoothedCameraUp.add(desiredCameraUp.copy().sub(smoothedCameraUp).mult(upLerpSpeed * deltaTime)).normalize();
            }

            camera._position(smoothedCameraPos.toVector3(RL_VEC_SCRATCHPAD));
            camera.target(smoothedCameraTarget.toVector3(RL_VEC_SCRATCHPAD));
            camera.up(smoothedCameraUp.toVector3(RL_VEC_SCRATCHPAD));
        }

        ShaderManager.setGlobalShaderValue("viewPos", camera._position(), SHADER_UNIFORM_VEC3);
        LightManager.applyLights();

        try (Vector3 sunDirection = new Vector3().x(-1.0f).y(-1.0f).z(-1.0f)) {
            ShaderManager.setGlobalShaderValue("lightDirection", sunDirection, SHADER_UNIFORM_VEC3);
        }

        timeArr[0] = (float) GetTime();
        ShaderManager.setGlobalShaderValue("time", timeArr, SHADER_UNIFORM_FLOAT);

        rlSetClipPlanes(1.0, 5000);
        BeginMode3D(camera);

        DrawSkybox(camera);

        for(BaseEntity entity : entities){

            Vector3D pos = entity.getComponent(PositionComponent.class).pos;

            RotationComponent rotComp = entity.getComponent(RotationComponent.class);
            float angle = 0.0f;
            Vector3D axis = new Vector3D(0, 0, 1);

            if(rotComp != null){
                angle = rotComp.quaternion.getAngleDegrees();
                axis = rotComp.quaternion.getAxis();
            }

            Render3DComponent render3DComponent = entity.getComponent(Render3DComponent.class);

            // --- GHOST RENDERING MATH ---

            // 1. Calculate primitive offsets (0f if not near a border)
            float ghostX = (pos.x > MAP_BOUNDS - BORDER_DIST) ? -MAP_SIZE : (pos.x < -MAP_BOUNDS + BORDER_DIST) ? MAP_SIZE : 0f;
            float ghostY = (pos.y > MAP_BOUNDS - BORDER_DIST) ? -MAP_SIZE : (pos.y < -MAP_BOUNDS + BORDER_DIST) ? MAP_SIZE : 0f;
            float ghostZ = (pos.z > MAP_BOUNDS - BORDER_DIST) ? -MAP_SIZE : (pos.z < -MAP_BOUNDS + BORDER_DIST) ? MAP_SIZE : 0f;

            // 2. Loop 1 or 2 times per axis
            int xCount = (ghostX != 0f) ? 2 : 1;
            int yCount = (ghostY != 0f) ? 2 : 1;
            int zCount = (ghostZ != 0f) ? 2 : 1;

            for (int ix = 0; ix < xCount; ix++) {
                float ox = (ix == 0) ? 0f : ghostX;

                for (int iy = 0; iy < yCount; iy++) {
                    float oy = (iy == 0) ? 0f : ghostY;

                    for (int iz = 0; iz < zCount; iz++) {
                        float oz = (iz == 0) ? 0f : ghostZ;

                        // Mutate our scratchpad to the new ghost coordinates
                        GHOST_POS_SCRATCHPAD.x = pos.x + ox;
                        GHOST_POS_SCRATCHPAD.y = pos.y + oy;
                        GHOST_POS_SCRATCHPAD.z = pos.z + oz;

                        // Draw the shapes at the calculated position!
                        for (Base3DShape shape : render3DComponent.getActiveShapes()){
                            drawShape(shape, GHOST_POS_SCRATCHPAD, angle, axis);
                        }
                    }
                }
            }
            // --- GHOST RENDERING END ---

        }

        EndMode3D();

    }

    private void DrawSkybox(Camera3D camera) {
        rlDisableDepthMask();
        rlDisableBackfaceCulling();
        DrawModel(skyboxModel, camera._position(), 1.0f, WHITE);
        rlEnableBackfaceCulling();
        rlEnableDepthMask();
    }

    private static void drawShape(Base3DShape shape, Vector3D pos, float angle, Vector3D axis) {
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
