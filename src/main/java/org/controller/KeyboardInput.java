package org.controller;

import org.view.*;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput {

    private final Window window;

    public KeyboardInput(Window window){
        this.window = window;
    }


    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(this.window.getWindowHandle(), keyCode) == GLFW_PRESS;
    }
}
