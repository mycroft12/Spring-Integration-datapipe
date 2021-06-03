package com.leyton.locatorfrdatapipe.transformer;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class TransformerDtj {

    public String transforme(String filePath) throws IOException {
        String content = Files.readAllBytes(Paths.get(filePath)).toString();
        return "Transformed : "+content;
    }
}
