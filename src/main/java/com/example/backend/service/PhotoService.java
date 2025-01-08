package com.example.backend.service;

import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.DescriptionEntity;
import com.example.backend.repository.PhotoRepository;
import com.example.backend.repository.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public List<PhotoEntity> getAllPhotos() {
        return photoRepository.findAll();
    }

    public PhotoEntity getPhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
    }

    public PhotoEntity createPhoto(PhotoEntity photo) {
        return photoRepository.save(photo);
    }

    public PhotoEntity updatePhoto(Long descriptionId, Long photoId, PhotoEntity photoDetails) {
        PhotoEntity photo = photoRepository.findById(photoId)
                // .filter(p -> p.getDescription().getDescriptionId().equals(descriptionId))
                .orElseThrow(() -> new RuntimeException("Photo not found or does not belong to the specified description"));

        photo.setBinaryPhoto(photoDetails.getBinaryPhoto()); // Base64エンコード済みの文字列
        // photo.setSequence(photoDetails.getSequence());
        return photoRepository.save(photo);
    }

    public void deletePhoto(Long photoId) {
        PhotoEntity photo = photoRepository.findById(photoId)
                // .filter(p -> p.getDescription().getDescriptionId().equals(descriptionId))
                .orElseThrow(() -> new RuntimeException("Photo not found or does not belong to the specified description"));

        photoRepository.delete(photo);
    }

    public PhotoEntity savePhoto(PhotoEntity photo) {
        return photoRepository.save(photo);
    }
}
