package org.view;

import org.graphics.Mesh;

import static org.lwjgl.opengl.GL30.*;


public class Renderer {
    public void renderMesh(Mesh mesh){
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glDrawElements(GL_TRIANGLES,mesh.getIndices().length, GL_UNSIGNED_INT,0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
    public void render(Window window) {
        clear();  // Limpa a janela

        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
    }
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}


