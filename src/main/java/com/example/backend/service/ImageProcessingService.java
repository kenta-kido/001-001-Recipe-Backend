package com.example.backend.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

@Service
public class ImageProcessingService {

    public String processImage(MultipartFile file) throws Exception {
        // 入力ファイルをInputStreamとして読み込む
        InputStream inputStream = file.getInputStream();
        BufferedImage image;

        // HEICの場合の処理
        if (file.getOriginalFilename() != null && file.getOriginalFilename().toLowerCase().endsWith(".heic")) {
            image = Imaging.getBufferedImage(inputStream); // HEICをBufferedImageに変換
        } else {
            image = ImageIO.read(inputStream); // HEIC以外の形式（PNG, JPEGなど）を読み込む
        }

        if (image == null) {
            throw new Exception("Invalid image format");
        }

        // 画像を縮小してJPEG形式に変換
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(image)
                .size(1200, 800) // 最大サイズを設定
                .outputFormat("jpeg") // JPEGに変換
                .toOutputStream(outputStream);

        // Base64エンコード
        byte[] jpegBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(jpegBytes);
    }
}
