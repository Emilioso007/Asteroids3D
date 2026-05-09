package io.asteroidsjaylib.common.render;

import com.raylib.*;
import io.asteroidsjaylib.common.util.ResourceLoader;

import static com.raylib.Raylib.*;

import java.util.HashMap;
import java.util.Map;

public class Model3D extends Base3DShape {

    private static final Map<String, Model> modelCache = new HashMap<>();

    public Model model;
    public float scale;
    public float pitchOffset, yawOffset, rollOffset;

    public boolean active = true;
    public int lodCount = -1;
    public int currentLodLevel = 0;

    private final BoundingBox localBBox;

    public Model3D(String glbPath, float scale, float pitchOffset, float yawOffset, float rollOffset){
        this.scale = scale;
        this.pitchOffset = pitchOffset;
        this.yawOffset = yawOffset;
        this.rollOffset = rollOffset;

        if (modelCache.containsKey(glbPath)){
            this.model = modelCache.get(glbPath);
            localBBox = getModelBoundingBox(model);
            return;
        }

        this.model = loadModel(ResourceLoader.getAsAbsolutePath(glbPath, StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass()));
        modelCache.put(glbPath, this.model);

        localBBox = getModelBoundingBox(model);

        //System.out.println("Actual Meshes Loaded: " + model.meshCount());
    }

    @Override
    public BoundingBox boundingBox(float x, float y, float z) {
        return new BoundingBox()
                .min(new Vector3(
                    localBBox.min().x() + x,
                    localBBox.min().y() + y,
                    localBBox.min().z() + z))
                .max(new Vector3(
                        localBBox.max().x() + x,
                        localBBox.max().y() + y,
                        localBBox.max().z() + z));
    }

    @Override
    public void draw(float distanceToCameraSquared) {
        if (!active) return;

        rlPushMatrix();
        rlRotatef(yawOffset, 0, 0, 1);
        rlRotatef(pitchOffset, 1, 0, 0);
        rlRotatef(rollOffset, 0, 1, 0);

        if (lodCount == -1){
            // Old school method. zzz
            drawModel(model, new Vector3(), scale, WHITE);
        } else {
            // New and exciting LOD method!!!

            if (distanceToCameraSquared <= 500*500){
                currentLodLevel = 0;
            } else if (distanceToCameraSquared <= 2500*2500) {
                currentLodLevel = 1;
            } else {
                currentLodLevel = 2;
            }

            int meshCountPerLod = model.getMeshCount()/lodCount;
            for(int i = currentLodLevel * meshCountPerLod; i < (currentLodLevel + 1) * meshCountPerLod; i++) {

                Mesh activeMesh = model.meshes().getArrayElement(i);

                Material activeMaterial = model.materials().getArrayElement(model.getMeshMaterial().get(i));

                drawMesh(activeMesh, activeMaterial, matrixIdentity());
            }
        }
        rlPopMatrix();
    }

    public void applyShader(Shader shader){
        if (this.model != null){

            for(int i = 0; i < this.model.materialCount(); i++) {
                this.model.materials().getArrayElement(i).shader(shader);
            }

            this.model.materials().getArrayElement(0);
        }
    }
}