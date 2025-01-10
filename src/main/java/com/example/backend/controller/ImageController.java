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

    @PostMapping("/process")
    public ResponseEntity<String> processImage(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file provided");
        }
        try {
            // 画像を処理してBase64を取得
            String base64Image = imageProcessingService.processImage(file);
            return ResponseEntity.ok(base64Image);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing image: " + e.getMessage());
        }
    }
}