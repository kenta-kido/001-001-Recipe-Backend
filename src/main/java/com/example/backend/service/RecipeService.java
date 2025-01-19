package com.example.backend.service;
import com.example.backend.entity.DescriptionEntity;
import com.example.backend.entity.IngredientsEntity;
import com.example.backend.entity.IngredientsSynonymEntity;
import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.entity.TagEntity;
import com.example.backend.entity.TagSynonymEntity;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.DescriptionRepository;
import com.example.backend.repository.IngredientsRepository;
import com.example.backend.repository.IngredientsSynonymRepository;
import com.example.backend.repository.PhotoRepository;
import com.example.backend.repository.RecipeRepository;
import com.example.backend.repository.TagRepository;
import com.example.backend.repository.TagSynonymRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.util.LevenshteinDistance;
import com.example.backend.util.TrigramSimilarity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<RecipeEntity> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<RecipeEntity> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public List<RecipeEntity> getLatestRecipes() {
        return recipeRepository.findTop3ByOrderByTimestampDesc();
    }

    // レシピIDから対応するPhotoEntityを取得
    public Optional<PhotoEntity> getPhotoByRecipeId(Long recipeId) {
        return Optional.ofNullable(recipeRepository.findByRecipeId(recipeId).getPhoto());
    }

    public List<RecipeEntity> getRecipesByUserId(Long userId) {
        return recipeRepository.findByUserId(userId);
    }
    
    public RecipeEntity createRecipe(RecipeEntity recipe, Long userId) {
        // データベースからユーザーを取得
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ユーザーのロールを確認
        if (!"ROLE_USER".equals(user.getRole())) {
            throw new RuntimeException("Unauthorized: Only ROLE_USER can create recipes");
        }

        // ユーザーをレシピに設定
        recipe.setUser(user);

        // レシピを保存
        return recipeRepository.save(recipe);
    }

    public RecipeEntity updateRecipe(Long id, RecipeEntity recipeDetails) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        
        recipe.setUser(recipeDetails.getUser());
        recipe.setTitle(recipeDetails.getTitle());
        recipe.setTimestamp(recipeDetails.getTimestamp());

        return recipeRepository.save(recipe);
    }
    
    public RecipeEntity updateRecipePhoto(Long recipeId, PhotoEntity newPhoto) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setPhoto(newPhoto);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipeRepository.delete(recipe);
    }

    public RecipeEntity deleteRecipePhoto(Long recipeId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setPhoto(null);
        return recipeRepository.save(recipe);
    }


    @Autowired
    private IngredientsSynonymRepository ingredientsSynonymRepository;

    @Autowired
    private TagSynonymRepository tagSynonymRepository;

    private static final int LEVENSHTEIN_THRESHOLD = 2;
    private static final double TRIGRAM_SIMILARITY_THRESHOLD = 0.15;

    public List<RecipeEntity> findRecipesByKeyword(String keyword) {
        // 材料名に基づく検索
        List<RecipeEntity> recipesByIngredient = findRecipesByIngredientNameWithSimilarity(keyword);

        // タグ名に基づく検索
        List<RecipeEntity> recipesByTag = findRecipesByTagNameWithSimilarity(keyword);

        // タイトルに基づく検索
        List<RecipeEntity> recipesByTitle = recipeRepository.findByTitleLike(keyword);

        // 材料名によるトライグラム検索
        List<RecipeEntity> recipesByIngredientTrigram = findRecipesByIngredientTrigram(keyword);

        // タグ名によるトライグラム検索
        List<RecipeEntity> recipesByTagTrigram = findRecipesByTagTrigram(keyword);
        
        // 材料とタグの検索結果をマージし、重複を除外
        Set<RecipeEntity> uniqueRecipes = new HashSet<>();
        uniqueRecipes.addAll(recipesByIngredient);
        uniqueRecipes.addAll(recipesByTag);
        uniqueRecipes.addAll(recipesByTitle);
        uniqueRecipes.addAll(recipesByIngredientTrigram);
        uniqueRecipes.addAll(recipesByTagTrigram);


        // 重複を排除したリストを返却
        return new ArrayList<>(uniqueRecipes);
    }

    // 材料名とシノニムを活用して類似度の高い材料を検索
    public List<RecipeEntity> findRecipesByIngredientNameWithSimilarity(String keyword) {
        List<IngredientsSynonymEntity> synonyms = ingredientsSynonymRepository.findAll();

        // レーベンシュタイン距離が閾値以内のシノニムを検索
        List<IngredientsEntity> matchingIngredients = synonyms.stream()
            .filter(synonym -> LevenshteinDistance.calculate(synonym.getSynonym(), keyword) <= LEVENSHTEIN_THRESHOLD)
            .map(IngredientsSynonymEntity::getIngredient)
            .distinct()
            .toList();

        // 一致する材料に関連するレシピを取得
        return recipeRepository.findByIngredientsIn(matchingIngredients);
    }

    // タグ名とシノニムを活用して類似度の高いタグを検索
    public List<RecipeEntity> findRecipesByTagNameWithSimilarity(String keyword) {
        List<TagSynonymEntity> synonyms = tagSynonymRepository.findAll();

        // レーベンシュタイン距離が閾値以内のシノニムを検索
        List<TagEntity> matchingTags = synonyms.stream()
            .filter(synonym -> LevenshteinDistance.calculate(synonym.getSynonym(), keyword) <= LEVENSHTEIN_THRESHOLD)
            .map(TagSynonymEntity::getTag)
            .distinct()
            .toList();

        // 一致するタグに関連するレシピを取得
        return recipeRepository.findByTagsIn(matchingTags);
    }
    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private TagRepository tagRepository;

        // 材料名によるトライグラム検索
    public List<RecipeEntity> findRecipesByIngredientTrigram(String keyword) {
        List<IngredientsEntity> allIngredients = ingredientsRepository.findAll();

        // トライグラムに基づいて類似度が高い材料を取得
        List<IngredientsEntity> matchingIngredients = allIngredients.stream()
                .filter(ingredient -> TrigramSimilarity.calculate(ingredient.getName(), keyword) >= TRIGRAM_SIMILARITY_THRESHOLD)
                .toList();

        // 一致する材料を使用しているレシピを取得
        return recipeRepository.findByIngredientsIn(matchingIngredients);
    }

    // タグ名によるトライグラム検索
    public List<RecipeEntity> findRecipesByTagTrigram(String keyword) {
        List<TagEntity> allTags = tagRepository.findAll();

        // トライグラムに基づいて類似度が高いタグを取得
        List<TagEntity> matchingTags = allTags.stream()
                .filter(tag -> TrigramSimilarity.calculate(tag.getName(), keyword) >= TRIGRAM_SIMILARITY_THRESHOLD)
                .toList();

        // 一致するタグを使用しているレシピを取得
        return recipeRepository.findByTagsIn(matchingTags);
    }
}
