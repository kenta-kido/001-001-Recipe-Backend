package com.example.backend.service;

import com.example.backend.entity.TagEntity;
import com.example.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    // 全てのタグを取得
    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    // IDでタグを取得
    public Optional<TagEntity> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    // 名前でタグを取得
    public Optional<TagEntity> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    // タグを作成
    public TagEntity createTag(TagEntity tag) {
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new RuntimeException("Tag with this name already exists");
        }
        return tagRepository.save(tag);
    }

    // タグを更新
    public TagEntity updateTag(Long id, TagEntity tagDetails) {
        TagEntity existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        existingTag.setName(tagDetails.getName());
        existingTag.setCategory(tagDetails.getCategory());
        return tagRepository.save(existingTag);
    }

    // タグを削除
    public void deleteTag(Long id) {
        TagEntity existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tagRepository.delete(existingTag);
    }
}
