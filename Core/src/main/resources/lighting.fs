#version 330
in vec3 fragPosition;
in vec2 fragTexCoord;
in vec3 fragNormal;
in vec4 fragColor;

out vec4 finalColor;

uniform vec3 lightDirection;
uniform vec3 viewPos;
uniform vec4 colDiffuse;

void main()
{
    // A harsh, space-like directional light (e.g., a distant sun)
    vec3 lightDir = normalize(lightDirection);
    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);

    // Very low ambient light because space is dark
    float ambient = 0.2;

    // Diffuse lighting (bright where facing the sun)
    float diff = max(dot(normal, lightDir), 0.0);

    // Specular highlight (optional, makes it look a bit rocky/shiny)
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 8.0);
    float specular = 0.3 * spec;

    // Combine everything
    vec3 lighting = (ambient + diff + specular) * colDiffuse.rgb;
    finalColor = vec4(lighting, 1.0);
}