package com.example.backend.service;

import com.example.backend.entity.TagEntity;
import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.TagSynonymRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagSynonymRepository tagSynonymRepository;

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
        // タグの重複をチェック
        if (tagRepository.existsByName(tag.getName())) {
            throw new RuntimeException("Tag already exists");
        }

        // タグを保存
        TagEntity savedTag = tagRepository.save(tag);

        // デフォルトのシノニムを登録
        TagSynonymEntity synonym = new TagSynonymEntity();
        synonym.setTag(savedTag);
        synonym.setSynonym(tag.getName()); // デフォルトで名前をシノニムとして使用

        tagSynonymRepository.save(synonym);
        return savedTag;
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
