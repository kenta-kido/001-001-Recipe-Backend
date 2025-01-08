package com.example.backend.controller;

import com.example.backend.entity.PhotoEntity;
import com.example.backend.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/descriptions/{descriptionId}/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // 特定の説明に関連するすべての写真を取得
    // @GetMapping
    // public List<PhotoEntity> getPhotosByDescriptionId(@PathVariable Long descriptionId) {
    //     return photoService.getPhotosByDescriptionId(descriptionId);
    // }

    // 特定の写真を取得
    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoEntity> getPhotoById(@PathVariable Long descriptionId, @PathVariable Long photoId) {
        PhotoEntity photo = photoService.getPhotoById(photoId);
        return ResponseEntity.ok(photo);
    }

    // 新しい写真を登録
    @PostMapping
    public ResponseEntity<PhotoEntity> createPhoto(
            @PathVariable Long descriptionId,
            @RequestBody PhotoEntity photo
    ) {
        PhotoEntity createdPhoto = photoService.createPhoto(photo);
        return ResponseEntity.ok(createdPhoto);
    }

    // 特定の写真を更新
    @PutMapping("/{photoId}")
    public ResponseEntity<PhotoEntity> updatePhoto(
            @PathVariable Long descriptionId,
            @PathVariable Long photoId,
            @RequestBody PhotoEntity photoDetails
    ) {
        PhotoEntity updatedPhoto = photoService.updatePhoto(descriptionId, photoId, photoDetails);
        return ResponseEntity.ok(updatedPhoto);
    }

    // 特定の写真を削除
    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }
}
