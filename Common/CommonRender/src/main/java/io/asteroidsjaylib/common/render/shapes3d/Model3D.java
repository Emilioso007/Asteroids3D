package io.asteroidsjaylib.common.render.shapes3d;

import com.raylib.Raylib;
import com.raylib.Raylib.Model;

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

    public Model3D(String objPath, String mtlPath, float scale, float pitchOffset, float yawOffset, float rollOffset) {
        this.scale = scale;
        this.pitchOffset = pitchOffset;
        this.yawOffset = yawOffset;
        this.rollOffset = rollOffset;

        if(modelCache.containsKey(objPath)){
            this.model = modelCache.get(objPath);
            return;
        }

        try {
            Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

            // --- Create a dedicated temporary directory ---
            Path tempDir = Files.createTempDirectory("asteroids_models_");
            tempDir.toFile().deleteOnExit();

            // Extract the original filenames
            String objFilename = new File(objPath).getName();
            String mtlFilename = new File(mtlPath).getName();

            // Setup the exact file paths inside our new temp directory
            File tempObjFile = new File(tempDir.toFile(), objFilename);
            File tempMtlFile = new File(tempDir.toFile(), mtlFilename);

            // --- 1. EXTRACT THE OBJ ---
            InputStream objStream = caller.getResourceAsStream(objPath);
            if (objStream == null) throw new RuntimeException("Could not find OBJ resource: " + objPath);
            Files.copy(objStream, tempObjFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            objStream.close();

            // --- 2. EXTRACT THE MTL ---
            InputStream mtlStream = caller.getResourceAsStream(mtlPath);
            if (mtlStream == null) throw new RuntimeException("Could not find MTL resource: " + mtlPath);
            Files.copy(mtlStream, tempMtlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            mtlStream.close();

            // --- 3. LOAD INTO RAYLIB ---
            // Because the correctly named .mtl is sitting in the same folder as the .obj,
            // Raylib will automatically find it and apply the colors!
            this.model = LoadModel(tempObjFile.getAbsolutePath());
            modelCache.put(objPath, this.model);

            // Tell Java to clean up when the game closes
            tempObjFile.deleteOnExit();
            tempMtlFile.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load model: " + objPath);
        }
    }

    @Override
    public void draw() {
        rlPushMatrix();
        rlRotatef(yawOffset, 0, 0, 1);
        rlRotatef(pitchOffset, 1, 0, 0);
        rlRotatef(rollOffset, 0, 1, 0);

        DrawModel(model, new Raylib.Vector3().x(0).y(0).z(0), scale, WHITE);

        rlPopMatrix();
    }
}