package no.foundation.tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ReaderUtils {

    public static String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (var stream = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1024];
            while (stream.available() > 0) {
                int read = stream.read(buffer);
                if (read == -1) break;
                sb.append(new String(buffer, 0, read, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
