package org.graphics;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private Vertex[] vertices;
    private int[] indices;
    private int vao, pbo, ibo;
    public Mesh(Vertex[] vertices, int[] indices){
        this.vertices = vertices;
        this.indices = indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return vao;
    }

    public int getPBO() {
        return pbo;
    }

    public int getIBO() {
        return ibo;
    }

    public void create(){

        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length * 7);  // 3 posição + 4 cor

        for (Vertex vertex : vertices) {
            vertexBuffer.put(vertex.getPosition().getX());
            vertexBuffer.put(vertex.getPosition().getY());
            vertexBuffer.put(vertex.getPosition().getZ());

            vertexBuffer.put(vertex.getColor().getR());
            vertexBuffer.put(vertex.getColor().getG());
            vertexBuffer.put(vertex.getColor().getB());
            vertexBuffer.put(vertex.getColor().getA());
        }
        vertexBuffer.flip();  // Preparar para leitura

        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);

        this.pbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.pbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Definição do atributo de posição (location = 0)
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * Float.BYTES, 0);

        // Definição do atributo de cor (location = 1)
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * Float.BYTES, 3 * Float.BYTES);

        // Desvincula o buffer e o VAO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);


        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(this.indices.length);
        indicesBuffer.put(this.indices).flip();

        this.ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

    }
}
