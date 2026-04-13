#version 330
in vec3 fragPosition;
in vec2 fragTexCoord;
in vec3 fragNormal;
in vec4 fragColor;

out vec4 finalColor;

uniform vec3 lightDirection;
uniform vec3 viewPos;
uniform vec4 colDiffuse;
uniform sampler2D texture0;

void main()
{
    // 1. COMBINE ALL COLORS
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;

    // 2. VARIABLE DITHERED TRANSPARENCY (Bayer Matrix)
    if (texelColor.a < 0.99) { // Apply to anything that isn't 100% solid

        // Find exactly which pixel we are on within a 4x4 block
        int x = int(mod(gl_FragCoord.x, 4.0));
        int y = int(mod(gl_FragCoord.y, 4.0));

        // A standard 4x4 Bayer Matrix flattened into an array.
        // These numbers represent the 'threshold' for each pixel in the 4x4 grid.
        float ditherMatrix[16] = float[](
             1.0,  9.0,  3.0, 11.0,
            13.0,  5.0, 15.0,  7.0,
             4.0, 12.0,  2.0, 10.0,
            16.0,  8.0, 14.0,  6.0
        );

        // Look up the threshold for our specific pixel (0 to 15 index)
        int index = x + (y * 4);
        float limit = ditherMatrix[index] / 17.0; // Normalize the limit to a 0.0 - 1.0 range

        // If the actual Alpha of your Blender material is lower than this pixel's limit, delete it!
        if (texelColor.a < limit) {
            discard;
        }

        // Force the surviving pixels to be 100% solid so the depth buffer works
        texelColor.a = 1.0;
    }

    vec3 lightDir = normalize(lightDirection);
    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);

    float ambient = 0.2;
    float diff = max(dot(normal, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 8.0);
    float specular = 0.3 * spec;

    vec3 lighting = (ambient + diff + specular) * texelColor.rgb;

    finalColor = vec4(lighting, texelColor.a);
}