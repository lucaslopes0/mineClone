package View;

import Controller.InputController;
import Model.IGameLogic;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class GameView implements IGameLogic {
    private float r = 0.0f;
    private float g = 0.0f;
    private float b = 0.0f;
    private final Renderer renderer;
    private boolean isF11Pressed = false;
    private boolean isPressed = false;
    private boolean isESCPressed = false;
    Random rand = new Random();

    public GameView() throws Exception {
        this.renderer = new Renderer();
        this.renderer.init();
    }

    @Override
    public void init() throws Exception {
        this.renderer.init();
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


        if (input.isKeyPressed(GLFW_KEY_M)){
            if (!this.isPressed) {
                this.r = this.rand.nextFloat();
                this.g = this.rand.nextFloat();
                this.b = this.rand.nextFloat();
                this.isPressed = true;
            }
        }else this.isPressed = false;


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
        renderer.render(window);
        /*
        window.setClearColor(this.r%255, this.g%255, this.b%255, 0.0f);
        this.renderer.clear();*/
    }

    @Override
    public void cleanup() {
        // Limpeza de recursos
    }
}

