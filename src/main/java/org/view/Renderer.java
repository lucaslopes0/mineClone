package org.view;

import org.graphics.*;

import static org.lwjgl.opengl.GL30.*;


public class Renderer {
    private ShadersProgram shaderProgram;

    public void init() throws Exception {
        // Inicializa os shaders
        this.shaderProgram = new ShadersProgram("./src/main/java/org/vertex.glsl", "./src/main/java/org/fragment.glsl");
    }
    public void render(Mesh mesh) {
        this.shaderProgram.bind(); // Ativa o programa de shaders

        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0); // Posição
        glEnableVertexAttribArray(1); // Cor

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shaderProgram.unbind(); // Desativa o programa de shaders
        /*clear();  // Limpa a janela

        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glDrawArrays(GL_TRIANGLES,0,3);
        //glDrawElements(GL_TRIANGLES,mesh.getIndices().length, GL_UNSIGNED_INT,0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);*/
    }
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}


