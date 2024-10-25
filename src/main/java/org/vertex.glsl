#version 330 core

layout(location = 0) in vec3 aPos;  // Posição
layout(location = 1) in vec4 aColor; // Cor

out vec4 vertexColor; // Passa para o Fragment Shader

void main() {
    gl_Position = vec4(aPos, 1.0);
    vertexColor = aColor;
}
