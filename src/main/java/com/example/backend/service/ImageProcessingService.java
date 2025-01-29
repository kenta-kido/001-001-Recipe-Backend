package com.example.backend.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.imaging.Imaging;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * Service for processing images.
 * This service handles image resizing, format conversion (HEIC to JPEG), 
 * and Base64 encoding for storage or transmission.
 */
@Service
public class ImageProcessingService {

    /**
     * Processes an image by converting it to JPEG, resizing it, and encoding it in Base64.
     * Supports HEIC format conversion using Apache Commons Imaging.
     *
     * @param file The uploaded image file.
     * @return A Base64-encoded string representing the processed image.
     * @throws Exception If the image format is invalid or processing fails.
     */
    public String processImage(MultipartFile file) throws Exception {
        // Read input file as InputStream
        InputStream inputStream = file.getInputStream();
        BufferedImage image;

        // Handle HEIC format conversion
        if (file.getOriginalFilename() != null && file.getOriginalFilename().toLowerCase().endsWith(".heic")) {
            image = Imaging.getBufferedImage(inputStream); // Convert HEIC to BufferedImage
        } else {
            image = ImageIO.read(inputStream); // Read other formats (PNG, JPEG, etc.)
        }

        if (image == null) {
            throw new Exception("Invalid image format");
        }

        // Resize image and convert to JPEG format
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(image)
                .size(1200, 800) // Set maximum size
                .outputFormat("jpeg") // Convert to JPEG
                .toOutputStream(outputStream);

        // Encode the image in Base64
        byte[] jpegBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(jpegBytes);
    }
}
