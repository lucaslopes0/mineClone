import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class treino {
    private long window;

    public void run(){

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();

        glfwSetErrorCallback(null).free();

    }

    public void init(){

        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            System.out.println("Error Initialization failed!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(1280,720,"Treino", NULL, NULL);

        if(window == NULL){
            throw new RuntimeException("Error: Failed to create GLFW window");
        }

        glfwKeyboardCallbacks();
    }
}
