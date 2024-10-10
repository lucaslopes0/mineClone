import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class teste {

    private long window;

    private boolean isFullscreen = false;
    Random rand = new Random();


    public void run(){
        System.out.println("HELLO LWJGL " + Version.getVersion());
        init();
        loop();

        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);

        glfwTerminate();

        glfwSetErrorCallback(null).free();
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new RuntimeException("Error: initialization failed");

        this.window = glfwCreateWindow(860, 460, "Minecraft", NULL, NULL);

        if(this.window == NULL)
            throw new RuntimeException("Error: GLFW window creation failed");

        GLFWImage.Buffer img = loadIcon(".//icons/mine.png");
        glfwSetWindowIcon(this.window,img);

        centralize();

        glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window,true);

            if(key == GLFW_KEY_M && action == GLFW_RELEASE)
                glClearColor(
                        this.rand.nextFloat()%255,
                        this.rand.nextFloat()%255,
                        this.rand.nextFloat()%255,
                        this.rand.nextFloat()%255
                );
            if(key ==  GLFW_KEY_F11 && action == GLFW_RELEASE)
                toggleFullscreen();
        });

        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(window, xpos, ypos);
        System.out.println("Cursor Position: X = " + xpos[0] + ", Y = " + ypos[0]);

        glfwSetCursorPosCallback(this.window, cursorPosition(this.window));


        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);
        glfwShowWindow(this.window);
    }

    private GLFWCursorPosCallback cursorPosition(long window) {
        return new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                System.out.println("Cursor position: X = " + xpos + ", Y = " + ypos);
            }
        };
    }

    private GLFWImage.Buffer loadIcon(String path) {
        GLFWImage.Buffer icon;

        try(MemoryStack stack = MemoryStack.stackPush()){
            ByteBuffer image;
            int[] width = new int[1];
            int[] height = new int[1];
            int[] channel = new int[1];


            image = stbi_load(path, width, height,channel,4);
            if(image == null)
                throw new RuntimeException("Failed to load image: "+stbi_failure_reason());

            icon = GLFWImage.malloc(1);
            icon.width(width[0]);
            icon.height(height[0]);
            icon.pixels(image);

            stbi_image_free(image);
        }
        return icon;
    }

    private void toggleFullscreen() {
        if(!this.isFullscreen){
            glfwSetWindowMonitor(window, 0,0,0,860,460,165);
            centralize();
            this.isFullscreen = true;
        }
        else{
            glfwWindowHint(GLFW_SOFT_FULLSCREEN, GLFW_TRUE);
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0,0, 1920,1080,165);
            centralize();
            this.isFullscreen = false;
        }
    }

    public void centralize(){
        try(MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(this.window,pWidth,pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(this.window,
                    (vidMode.width() - pWidth.get(0))/2,
                    (vidMode.height() - pHeight.get(0))/2
            );
        }
    }
    public void loop(){
        GL.createCapabilities();

        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);

        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(this.window);
            glfwPollEvents();
        }

    }

}
