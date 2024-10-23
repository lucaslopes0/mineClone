package org.example;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SimplyWIndow {

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


        //Garante que o valor não seja nulo antes de chamar free(),
        //lançando uma NullPointerException se for nulo,
        //mas de forma mais controlada e previsível.
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();

    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new RuntimeException("Error: initialization failed");

        this.window = glfwCreateWindow(860, 460, "Minecraft", NULL, NULL);

        if(this.window == NULL)
            throw new RuntimeException("Error: GLFW window creation failed");


        //load icon
        GLFWImage.Buffer iconImage = loadImages(".//icons/mine.png");
        glfwSetWindowIcon(this.window,iconImage);

        //centralize window
        centralize();

        //keyboard events
        glfwSetKeyCallback(this.window, (window1, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                glfwSetWindowShouldClose(window1,true);

            if(key == GLFW_KEY_M && action == GLFW_PRESS)
                glClearColor(
                        this.rand.nextFloat()%255,
                        this.rand.nextFloat()%255,
                        this.rand.nextFloat()%255,
                        this.rand.nextFloat()%255
                );
            if(key ==  GLFW_KEY_F11 && action == GLFW_PRESS)
                toggleFullscreen();
        });

        //Cursor tracking
        glfwSetCursorPosCallback(this.window, (window1, xpos, ypos)->{
            double[] x = new double[1];
            double[] y = new double[1];

            glfwGetCursorPos(window1, x, y);
            System.out.println("Cursor : X = " +xpos+ ", Y = "+ypos);
        });

        // Load Cursor Icon
        GLFWImage.Buffer iconBuffer = loadImages(".//icons/cursor.png");
        iconBuffer.get(0);
        GLFWImage cursorImage = iconBuffer.get(0);

        long cursor = glfwCreateCursor(cursorImage, 0,0);
        glfwSetCursor(this.window,cursor);

        iconBuffer.free();

        //deixa o movimento do cursor ilimitado/ usado para controle de camera first person
        //glfwSetInputMode(this.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        //Mouse events
        glfwSetMouseButtonCallback(this.window, ((window1, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS)
                System.out.println("Right button pressed");
            int state = glfwGetMouseButton(window1, GLFW_MOUSE_BUTTON_LEFT);
            if (state == GLFW_PRESS)
                System.out.println("Pressing Left button");
        }));

        //Mouse scroll events
        glfwSetScrollCallback(this.window, ((window1, xoffset, yoffset) -> {
            if (yoffset == 1.0)
                System.out.println("Up Scrolling");
            else
                System.out.println("Down Scrolling");
        }));

        /*
        glfwSetCharCallback(this.window, (window, codepoint) ->{
            String key_name = glfwGetKeyName(GLFW_KEY_M, GLFW_RELEASE);
            System.out.printf("Press %S to change color.\n", key_name);
        });
        */

        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);
        glfwShowWindow(this.window);
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

            assert vidMode != null;
            glfwSetWindowPos(this.window,
                    (vidMode.width() - pWidth.get(0))/2,
                    (vidMode.height() - pHeight.get(0))/2
            );
        }
    }

    private GLFWImage.Buffer loadImages(String path) {
        GLFWImage.Buffer icon;

        try{
            ByteBuffer image;
            int[] width = new int[1];
            int[] height = new int[1];
            int[] channel = new int[1];

            image = stbi_load(path, width, height,channel,4);
            if(image == null)
                throw new RuntimeException("Failed to load image: "+stbi_failure_reason());
            else
                System.out.println("Image loaded: " + width[0] + "x" + height[0]);

            icon = GLFWImage.malloc(1);
            icon.width(width[0]);
            icon.height(height[0]);
            icon.pixels(image);

            stbi_image_free(image);
        }catch (Exception err) {
            throw new RuntimeException(err + ":" + stbi_failure_reason());
        }
        return icon;
    }

}
