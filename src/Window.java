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
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
public class Window {

    Random rand = new Random();
    private long window;
    private boolean isFullscreen = false;


    public void run() {
        System.out.println("LWJGL VERSION: " + Version.getVersion());
        init();
        loop();

        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);

        glfwTerminate();

        glfwSetErrorCallback(null).free();
    }

    public void toggleFullScreen() {

        if (!this.isFullscreen){
            glfwWindowHint(GLFW_SOFT_FULLSCREEN, GLFW_TRUE);
            glfwSetWindowMonitor(this.window, glfwGetPrimaryMonitor(), 0, 0, 1920, 1080, 165);
            this.isFullscreen = true;

        }
        else {
            glfwSetWindowMonitor(this.window, 0, 0, 0, 860, 460, 165);
            centralize();
            this.isFullscreen = false;
        }
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new RuntimeException("Err");

        this.window = glfwCreateWindow(860, 460, "Minecraft", NULL, NULL);


        GLFWImage.Buffer img = loadIcon("C:/Users/lucas/Desktop/projectMineClone/icons/mine.png"); //load_icon é um metodo que eu criei
        glfwSetWindowIcon(window,img);

        if (this.window == NULL)
            throw new RuntimeException("err");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);


        glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
            if (key == GLFW_KEY_M && action == GLFW_RELEASE)
                glClearColor(
                        rand.nextFloat() % 255f,
                        rand.nextFloat() % 255f,
                        rand.nextFloat() % 255f,
                        rand.nextFloat() % 255f
                );
            if (key == GLFW_KEY_F11 && action == GLFW_RELEASE) {
                toggleFullScreen();
            }
            glfwSetWindowUserPointer(window, 0);
            glfwGetWindowUserPointer(window);
        });
        centralize();
        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);
        glfwShowWindow(this.window);
    }

    public void centralize() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(this.window, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(this.window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
        }
    }
    public GLFWImage.Buffer loadIcon(String path) {
        GLFWImage.Buffer icon;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Cria variáveis para largura, altura e canais da imagem
            ByteBuffer image;
            int[] width = new int[3];
            int[] height = new int[3];
            int[] channels = new int[3];

            // Carrega a imagem no formato RGBA
            image = stbi_load(path, width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            // Cria um buffer GLFWImage e define os dados da imagem
            icon = GLFWImage.malloc(1);
            icon.width(width[0]); // Define a largura da imagem
            icon.height(height[0]); // Define a altura da imagem
            icon.pixels(image); // Define os pixels da imagem

            stbi_image_free(image); // Libera a imagem da memória
        }
        return icon;
    }

    public void loop() {
        GL.createCapabilities();

        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(this.window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(this.window);
            glfwPollEvents();
        }
    }
}

