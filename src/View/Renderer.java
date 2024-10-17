package View;

import static org.lwjgl.opengl.GL11C.*;

public class Renderer {
    public void init() throws Exception {
        // Inicializa qualquer recurso de renderização aqui
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}

