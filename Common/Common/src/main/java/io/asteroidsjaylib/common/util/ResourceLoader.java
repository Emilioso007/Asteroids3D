package io.asteroidsjaylib.common.util;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ResourceLoader {

    private static final Path GLOBAL_TEMP_DIR;

    static {
        try {
            GLOBAL_TEMP_DIR = Files.createTempDirectory("asteroids_");
            GLOBAL_TEMP_DIR.toFile().deleteOnExit();
        } catch (Exception e){
            throw new RuntimeException("Failed to create global temporary resource directory", e);
        }
    }

    public static String getAsAbsolutePath(String relativePath){
        return getAsAbsolutePath(relativePath, StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass());
    }

    public static String getAsAbsolutePath(String relativePath, Class<?> caller){
        try {
            String filename = new File(relativePath).getName();

            File tempFile = new File(GLOBAL_TEMP_DIR.toFile(), filename);

            if (tempFile.exists()) return tempFile.getAbsolutePath();

            InputStream stream = caller.getResourceAsStream(relativePath);
            if (stream == null) throw new RuntimeException("Could not find resource: " + relativePath);
            Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            stream.close();

            tempFile.deleteOnExit();

            return tempFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load resource: " + relativePath);
        }
    }
}
