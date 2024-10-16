import org.lwjgl.glfw.GLFWImage;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;

public class ImageLoader {

    private final Window window;

    public ImageLoader(Window window) {
        this.window = window;
    }

    public void init(){
        ImageLoader.setWindowIcon(this.window.getWindowHandle(),".//icons/mine.png");
        ImageLoader.setCursorImg(this.window.getWindowHandle(),".//icons/cursor.png");
    }

    private static GLFWImage.Buffer load(String path) {
        GLFWImage.Buffer icon;

        try{
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
        }catch (Exception err) {
            throw new RuntimeException(err + ":" + stbi_failure_reason());
        }
        return icon;
    }

    public static void setWindowIcon(long window, String path) {
        glfwSetWindowIcon(window, load(path));
    }

    public static void setCursorImg(long window, String path){
        GLFWImage.Buffer iconBuffer = load(path);
        iconBuffer.get(0);
        GLFWImage cursorImage = iconBuffer.get(0);
        long cursor = glfwCreateCursor(cursorImage, 0,0);
        glfwSetCursor(window, cursor);
        iconBuffer.free();
    }
}
