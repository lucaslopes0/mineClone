import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
public class Window {

    Random rand = new Random();
    private long window;

    private boolean isFullscreen = false;

    public void run(){
        System.out.println("LWJGL VERSION: "+ Version.getVersion());
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();

        glfwSetErrorCallback(null).free();
    }

    public void toggleFullscreen() {
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (isFullscreen) {
            // Mudar para modo janela
            glfwSetWindowMonitor(window, NULL, 100, 100, 400, 400, 0); // Tamanho e posição para janela
            centralize();
        } else {
            // Mudar para tela cheia
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, vidMode.width(), vidMode.height(), vidMode.refreshRate());
        }
        isFullscreen = !isFullscreen;  // Alternar estado
    }


    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new RuntimeException("Err");

        window = glfwCreateWindow(400, 400, "new", NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("err");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);


        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
            if (key == GLFW_KEY_M && action == GLFW_RELEASE)
                glClearColor(rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255);
            if (key == GLFW_KEY_F11 && action == GLFW_RELEASE) {
                toggleFullscreen();
            }
        });

        centralize();
    }
    public void centralize () {
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    public void loop(){
        GL.createCapabilities();

        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);

        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
