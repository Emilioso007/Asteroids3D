#version 330

in vec2 fragTexCoord;
in vec4 fragColor;

out vec4 finalColor;

uniform sampler2D texture0;
uniform vec4 colDiffuse;
uniform float time;

void main()
{
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;

    // 1. A softer pulse that waves between 0.8 and 1.2
    float pulse = (sin(time * 30.0) * 0.2) + 1.0;

    // 2. Keep the multiplier low enough (1.5) so orange doesn't turn into yellow
    vec3 baseGlow = texelColor.rgb * pulse * 1.5;

    // 3. Only inject the white-hot core at the absolute peak of the sine wave
    // The pow() function makes the spike very sharp and fast, like an actual flicker
    float coreIntensity = pow(max(sin(time * 30.0), 0.0), 4.0);
    vec3 hotCore = vec3(1.0, 0.8, 0.6) * coreIntensity * 0.5;

    finalColor = vec4(baseGlow + hotCore, texelColor.a);
}