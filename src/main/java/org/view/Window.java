package org.view;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window{
    private long windowHandle;
    private int width, height;
    private boolean resized = false;
    private boolean isFullscreen = false;
    private final String title;
    private boolean vSync;

    //construtor da janela
    public Window(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
    }

    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //hints
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        //window creation
        this.windowHandle = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        centralize();

        if (this.windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(this.windowHandle);
        if (vSync) {
            glfwSwapInterval(1); // Habilita v-sync
        }

        glfwShowWindow(this.windowHandle);
        GL.createCapabilities();

        glfwSetFramebufferSizeCallback(this.windowHandle, (window, w, h) -> {
            this.width = w;
            this.height = h;
            this.resized = true;
        });
    }

    public long getWindowHandle(){return this.windowHandle;}

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width){this.width = width;}

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height){this.height = height;}

    public boolean isResized() {return resized;}

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean isvSync() {return vSync;}

    public void setVSync(boolean vSync){this.vSync = vSync;}

    public boolean windowShouldClose(){return glfwWindowShouldClose(this.windowHandle);}

    public boolean isFullscreen(){return this.isFullscreen;}

    public void setWindowed() {
        configureWindow(0, 600, 480, false);
    }

    public void setFullscreen() {
        glfwWindowHint(GLFW_SOFT_FULLSCREEN, GLFW_TRUE);
        configureWindow(glfwGetPrimaryMonitor(), 1920, 1080, true);
    }

    public void update() {
        glfwSwapBuffers(this.windowHandle);
        glfwPollEvents();
    }

    public void cleanup() {
        glfwFreeCallbacks(this.windowHandle);
        glfwDestroyWindow(this.windowHandle);
        glfwTerminate();
    }

    private void configureWindow(long monitor, int width, int height, boolean fullscreen) {
        glfwSetWindowMonitor(this.windowHandle, monitor, 0, 0, width, height, 165);
        centralize();  // Centraliza a janela
        this.isFullscreen = fullscreen;
    }

    private void centralize(){
        try(MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(this.windowHandle,pWidth,pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidMode != null;
            glfwSetWindowPos(this.windowHandle,
                    (vidMode.width() - pWidth.get(0))/2,
                    (vidMode.height() - pHeight.get(0))/2
            );
        }
    }
}
