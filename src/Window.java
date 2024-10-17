import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window{
    private long windowHandle;
    private int width, height;
    private boolean resized = false;
    private boolean isFullscreen = false;
    private String title;
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

    public boolean isVSync() {
        return vSync;
    }

    public long getWindowHandle(){return this.windowHandle;}

    public void centralize(){
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

    public void turnFullScreen(){
        if(!this.isFullscreen){
            glfwSetWindowMonitor(this.windowHandle, 0,0,0,600,480,165);
            centralize();
            this.isFullscreen = true;
        }
    }

    public void turnWindowed(){
        glfwWindowHint(GLFW_SOFT_FULLSCREEN, GLFW_TRUE);
        glfwSetWindowMonitor(this.windowHandle, glfwGetPrimaryMonitor(), 0,0, 1920,1080,165);
        centralize();
        this.isFullscreen = false;
    }

    public void toggleFullscreen() {
        if(!this.isFullscreen){
            glfwSetWindowMonitor(this.windowHandle, 0,0,0,600,480,165);
            centralize();
            this.isFullscreen = true;
        }
        else{
            glfwWindowHint(GLFW_SOFT_FULLSCREEN, GLFW_TRUE);
            glfwSetWindowMonitor(this.windowHandle, glfwGetPrimaryMonitor(), 0,0, 1920,1080,165);
            centralize();
            this.isFullscreen = false;
        }
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public void update() {
        glfwSwapBuffers(this.windowHandle);
        glfwPollEvents();
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(this.windowHandle);
    }
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void cleanup() {
        glfwFreeCallbacks(this.windowHandle);
        glfwDestroyWindow(this.windowHandle);
        glfwTerminate();
    }
}
