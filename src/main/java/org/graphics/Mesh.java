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

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(this.vertices.length*7);
        float[] positionData =  new float[this.vertices.length * 7];
        for (int i = 0; i < this.vertices.length; i++) {
            positionData[i * 7] = this.vertices[i].getPosition().getX();
            positionData[i * 7 + 1] = this.vertices[i].getPosition().getY();
            positionData[i * 7 + 2] = this.vertices[i].getPosition().getZ();

            // Adiciona cor
            positionData[i * 7 + 3] = this.vertices[i].getColor().getR();
            positionData[i * 7 + 4] = this.vertices[i].getColor().getG();
            positionData[i * 7 + 5] = this.vertices[i].getColor().getB();
            positionData[i * 7 + 6] = this.vertices[i].getColor().getA();
        }
        positionBuffer.put(positionData).flip();

        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);

        this.pbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, this.pbo);
        glBufferData(GL_ARRAY_BUFFER,positionBuffer,GL_STATIC_DRAW);

        glVertexAttribPointer(0,3, GL_FLOAT,false, Float.BYTES*7,0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1,4, GL_FLOAT,false, Float.BYTES*7,Float.BYTES*3);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER,0);
        glBindVertexArray(0);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(this.indices.length);
        indicesBuffer.put(this.indices).flip();

        this.ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

    }
}
