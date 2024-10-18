package View;



import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;


import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryUtil.memFree;


public class Renderer {

    private int vaoId;  // Declarar vaoId como variável de instância
    private int vboId;  // Também é recomendável declarar vboId, se ainda não tiver feito
    private ShaderProgram shaderProgram;  // Declarar o programa shader aqui


    public void init() throws Exception {
        // Criar o programa shader
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.FileLoader.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.FileLoader.loadResource("/fragment.fs"));
        shaderProgram.link();

        // Definir as coordenadas do triângulo
        float[] vertex = new float[]{
                 0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                 0.5f, -0.5f, 0.0f
        };

        // Buffer de memória
        FloatBuffer vertexBuffers = MemoryUtil.memAllocFloat(vertex.length);
        vertexBuffers.put(vertex).flip();

        // Gera o VAO
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Gera o VBO
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffers, GL_STATIC_DRAW);
        //memFree(vertexBuffers);

        // Especifica o layout dos dados de vértices
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Unbind the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Unbind the VAO
        glBindVertexArray(0);

        if (vertexBuffers != null) {
            MemoryUtil.memFree(vertexBuffers);
        }
    }

    public void render(Window window) {
        clear();  // Limpa a janela

        if ( window.isResized() ) {
                glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        // Vincular VAO e desenhar o triângulo
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        // Desenha 3 vértices que formam 1 triângulo
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindBuffer(GL_ARRAY_BUFFER,0);

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
        glDisableVertexAttribArray(0);
        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}


