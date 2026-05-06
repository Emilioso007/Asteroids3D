package io.asteroidsjaylib.common.render;

import com.raylib.Shader;
import com.raylib.Vector3;
import io.asteroidsjaylib.common.util.ResourceLoader;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.module.ModuleReader;
import java.lang.module.ResolvedModule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.raylib.Raylib.*;

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
                    shaderMap.put(keyName, loadShader(vsAbsolutePath, ResourceLoader.getAsAbsolutePath("/"+shaderPath)));

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

            int loc = getShaderLocation(shader, uniformName);

            if (loc != -1){
                setShaderValue(shader, loc, vector3.memorySegment, uniformType);
            }

        }
    }

    public static void setGlobalShaderValue(String uniformName, float[] values, int uniformType) {

        try (Arena arena = Arena.ofConfined()){

            MemorySegment segment = arena.allocateFrom(ValueLayout.JAVA_FLOAT, values);

            for (var shader : shaderMap.values()){

                int loc = getShaderLocation(shader, uniformName);

                if (loc != -1){
                    setShaderValue(shader, loc, segment, uniformType);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setGlobalShaderValue(String uniformName, float value, int uniformType) {

        try (Arena arena = Arena.ofConfined()){

            MemorySegment segment = arena.allocateFrom(ValueLayout.JAVA_FLOAT, value);

            for (var shader : shaderMap.values()){

                int loc = getShaderLocation(shader, uniformName);

                if (loc != -1){
                    setShaderValue(shader, loc, segment, uniformType);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setGlobalShaderValue(String uniformName, int value, int uniformType) {

        try (Arena arena = Arena.ofConfined()){

            MemorySegment segment = arena.allocateFrom(ValueLayout.JAVA_INT, value);

            for (var shader : shaderMap.values()){


                int loc = getShaderLocation(shader, uniformName);

                if (loc != -1){
                    setShaderValue(shader, loc, segment, uniformType);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
