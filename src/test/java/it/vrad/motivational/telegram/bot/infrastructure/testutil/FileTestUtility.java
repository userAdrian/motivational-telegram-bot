package it.vrad.motivational.telegram.bot.infrastructure.testutil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileTestUtility {
    private static final String ERROR_READING_CONTENT_FROM_FILE = "Error reading content from {} file";

    /**
     * Returns a {@link File} object from the given classpath resource path.
     *
     * @param path the classpath resource path
     * @return the File object
     * @throws UncheckedIOException if the file cannot be accessed
     */
    public static File getFileFromClassPath(String path) {
        try {
            return new ClassPathResource(path).getFile();
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot access file from classpath: " + path, e);
        }
    }

    /**
     * Reads the content of a classpath resource as a String using UTF-8 encoding.
     *
     * @param path the classpath resource path
     * @return the content of the resource as a String
     * @throws UncheckedIOException if the resource cannot be read
     */
    public static String readResourceAsString(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        try {
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(ERROR_READING_CONTENT_FROM_FILE, path);
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    /**
     * Reads the content of a classpath resource as a byte array.
     *
     * @param classpath the classpath resource path
     * @return the content of the resource as a byte array
     * @throws UncheckedIOException if the resource cannot be read
     */
    public static byte[] readBytesFromResource(String classpath) {
        ClassPathResource resource = new ClassPathResource(classpath);

        return readBytesFromResource(resource);
    }

    /**
     * Reads the content of a {@link Resource} as a byte array.
     *
     * @param resource the Spring Resource
     * @return the content of the resource as a byte array
     * @throws UncheckedIOException if the resource cannot be read
     */
    public static byte[] readBytesFromResource(Resource resource) {
        try {
            return resource.getContentAsByteArray();
        } catch (IOException e) {
            log.error(ERROR_READING_CONTENT_FROM_FILE, resource.getFilename());
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }
}
