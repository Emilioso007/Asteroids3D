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
                Vector3D vector = position.vector();
                if (withinFrustum(camera, shape.boundingBox(vector.x, vector.y, vector.z)))
                    drawShape(shape, vector, angle, axis);
            }

            // ---

            long ms = (System.nanoTime() - start) / 1000000;
            if (ms > 1) {
                System.out.println(entity.getClass().getSimpleName() + " took " + ms + "ms");
            }

        }

        endMode3D();

    }

    private boolean withinFrustum(Camera3D camera, BoundingBox boundingBox) {
        // 1. Calculate the View Matrix
        Matrix view = getCameraMatrix(camera);

        // 2. Calculate the Projection Matrix
        // Assuming a standard perspective camera.
        // You may need to pass screen width/height as arguments if your window is resizable.
        float aspect = (float) getScreenWidth() / (float) getScreenHeight();

        // Near and Far planes. Match these to your game's actual render distances.
        Matrix proj = matrixPerspective(camera.fovy() * Math.PI/180, aspect, 0.01f, 5000.0f);

        // 3. Combine to get the View-Projection Matrix
        Matrix vp = matrixMultiply(view, proj);

        // 4. Extract the 6 Frustum Planes from the VP Matrix
        // A plane is defined by an XYZ normal vector and a distance (W).
        float[][] planes = new float[6][4];

        // Left Plane
        planes[0][0] = vp.m3() + vp.m0();
        planes[0][1] = vp.m7() + vp.m4();
        planes[0][2] = vp.m11() + vp.m8();
        planes[0][3] = vp.m15() + vp.m12();

        // Right Plane
        planes[1][0] = vp.m3() - vp.m0();
        planes[1][1] = vp.m7() - vp.m4();
        planes[1][2] = vp.m11() - vp.m8();
        planes[1][3] = vp.m15() - vp.m12();

        // Bottom Plane
        planes[2][0] = vp.m3() + vp.m1();
        planes[2][1] = vp.m7() + vp.m5();
        planes[2][2] = vp.m11() + vp.m9();
        planes[2][3] = vp.m15() + vp.m13();

        // Top Plane
        planes[3][0] = vp.m3() - vp.m1();
        planes[3][1] = vp.m7() - vp.m5();
        planes[3][2] = vp.m11() - vp.m9();
        planes[3][3] = vp.m15() - vp.m13();

        // Near Plane
        planes[4][0] = vp.m3() + vp.m2();
        planes[4][1] = vp.m7() + vp.m6();
        planes[4][2] = vp.m11() + vp.m10();
        planes[4][3] = vp.m15() + vp.m14();

        // Far Plane
        planes[5][0] = vp.m3() - vp.m2();
        planes[5][1] = vp.m7() - vp.m6();
        planes[5][2] = vp.m11() - vp.m10();
        planes[5][3] = vp.m15() - vp.m14();

        // Normalize all planes
        for (int i = 0; i < 6; i++) {
            float length = (float) Math.sqrt(planes[i][0] * planes[i][0] + planes[i][1] * planes[i][1] + planes[i][2] * planes[i][2]);
            planes[i][0] /= length;
            planes[i][1] /= length;
            planes[i][2] /= length;
            planes[i][3] /= length;
        }

        // 5. Check the Bounding Box against each plane
        // If the box is completely behind ANY plane, it's not visible.
        for (int i = 0; i < 6; i++) {
            // Find the "positive vertex" - the corner of the AABB furthest along the plane's normal
            float px = (planes[i][0] > 0) ? boundingBox.max().x() : boundingBox.min().x();
            float py = (planes[i][1] > 0) ? boundingBox.max().y() : boundingBox.min().y();
            float pz = (planes[i][2] > 0) ? boundingBox.max().z() : boundingBox.min().z();

            // Calculate the distance of this vertex from the plane
            float distance = planes[i][0] * px + planes[i][1] * py + planes[i][2] * pz + planes[i][3];

            // If the furthest point on the box is behind the plane, the whole box is behind it
            if (distance < 0) {
                return false; // Culled!
            }
        }

        // Passed all plane checks, the box is at least partially visible
        return true;
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
