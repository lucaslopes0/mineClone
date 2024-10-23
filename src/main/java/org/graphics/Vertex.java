package org.graphics;

import org.maths.Vector;

public class Vertex {

    private Vector position;

    public Vertex(Vector position){
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
