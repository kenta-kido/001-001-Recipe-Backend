package com.example.backend.service;

import com.example.backend.entity.DescriptionEntity;
import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.repository.DescriptionRepository;
import com.example.backend.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class DescriptionService {

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private PhotoService photoService;

    public List<DescriptionEntity> getDescriptionsByRecipeId(Long recipeId) {
        return descriptionRepository.findByRecipeRecipeId(recipeId);
    }

    public List<DescriptionEntity> getDescriptionsByPhotoId(Long photoId) {
        return descriptionRepository.findByPhotoPhotoId(photoId); // photoId で検索
    }

    public Optional<PhotoEntity> getPhotoByDescriptionId(Long descriptionId) {
        return Optional.ofNullable(descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new RuntimeException("Description not found"))
                .getPhoto());
    }

    public DescriptionEntity createDescriptionWithPhoto(Long recipeId, DescriptionEntity description) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
    
        description.setRecipe(recipe);
    
        // 写真が含まれている場合は保存
        if (description.getPhoto() != null) {
            PhotoEntity savedPhoto = photoService.savePhoto(description.getPhoto());
            description.setPhoto(savedPhoto);
        }
    
        return descriptionRepository.save(description);
    }
    
    public DescriptionEntity updateDescription(Long id, DescriptionEntity descriptionDetails) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        description.setDescription(descriptionDetails.getDescription());
        description.setSequence(descriptionDetails.getSequence());
        return descriptionRepository.save(description);
    }

    public DescriptionEntity updateDescriptionPhoto(Long id, PhotoEntity newPhoto) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        PhotoEntity savedPhoto = photoService.savePhoto(newPhoto);
        description.setPhoto(savedPhoto);

        return descriptionRepository.save(description);
    }

    public void deleteDescription(Long id) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        // 関連画像削除
        if (description.getPhoto() != null) {
            photoService.deletePhoto(description.getPhoto().getPhotoId());
        }

        descriptionRepository.delete(description);
    }

    public void deleteDescriptionPhoto(Long id) {
        DescriptionEntity description = descriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Description not found"));

        if (description.getPhoto() != null) {
            photoService.deletePhoto(description.getPhoto().getPhotoId());
            description.setPhoto(null);
            descriptionRepository.save(description);
        }
    }
    
}
