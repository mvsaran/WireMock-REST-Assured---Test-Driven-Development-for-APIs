package com.demo;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestDataLoader {
    
    public static String loadJson(String fileName) {
        try (InputStream is = TestDataLoader.class
                .getResourceAsStream("/testdata/" + fileName)) {
            
            if (is == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load: " + fileName, e);
        }
    }
}

