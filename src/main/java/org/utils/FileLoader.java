package org.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileLoader {

    public String loadResource(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Arquivo de shader não encontrado: " + fileName);
            }
            return new String(inputStream.readAllBytes());
        }
    }
}
