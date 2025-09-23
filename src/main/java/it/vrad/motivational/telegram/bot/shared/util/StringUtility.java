package it.vrad.motivational.telegram.bot.shared.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

/**
 * Utility class for string operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtility {

    /**
     * Creates a new UTF-8 string from a byte array.
     * @param bytes the byte array
     * @return UTF-8 string
     */
    public static String newStringUTF8(byte[] bytes){
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
