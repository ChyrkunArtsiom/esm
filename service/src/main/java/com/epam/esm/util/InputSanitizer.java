package com.epam.esm.util;

/**
 * Class for sanitizing input strings.
 */
public class InputSanitizer {
    /**
     * Sanitizes the string.
     *
     * @param input the input string
     * @return the sanitized string
     */
    public static String sanitize(String input) {
        input = input.replace("<", "&lt;");
        input = input.replace(">", "&gt;");
        return input;
    }
}