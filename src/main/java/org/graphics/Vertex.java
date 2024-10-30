package org.graphics;

import org.maths.*;

public class Vertex {

    private VectorPosition position;
    private VectorColor color;

    public Vertex(VectorPosition position, VectorColor color){
        this.position = position;
        this.color = color;
    }

    public VectorPosition getPosition() {return this.position;}

    public VectorColor getColor(){return this.color;}

    public void setPosition(VectorPosition position) {
        this.position = position;
    }

    public void setColor(VectorColor color){this.color = color;}

}
