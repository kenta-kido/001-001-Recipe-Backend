package com.example.backend.controller;


import com.example.backend.service.ImageProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageProcessingService imageProcessingService;

    /**
     * Endpoint to process an uploaded image.
     * Accepts an image file as input, processes it, and returns the processed image
     * as a Base64 encoded string.
     *
     * @param file The uploaded image file sent as a multipart request.
     * @return A ResponseEntity containing the Base64 encoded image if successful,
     *         or an error message if the processing fails.
     */
    @PostMapping("/process")
    public ResponseEntity<String> processImage(@RequestPart("file") MultipartFile file) {
        // Check if the file is empty and return a 400 Bad Request if true
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file provided");
        }
        try {
            // Process the image and retrieve the Base64 encoded result
            String base64Image = imageProcessingService.processImage(file);
            return ResponseEntity.ok(base64Image);
        } catch (Exception e) {
            // Handle errors during image processing and return a 500 Internal Server Error
            return ResponseEntity.status(500).body("Error processing image: " + e.getMessage());
        }
    }
}