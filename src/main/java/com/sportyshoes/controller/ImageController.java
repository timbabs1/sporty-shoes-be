package com.sportyshoes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.*;

@RestController
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);
    // Inject the directory path (ensure it matches what you're using elsewhere)
    @Value("${product.image.upload-dir:sporty-shoes-images}")
    private String uploadDir;

    /**
     * Endpoint to serve an image file by filename.
     * Example: GET /images/filename.jpg
     */
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            // Resolve the file path
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Try to determine file's content type
            String contentType = Files.probeContentType(filePath);
            log.info("Serving image: {} with content type: {}", filename, contentType);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

