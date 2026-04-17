#version 330

in vec2 fragTexCoord;
in vec3 fragNormal;
in vec3 fragPosition;
in vec4 fragColor;

out vec4 finalColor;

uniform sampler2D texture0;
uniform vec4 colDiffuse;

// The Sun
uniform vec3 lightDirection;
uniform vec3 viewPos;

// ==========================================
// THE DYNAMIC LIGHT SYSTEM (AREA LIGHTS)
// ==========================================
#define MAX_LIGHTS 400

uniform int activeLightCount;
uniform vec3 lightPositions[MAX_LIGHTS];
uniform vec3 lightColors[MAX_LIGHTS];
uniform float lightRadii[MAX_LIGHTS];

void main()
{
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;

    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);

    // ==========================================
    // 1. The Sun Math
    // ==========================================
    vec3 sunLightDir = normalize(lightDirection);

    float ambient = 0.4; // Higher ambient for glass
    float sunDiff = max(dot(normal, sunLightDir), 0.0);

    vec3 sunReflectDir = reflect(-sunLightDir, normal);
    float sunSpec = pow(max(dot(viewDir, sunReflectDir), 0.0), 32.0); // Sharper specular for glass
    float sunSpecular = 0.8 * sunSpec;

    vec3 sunLighting = (ambient + sunDiff + sunSpecular) * texelColor.rgb;

    // ==========================================
    // 2. The Spherical Area Lights
    // ==========================================
    vec3 totalPointLighting = vec3(0.0);
    vec3 viewReflectDir = reflect(-viewDir, normal);

    for (int i = 0; i < activeLightCount; i++)
    {
        vec3 lightCenterVector = lightPositions[i] - fragPosition;
        float centerDist = length(lightCenterVector);

        // --- 1. SURFACE ATTENUATION ---
        float surfaceDist = max(centerDist - lightRadii[i], 0.0);
        float attenuation = 1.0 / (1.0 + 0.05 * surfaceDist + 0.01 * (surfaceDist * surfaceDist));

        // --- 2. VOLUME-AWARE DIFFUSE ---
        vec3 diffusePos = lightPositions[i] + normal * lightRadii[i];
        vec3 diffuseVec = diffusePos - fragPosition;
        float diffDist = length(diffuseVec);

        vec3 diffuseDir = diffDist < 0.001 ? normal : diffuseVec / diffDist;
        float pointDiff = max(dot(normal, diffuseDir), 0.0);

        // --- 3. AREA LIGHT SPECULAR ---
        vec3 safeCenterVector = centerDist < 0.001 ? normal : lightCenterVector;

        vec3 centerToRay = (dot(safeCenterVector, viewReflectDir) * viewReflectDir) - safeCenterVector;
        float rayDist = length(centerToRay);

        vec3 closestPoint = safeCenterVector + centerToRay * clamp(lightRadii[i] / max(rayDist, 0.001), 0.0, 1.0);
        float specDist = length(closestPoint);

        vec3 specLightDir = specDist < 0.001 ? normal : closestPoint / specDist;

        float pointSpec = pow(max(dot(viewReflectDir, specLightDir), 0.0), 32.0); // Glass specular
        float pointSpecular = 0.8 * pointSpec;

        if (pointDiff <= 0.0) pointSpecular = 0.0;

        vec3 lightContribution = (pointDiff + pointSpecular) * lightColors[i] * attenuation;
        totalPointLighting += (lightContribution * texelColor.rgb);
    }

    // Preserve Alpha for glass transparency
    finalColor = vec4(sunLighting + totalPointLighting, texelColor.a);
}