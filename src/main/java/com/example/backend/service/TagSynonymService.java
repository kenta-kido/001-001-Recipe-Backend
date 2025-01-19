package com.example.backend.service;

import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.repository.TagSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagSynonymService {

    @Autowired
    private TagSynonymRepository tagSynonymRepository;

    public List<TagSynonymEntity> searchBySynonym(String synonym) {
        return tagSynonymRepository.findBySynonymContainingIgnoreCase(synonym);
    }

    public TagSynonymEntity createSynonym(TagSynonymEntity synonym) {
        return tagSynonymRepository.save(synonym);
    }

    public void deleteSynonym(Long id) {
        tagSynonymRepository.deleteById(id);
    }
}
