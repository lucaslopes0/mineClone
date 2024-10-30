#version 330 core

in vec4 vertexColor;
out vec4 FragColor;
uniform vec4 u_Color;  // Recebe a cor definida pelo programa Java

void main() {
    FragColor = vertexColor;  // Usa a cor do vértice
}
