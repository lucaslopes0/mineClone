import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;

public class GameFicticio implements IGameLogic {
    private float r = 0.0f;
    private float g = 0.0f;
    private float b = 0.0f;
    private final Renderer renderer;
    private boolean isF11Pressed = false;
    private boolean isPressed = false;
    private boolean isESCPressed = false;
    Random rand = new Random();

    public GameFicticio() {
        this.renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        this.renderer.init();
    }

    @Override
    public void input(Window window) {

        if (window.isKeyPressed(GLFW_KEY_ESCAPE)){
            if(!this.isESCPressed){
                glfwSetWindowShouldClose(window.getWindowHandle(),true);
                this.isESCPressed = true;
            }
        }else this.isESCPressed = false;

        if (window.isKeyPressed(GLFW_KEY_F11)) {
            if (!this.isF11Pressed) {
                window.toggleFullscreen();
                this.isF11Pressed = true;
            }
        }else this.isF11Pressed = false;


        if (window.isMousePressed(GLFW_MOUSE_BUTTON_1))
            System.out.println("Left button pressed");
        else if (window.isMousePressed(GLFW_MOUSE_BUTTON_2))
            System.out.println("Right button pressed");


        if (window.isKeyPressed(GLFW_KEY_M)){
            if (!this.isPressed) {
                this.r = this.rand.nextFloat();
                this.g = this.rand.nextFloat();
                this.b = this.rand.nextFloat();
                this.isPressed = true;
            }
        }else this.isPressed = false;


        window.setMouseScroll();
        window.cursorTracking();
    }

    @Override
    public void update(float interval) {

    }

    @Override
    public void render(Window window) {
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
        window.setClearColor(this.r%255, this.g%255, this.b%255, 0.0f);
        this.renderer.clear();
    }

    @Override
    public void cleanup() {
        // Limpeza de recursos
    }
}

