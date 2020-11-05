package com.epam.esm.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputSanitizerTest {

    @Test
    public void testSanitize() {
        String badString = "<script>Test</script>";
        String niceString = "&lt;script&gt;Test&lt;/script&gt;";
        assertEquals(niceString, InputSanitizer.sanitize(badString));
    }
}