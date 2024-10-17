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
        this.setWindowIcon(".//icons/mine.png");
        this.setCursorImg(".//icons/cursor.png");
    }

    private static GLFWImage.Buffer load(String path) {
        GLFWImage.Buffer icon;
        ByteBuffer image = null;
        try{
            int[] width = new int[1];
            int[] height = new int[1];
            int[] channel = new int[1];

            image = stbi_load(path, width, height,channel,4);
            if(image == null)
                throw new RuntimeException("Failed to load image at path: " + path + ". Reason: " + stbi_failure_reason());

            icon = GLFWImage.malloc(1);
            icon.width(width[0]);
            icon.height(height[0]);
            icon.pixels(image);

        }catch (Exception err) {
            throw new RuntimeException(err + ":" + stbi_failure_reason());
        }finally {
            if(image!=null)
                stbi_image_free(image);
        }
        return icon;
    }

    private void setWindowIcon(String path) {
        glfwSetWindowIcon(this.window.getWindowHandle(), load(path));
    }

    private void setCursorImg(String path){
        GLFWImage.Buffer iconBuffer = load(path);
        iconBuffer.get(0);
        GLFWImage cursorImage = iconBuffer.get(0);
        long cursor = glfwCreateCursor(cursorImage, 0,0);
        glfwSetCursor(this.window.getWindowHandle(), cursor);
        iconBuffer.free();
    }
}
