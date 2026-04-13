package io.asteroidsjaylib.common.render.shapes3d;

import com.raylib.Raylib.Model;
import io.asteroidsjaylib.common.util.ResourceLoader;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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

        try{
            var caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

            Path tempDir = Files.createTempDirectory("asteroids_models_glb_");
            tempDir.toFile().deleteOnExit();

            String glbFilename = new File(glbPath).getName();

            File tempGlbFile = new File(tempDir.toFile(), glbFilename);

            InputStream glbStream = caller.getResourceAsStream(glbPath);
            if (glbStream == null) throw new RuntimeException("Could not find GLB resource: " + glbPath);
            Files.copy(glbStream, tempGlbFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            glbStream.close();

            this.model = LoadModel(tempGlbFile.getAbsolutePath());
            modelCache.put(glbPath, this.model);

            tempGlbFile.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load model: " + glbPath);
        }

    }

    @Deprecated
    public Model3D(String objPath, String mtlPath, float scale, float pitchOffset, float yawOffset, float rollOffset) {
        this.scale = scale;
        this.pitchOffset = pitchOffset;
        this.yawOffset = yawOffset;
        this.rollOffset = rollOffset;

        if(modelCache.containsKey(objPath)){
            this.model = modelCache.get(objPath);
            return;
        }

        Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

        String modelPath = ResourceLoader.getAsAbsolutePath(objPath, caller);
        ResourceLoader.getAsAbsolutePath(mtlPath, caller);

        this.model = LoadModel(modelPath);
        modelCache.put(objPath, this.model);
    }

    @Override
    public void draw() {
        if (!active) return;

        rlPushMatrix();
        rlRotatef(yawOffset, 0, 0, 1);
        rlRotatef(pitchOffset, 1, 0, 0);
        rlRotatef(rollOffset, 0, 1, 0);

        DrawModel(model, new Vector3(), scale, WHITE);
        //DrawModelWires(model, new Vector3(), scale, BLACK);

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