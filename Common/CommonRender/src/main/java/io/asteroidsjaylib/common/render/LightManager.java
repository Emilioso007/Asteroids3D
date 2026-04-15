package io.asteroidsjaylib.common.render;

import static com.raylib.Raylib.*;

public class LightManager {

    public static final int MAX_LIGHTS = 400;

    private static final Vector3[] lightPositions = new Vector3[MAX_LIGHTS];
    private static final Vector3[] lightColors = new Vector3[MAX_LIGHTS];
    private static final float[] lightRadii = new float[MAX_LIGHTS];

    private static final String[] posLocNames = new String[MAX_LIGHTS];
    private static final String[] colLocNames = new String[MAX_LIGHTS];
    private static final String[] radLocNames = new String[MAX_LIGHTS];

    private static int activeCount = 0;

    static {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            lightPositions[i] = new Vector3();
            lightColors[i] = new Vector3();
            lightRadii[i] = 0f;
            posLocNames[i] = "lightPositions[" + i + "]";
            colLocNames[i] = "lightColors[" + i + "]";
            radLocNames[i] = "lightRadii[" + i + "]";
        }
    }

    public static void addLightSphere(float px, float py, float pz, float radius, float r, float g, float b){
        if (activeCount >= MAX_LIGHTS) return;

        lightPositions[activeCount].x(px).y(py).z(pz);
        lightColors[activeCount].x(r).y(g).z(b);
        lightRadii[activeCount] = radius;

        activeCount++;
    }

    public static void addLightSource(float px, float py, float pz, float r, float g, float b) {
        addLightSphere(px, py, pz, 1f, r, g, b);
    }

    public static void applyLights(){
        ShaderManager.setGlobalShaderValue("activeLightCount", activeCount, SHADER_UNIFORM_INT);
        if (activeCount == 0) return;

        for (int i = 0; i < activeCount; i++) {
            ShaderManager.setGlobalShaderValue(posLocNames[i], lightPositions[i], SHADER_UNIFORM_VEC3);
            ShaderManager.setGlobalShaderValue(colLocNames[i], lightColors[i], SHADER_UNIFORM_VEC3);
            ShaderManager.setGlobalShaderValue(radLocNames[i], lightRadii[i], SHADER_UNIFORM_FLOAT);
        }

        activeCount = 0;
    }

}
