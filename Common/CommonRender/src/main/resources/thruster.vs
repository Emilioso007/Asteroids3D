#version 330

in vec3 vertexPosition;
in vec2 vertexTexCoord;
in vec4 vertexColor;

out vec2 fragTexCoord;
out vec4 fragColor;
out vec3 localPosition; // The magic treadmill fix

uniform mat4 mvp;

void main()
{
    fragTexCoord = vertexTexCoord;
    fragColor = vertexColor;

    // Pass raw model space to fragment shader
    localPosition = vertexPosition;

    gl_Position = mvp * vec4(vertexPosition, 1.0);
}