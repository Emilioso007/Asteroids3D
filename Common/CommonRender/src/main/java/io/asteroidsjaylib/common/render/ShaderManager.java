package io.asteroidsjaylib.common.render;

import com.raylib.Raylib;
import com.raylib.Raylib.*;
import io.asteroidsjaylib.common.util.ResourceLoader;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;

import java.lang.module.ModuleReader;
import java.lang.module.ResolvedModule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.raylib.Raylib.LoadShader;

public class ShaderManager {
    private static final Map<String, Shader> shaderMap = new HashMap<>();
    private static boolean initialized = false;

    public static Shader getShader(String name){
        if (!initialized) initShaders();
        if (!shaderMap.containsKey(name)) throw new RuntimeException("No shader with name: " + name);

        return shaderMap.get(name);
    }

    private static void initShaders(){

        Module module = ShaderManager.class.getModule();

        if (!module.isNamed()) throw new RuntimeException("ShaderManager is not running inside a named module!");

        try {

            ResolvedModule resolvedModule = module.getLayer().configuration()
                    .findModule(module.getName())
                    .orElseThrow(() -> new RuntimeException("Could not resolve Module"));

            try (ModuleReader reader = resolvedModule.reference().open()){

                String vsAbsolutePath = ResourceLoader.getAsAbsolutePath("/"+reader.list()
                        .filter(path -> path.endsWith(".vs"))
                        .findFirst().orElseThrow(() -> new RuntimeException("No .vs shader provided!")));

                List<String> shaderFiles = reader.list()
                        .filter(path -> path.endsWith(".fs"))
                        .toList();

                for (String shaderPath : shaderFiles){

                    String keyName = new java.io.File(shaderPath).getName().replace(".fs", "");
                    shaderMap.put(keyName, LoadShader(vsAbsolutePath, ResourceLoader.getAsAbsolutePath("/"+shaderPath)));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        initialized = true;

    }

    public static void setGlobalShaderValue(String uniformName, Vector3 vector3, int uniformType) {
        for (var shader : shaderMap.values()){

            int loc = Raylib.GetShaderLocation(shader, uniformName);

            if (loc != -1){
                Raylib.SetShaderValue(shader, loc, vector3, uniformType);
            }

        }
    }

    public static void setGlobalShaderValue(String uniformName, float[] values, int uniformType) {

        try (FloatPointer nativePointer = new FloatPointer(values)){

            for (var shader : shaderMap.values()){

                int loc = Raylib.GetShaderLocation(shader, uniformName);

                if (loc != -1){
                    Raylib.SetShaderValue(shader, loc, nativePointer, uniformType);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setGlobalShaderValue(String uniformName, float value, int uniformType) {

        try (FloatPointer nativePointer = new FloatPointer(1).put(value)){

            for (var shader : shaderMap.values()){

                int loc = Raylib.GetShaderLocation(shader, uniformName);

                if (loc != -1){
                    Raylib.SetShaderValue(shader, loc, nativePointer, uniformType);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setGlobalShaderValue(String uniformName, int value, int uniformType) {

        try (IntPointer nativePointer = new IntPointer(1).put(value)){

            for (var shader : shaderMap.values()){

                int loc = Raylib.GetShaderLocation(shader, uniformName);

                if (loc != -1){
                    Raylib.SetShaderValue(shader, loc, nativePointer, uniformType);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
