package com.example.backend.repository;

import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.DescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
    boolean existsByDescriptionAndSequence(DescriptionEntity description, int sequence); // 重複チェック
    List<PhotoEntity> findAllByDescription(DescriptionEntity description); // 特定の説明に関連付く写真を取得
    List<PhotoEntity> findByDescriptionDescriptionId(Long descriptionId);
}
