package io.asteroidsjaylib.common.render;

import io.asteroidsjaylib.common.util.ResourceLoader;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import java.util.HashMap;
import java.util.Map;

public class Model3D extends Base3DShape {

    private static final Map<String, Model> modelCache = new HashMap<>();

    public Model model;
    public float scale;
    public float pitchOffset, yawOffset, rollOffset;

    public boolean active = true;

    public Model3D(String glbPath, float scale, float pitchOffset, float yawOffset, float rollOffset){
        this.scale = scale;
        this.pitchOffset = pitchOffset;
        this.yawOffset = yawOffset;
        this.rollOffset = rollOffset;

        if (modelCache.containsKey(glbPath)){
            this.model = modelCache.get(glbPath);
            return;
        }

        this.model = LoadModel(ResourceLoader.getAsAbsolutePath(glbPath, StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass()));
        modelCache.put(glbPath, this.model);

    }

    @Override
    public void draw() {
        if (!active) return;

        rlPushMatrix();
        rlRotatef(yawOffset, 0, 0, 1);
        rlRotatef(pitchOffset, 1, 0, 0);
        rlRotatef(rollOffset, 0, 1, 0);

        DrawModel(model, new Vector3(), scale, WHITE);

        rlPopMatrix();
    }

    public void applyShader(Shader shader){
        if (this.model != null){

            for(int i = 0; i < this.model.materialCount(); i++) {
                this.model.materials().position(i).shader(shader);
            }

            this.model.materials().position(0);
        }
    }
}