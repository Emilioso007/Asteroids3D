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
// THE DYNAMIC LIGHT SYSTEM
// ==========================================
#define MAX_LIGHTS 4

uniform int activeLightCount; // How many lights are actually ON right now?
uniform vec3 lightPositions[MAX_LIGHTS];
uniform vec3 lightColors[MAX_LIGHTS];

void main()
{
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;
    if (texelColor.a < 0.1) discard;

    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);

    // 1. The Sun Math (Same as before)
    vec3 sunLightDir = normalize(lightDirection);
    float ambient = 0.2;
    float sunDiff = max(dot(normal, sunLightDir), 0.0);
    vec3 reflectDir = reflect(-sunLightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 8.0);
    float sunSpecular = 0.3 * spec;
    vec3 sunLighting = (ambient + sunDiff + sunSpecular) * texelColor.rgb;

    // 2. The Dynamic Points Lights
    vec3 totalPointLighting = vec3(0.0);

    // Loop through however many lights Java told us are active!
    for (int i = 0; i < activeLightCount; i++)
    {
        vec3 pointLightDir = lightPositions[i] - fragPosition;
        float distance = length(pointLightDir);
        pointLightDir = normalize(pointLightDir);

        // Calculate falloff
        float attenuation = 1.0 / (1.0 + 0.05 * distance + 0.01 * (distance * distance));

        float pointDiff = max(dot(normal, pointLightDir), 0.0);

        // Add this light's contribution to the total!
        totalPointLighting += (pointDiff * lightColors[i] * attenuation * texelColor.rgb);
    }

    // Combine sun and all point lights!
    finalColor = vec4(sunLighting + totalPointLighting, 1.0);
}