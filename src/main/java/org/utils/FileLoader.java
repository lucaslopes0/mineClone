package org.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileLoader {
    public static String loadResource(String fileName) throws IOException {
        String result;
        try (InputStream in = FileLoader.class.getResourceAsStream(fileName)) {
            assert in != null;
            try (Scanner scanner = new Scanner(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                result = scanner.useDelimiter("\\A").next();
            }
        }
        return result;
    }
}
