package org.graphics;

import org.maths.VectorColor;
import org.maths.VectorPosition;

public class GeometricForms {

    public static Mesh drawStar(){
        return new Mesh(new Vertex[]{
                new Vertex(new VectorPosition(  0.0f, 0.85f,0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//0
                new Vertex(new VectorPosition( -0.1f, 0.5f, 0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//1
                new Vertex(new VectorPosition(  0.1f, 0.5f, 0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//2

                new Vertex(new VectorPosition(  -0.4f, 0.5f,0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//3
                new Vertex(new VectorPosition(  0.25f,-0.1f,0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//4

                new Vertex(new VectorPosition(  0.4f, 0.5f, 0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//5
                new Vertex(new VectorPosition(-0.25f,-0.1f, 0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//6
        }, new int[]{
                0,1,2,
                3,2,4,
                5,1,6,
        });
    }

    public static Mesh drawSquare(){
        return new Mesh(new Vertex[]{
                new Vertex(new VectorPosition(-0.5f,-0.5f,0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//0
                new Vertex(new VectorPosition(-0.5f, 0.5f,0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//1
                new Vertex(new VectorPosition( 0.5f,-0.5f,0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//2
                new Vertex(new VectorPosition( 0.5f, 0.5f,0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//3

        }, new int[]{
                0,1,2,
                3,2,1
        });
    }

    public static Mesh drawTriangle(){
        return new Mesh(new Vertex[]{
                new Vertex(new VectorPosition( 0.0f, 0.5f, 0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//0
                new Vertex(new VectorPosition( 0.5f,-0.5f, 0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//1
                new Vertex(new VectorPosition(-0.5f,-0.5f, 0.0f), new VectorColor(0.0f, 1.0f, 0.0f,1.0f)),//2
        }, new int[]{
                0,1,2,
        });
    }

    public static Mesh drawLinesTriangle(){
        return new Mesh(new Vertex[]{
                new Vertex(new VectorPosition( 0.0f, 0.5f, 0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//0
                new Vertex(new VectorPosition( 0.5f,-0.5f, 0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//1
                new Vertex(new VectorPosition(-0.5f,-0.5f, 0.0f), new VectorColor(0.0f, 0.85f, 0.0f,1.0f)),//2
        }, new int[]{
                0,1,
                0,2,
                1,2
        });
    }
}

