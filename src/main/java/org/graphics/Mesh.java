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
        this.vao = glGenVertexArrays();
        glBindVertexArray(vao);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(this.vertices.length*3);
        float[] positionData =  new float[this.vertices.length * 3];
        for (int i = 0; i < this.vertices.length; i++) {
            positionData[i * 3] = this.vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = this.vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = this.vertices[i].getPosition().getZ();
        }
        positionBuffer.put(positionData).flip();

        pbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.pbo);
        glBufferData(GL_ARRAY_BUFFER,positionBuffer,GL_STATIC_DRAW);
        glVertexAttribPointer(0,3, GL_FLOAT,false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER,0);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(this.indices.length);
        indicesBuffer.put(this.indices).flip();

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

    }
}
