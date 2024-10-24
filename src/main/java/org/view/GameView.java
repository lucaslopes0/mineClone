package org.view;

import org.controller.*;
import org.engine.IGameLogic;
import org.maths.Vector;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class GameView implements IGameLogic {

    private final Renderer renderer;
    private boolean isF11Pressed = false;
    private boolean isPressed = false;
    private boolean isESCPressed = false;
    Random rand = new Random();
    private Vector background = new Vector(this.rand.nextFloat(),this.rand.nextFloat(),this.rand.nextFloat());

    public GameView() throws Exception {
        this.renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
            //
    }

    @Override
    public void input(InputController input, Window window) {
        
        if (input.isKeyPressed(GLFW_KEY_ESCAPE)){
            if(!this.isESCPressed){
                glfwSetWindowShouldClose(window.getWindowHandle(),true);
                this.isESCPressed = true;
            }
        }else this.isESCPressed = false;

        if (input.isKeyPressed(GLFW_KEY_F11)) {
            if (!this.isF11Pressed){
                if (window.isFullscreen())
                    window.setWindowed();
                else
                    window.setFullscreen();

                this.isF11Pressed = true;
            }
        }else this.isF11Pressed = false;


        if (input.isMousePressed(GLFW_MOUSE_BUTTON_1))
            System.out.println("Left button pressed");
        else if (input.isMousePressed(GLFW_MOUSE_BUTTON_2))
            System.out.println("Right button pressed");


        input.setMouseScroll();
        input.cursorTracking();
    }

    @Override
    public void update(float interval) {
        //
    }
    @Override
    public void render(Window window) {
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
        window.setClearColor(this.background.getX(), this.background.getY(), this.background.getZ(), 0.0f);
    }

    @Override
    public void cleanup() {
        // Limpeza de recursos
    }
}

