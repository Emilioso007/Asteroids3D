#version 330

in vec2 fragTexCoord;
in vec3 fragNormal;
in vec3 fragPosition;
in vec4 fragColor;

out vec4 finalColor;

uniform sampler2D texture0;
uniform vec4 colDiffuse;
uniform vec3 lightDirection;
uniform vec3 viewPos;

void main()
{
    // Get base colors (Including the Alpha slider from Blender!)
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;

    vec3 lightDir = normalize(lightDirection);
    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);

    // Increase ambient light slightly for glass so it doesn't look like dark plastic
    float ambient = 0.4;
    float diff = max(dot(normal, lightDir), 0.0);

    // Glass should be VERY shiny!
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32.0); // Tighter, sharper highlight
    float specular = 0.8 * spec; // Brighter reflection

    vec3 lighting = (ambient + diff + specular) * texelColor.rgb;

    // USE THE ACTUAL ALPHA (No dithering, no forced 1.0)
    // If the Blender material is 0.5 alpha, it will draw perfectly semi-transparent.
    finalColor = vec4(lighting, texelColor.a);
}