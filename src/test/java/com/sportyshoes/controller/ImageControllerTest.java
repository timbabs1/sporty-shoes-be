package com.sportyshoes.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // The upload directory (should match your configuration)
    @Value("${product.image.upload-dir:sporty-shoes-images}")
    private String uploadDir;

    @Test
    public void testServeImageSuccess() throws Exception {
        // Prepare a test file in the expected location.
        // For example, ensure that src/test/resources/test-data/test_image.jpg exists
        // and then copy it to the upload directory before running the test.
        ClassPathResource resource = new ClassPathResource("test-data/test_image.jpg");
        Path targetPath = Path.of(uploadDir, "test_image.jpg");
        Files.createDirectories(targetPath.getParent());
        Files.copy(resource.getInputStream(), targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        mockMvc.perform(get("/images/test_image.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    public void testServeImageNotFound() throws Exception {
        mockMvc.perform(get("/images/nonexistent.jpg"))
                .andExpect(status().isNotFound());
    }
}
