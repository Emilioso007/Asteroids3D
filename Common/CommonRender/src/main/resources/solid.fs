#version 330

in vec2 fragTexCoord;
in vec3 fragNormal;
in vec3 fragPosition;
in vec4 fragColor;

out vec4 finalColor;

uniform sampler2D texture0;
uniform vec4 colDiffuse;

// The Sun (Directional Light)
uniform vec3 lightDirection;
uniform vec3 viewPos;

// THE NEW POINT LIGHT (The Thruster)
uniform vec3 thrusterLightPos;
uniform float thrusterIntensity;
uniform float time; // We will use the same time variable to make the cast light flicker!

void main()
{
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;

    if (texelColor.a < 0.1) discard;

    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);

    // ==========================================
    // 1. THE SUN (Directional Light)
    // ==========================================
    vec3 sunLightDir = normalize(lightDirection);
    float ambient = 0.2;
    float sunDiff = max(dot(normal, sunLightDir), 0.0);

    vec3 reflectDir = reflect(-sunLightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 8.0);
    float sunSpecular = 0.3 * spec;

    vec3 sunLighting = (ambient + sunDiff + sunSpecular) * texelColor.rgb;

    // ==========================================
    // 2. THE THRUSTER (Point Light)
    // ==========================================
    vec3 pointLighting = vec3(0.0); // Default to pitch black

    // ONLY do the math if the thruster is actually alive/on!
    if (thrusterIntensity > 0.0) {
        vec3 pointLightDir = thrusterLightPos - fragPosition;
        float distance = length(pointLightDir);
        pointLightDir = normalize(pointLightDir);

        float attenuation = 1.0 / (1.0 + 0.05 * distance + 0.01 * (distance * distance));
        float pointDiff = max(dot(normal, pointLightDir), 0.0);

        float pulse = (sin(time * 30.0) * 0.2) + 1.0;
        vec3 orangeFireColor = vec3(1.0, 0.5, 0.0) * pulse * 10.0;

        // Multiply by intensity so you can optionally fade it in/out later!
        pointLighting = pointDiff * orangeFireColor * attenuation * texelColor.rgb * thrusterIntensity;
    }

    // ==========================================
    // 3. COMBINE EVERYTHING
    // ==========================================
    finalColor = vec4(sunLighting + pointLighting, 1.0);
}