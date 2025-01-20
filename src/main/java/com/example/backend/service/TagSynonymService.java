package com.example.backend.service;

import com.example.backend.entity.TagEntity;
import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.TagSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagSynonymService {

    @Autowired
    private TagSynonymRepository tagSynonymRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<TagSynonymEntity> getSynonymsByTagId(Long tagId) {
        return tagSynonymRepository.findByTagTagId(tagId);
    }
    
    public List<TagSynonymEntity> searchBySynonym(String synonym) {
        return tagSynonymRepository.findBySynonymContainingIgnoreCase(synonym);
    }

    public TagSynonymEntity addSynonymToTag(Long tagId, String synonymName) {
        // タグを取得
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // シノニムを作成
        TagSynonymEntity synonym = new TagSynonymEntity();
        synonym.setTag(tag);
        synonym.setSynonym(synonymName);

        // シノニムを保存
        return tagSynonymRepository.save(synonym);
    }

    public TagSynonymEntity createSynonym(TagSynonymEntity synonym) {
        return tagSynonymRepository.save(synonym);
    }

    public void deleteSynonym(Long id) {
        tagSynonymRepository.deleteById(id);
    }
}
