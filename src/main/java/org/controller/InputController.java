package org.controller;

import static org.lwjgl.glfw.GLFW.*;

import org.view.*;

public class InputController {

    private final Window window;

    public InputController(Window window){
        this.window = window;
    }


    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(this.window.getWindowHandle(), keyCode) == GLFW_PRESS;
    }

    public boolean isMousePressed(int mButton){
        return glfwGetMouseButton(this.window.getWindowHandle(), mButton) == GLFW_PRESS;
    }

    public void cursorTracking(){
        glfwSetCursorPosCallback(this.window.getWindowHandle(), (window1, xpos, ypos)->{
            double[] x = new double[1];
            double[] y = new double[1];

            glfwGetCursorPos(window1, x, y);
            System.out.println("Cursor : X = " +xpos+ ", Y = "+ypos);
        });
    }

    public void setMouseScroll(){
        glfwSetScrollCallback(this.window.getWindowHandle(), ((window1, xoffset, yoffset)->{
            if (yoffset == 1.0)
                System.out.println("Up Scrolling");
            else
                System.out.println("Down Scrolling");
        }));
    }
}
