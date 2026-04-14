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
    // Get base colors
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;

    // Hard cutoff for any microscopic invisible pixels (e.g. cutouts)
    if (texelColor.a < 0.1) discard;

    vec3 lightDir = normalize(lightDirection);
    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);

    float ambient = 0.2;
    float diff = max(dot(normal, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 8.0);
    float specular = 0.3 * spec;

    vec3 lighting = (ambient + diff + specular) * texelColor.rgb;

    // FORCE ALPHA TO 1.0 (100% Solid)
    finalColor = vec4(lighting, 1.0);
}