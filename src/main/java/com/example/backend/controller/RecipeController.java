package com.example.backend.controller;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.security.JwtProperties;
import com.example.backend.service.PhotoService;
import com.example.backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// import org.springframework.security.oauth2.jwt.Jwt; // 正しいJwt型をインポート
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    private JwtProperties properties; // @Autowiredを追加

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<RecipeEntity> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/userrecipe")
    public ResponseEntity<List<RecipeEntity>> getUserRecipes(
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(null);
            }
            String token = authHeader.substring(7);
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                    .build()
                    .verify(token);
    
            Long userId = Long.parseLong(jwt.getSubject());
    
            List<RecipeEntity> userRecipes = recipeService.getRecipesByUserId(userId);
            return ResponseEntity.ok(userRecipes);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RecipeEntity> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 新しいエンドポイント: レシピIDからPhotoEntityを取得
    @GetMapping("/{id}/photo")
    public ResponseEntity<String> getPhotoByRecipeId(@PathVariable Long id) {
        return recipeService.getPhotoByRecipeId(id)
                .map(photo -> ResponseEntity.ok("data:image/jpeg;base64," + photo.getBinaryPhoto()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeEntity> createRecipe(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RecipeEntity recipe
    ) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(null);
            }
            String token = authHeader.substring(7);
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                    .build()
                    .verify(token);
    
            Long userId = Long.parseLong(jwt.getSubject());
    
            // レシピに画像が含まれている場合、画像を保存
            if (recipe.getPhoto() != null) {
                PhotoEntity photo = recipe.getPhoto();
                // `photoService` を呼び出して保存
                photo = photoService.savePhoto(photo); 
                recipe.setPhoto(photo); 
            }
    
            RecipeEntity createdRecipe = recipeService.createRecipe(recipe, userId);
            return ResponseEntity.ok(createdRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<RecipeEntity> updateRecipe(@PathVariable Long id, @RequestBody RecipeEntity recipeDetails) {
        try {
            RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipeDetails);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
        
    @PutMapping("/{id}/photo")
    public ResponseEntity<RecipeEntity> updateRecipePhoto(
            @PathVariable Long id,
            @RequestBody PhotoEntity photo
    ) {
        try {
            RecipeEntity recipe = recipeService.getRecipeById(id)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));
            
            // 新しい画像を設定
            recipe.setPhoto(photo);

            RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
        
    @DeleteMapping("/{id}/photo")
    public ResponseEntity<RecipeEntity> deleteRecipePhoto(@PathVariable Long id) {
        try {
            RecipeEntity recipe = recipeService.getRecipeById(id)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            // 画像の関連を解除
            recipe.setPhoto(null);

            RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
