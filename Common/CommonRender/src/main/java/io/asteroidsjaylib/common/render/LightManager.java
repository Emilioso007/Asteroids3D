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

    private static final float MAP_BOUNDS = 10000f;
    private static final float MAP_SIZE = 20000f;
    private static final float BORDER_DIST = 5000f;

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

        // Calculate the optional ghost offset for each axis (0f if not near a border)
        float ghostX = (px > MAP_BOUNDS - BORDER_DIST) ? -MAP_SIZE : (px < -MAP_BOUNDS + BORDER_DIST) ? MAP_SIZE : 0f;
        float ghostY = (py > MAP_BOUNDS - BORDER_DIST) ? -MAP_SIZE : (py < -MAP_BOUNDS + BORDER_DIST) ? MAP_SIZE : 0f;
        float ghostZ = (pz > MAP_BOUNDS - BORDER_DIST) ? -MAP_SIZE : (pz < -MAP_BOUNDS + BORDER_DIST) ? MAP_SIZE : 0f;

        // Loop 1 or 2 times per axis based on whether a ghost offset exists
        int xCount = (ghostX != 0f) ? 2 : 1;
        int yCount = (ghostY != 0f) ? 2 : 1;
        int zCount = (ghostZ != 0f) ? 2 : 1;

        for (int ix = 0; ix < xCount; ix++) {
            float ox = (ix == 0) ? 0f : ghostX; // 1st pass: no offset. 2nd pass: apply ghost offset.

            for (int iy = 0; iy < yCount; iy++) {
                float oy = (iy == 0) ? 0f : ghostY;

                for (int iz = 0; iz < zCount; iz++) {
                    float oz = (iz == 0) ? 0f : ghostZ;

                    if (activeCount >= MAX_LIGHTS) return;

                    // Apply the offsets safely
                    lightPositions[activeCount].x(px + ox).y(py + oy).z(pz + oz);
                    lightColors[activeCount].x(r).y(g).z(b);
                    lightRadii[activeCount] = radius;

                    activeCount++;
                }
            }
        }
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
