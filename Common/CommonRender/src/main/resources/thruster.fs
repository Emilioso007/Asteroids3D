#version 330

in vec2 fragTexCoord;
in vec4 fragColor;
in vec3 localPosition;

out vec4 finalColor;

uniform sampler2D texture0;
uniform vec4 colDiffuse;
uniform float time;

void main()
{
    // Gets the base color of the LEGO piece
    vec4 texelColor = texture(texture0, fragTexCoord) * colDiffuse * fragColor;

    // 1. GENERIC SPATIAL DESYNC
    // By adding the X, Y, and Z local coordinates and scaling them way down,
    // we create a smooth 3D wave. It's small enough to avoid CRT scanlines on a
    // single piece, but large enough that engines on opposite sides of a ship
    // will pulse out of sync naturally.
    float spatialScale = 0.08;
    float spatialOffset = (localPosition.x + localPosition.y + localPosition.z) * spatialScale;

    // 2. THE PLASMA WAVE
    // Base time multiplied by speed, offset by the engine's physical location
    float waveSpeed = 40.0;
    float wave = (time * waveSpeed) + spatialOffset;

    // 3. BASE THROB
    // A soft, continuous pulsing multiplier between 0.8 and 1.2
    float pulse = (sin(wave) * 0.2) + 1.0;
    vec3 baseGlow = texelColor.rgb * pulse * 1.5;

    // 4. THE HOT CORE (FLICKER)
    // We use a slightly offset, faster wave and a power function (pow)
    // to create violent, sharp spikes of white-hot energy
    float coreIntensity = pow(max(sin(wave * 1.3), 0.0), 3.0);
    vec3 hotCore = vec3(1.0, 0.9, 1.0) * coreIntensity * 0.4;

    // 5. FINAL BLEND
    finalColor = vec4(baseGlow + hotCore, texelColor.a);
}