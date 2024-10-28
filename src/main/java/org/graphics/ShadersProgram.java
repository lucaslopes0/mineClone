package org.graphics;

import static org.lwjgl.opengl.GL30.*;
import org.utils.*;

public class ShadersProgram {
    private final int programId;
    public ShadersProgram(String vertexFileName, String fragmentFileName) throws Exception {
        // Criação do programa de shader
        this.programId = glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Não foi possível criar o programa de shaders.");
        }

        // Carregar e compilar os shaders
        int vertexShaderId = createShader(new FileLoader().loadResource(vertexFileName), GL_VERTEX_SHADER);
        int fragmentShaderId = createShader(new FileLoader().loadResource(fragmentFileName), GL_FRAGMENT_SHADER);

        // Linkar os shaders ao programa
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Erro ao linkar o programa: " + glGetProgramInfoLog(programId));
        }

        // Limpar shaders após linkar
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
    }

    private int createShader(String shaderSource, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new RuntimeException("Não foi possível criar o shader.");
        }

        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Erro ao compilar o shader: " + glGetShaderInfoLog(shaderId));
        }

        return shaderId;
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        glDeleteProgram(programId);
    }
}
