package com.example.backend.controller;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.security.JwtProperties;
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
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    private JwtProperties properties; // @Autowiredを追加


    // @GetMapping("/decode-jwt")
    // public ResponseEntity<String> decodeJwt(@RequestHeader("Authorization") String authHeader) {
    //     try {
    //         // Authorizationヘッダーからトークンを取得
    //         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    //             return ResponseEntity.status(401).body("Authorization header is missing or invalid.");
    //         }
    //         String token = authHeader.substring(7); // "Bearer "を削除

    //         // トークンをデコード
    //         DecodedJWT jwt = JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
    //                 .build()
    //                 .verify(token);

    //         // JWTのクレームを取得
    //         String subject = jwt.getSubject(); // "sub"
    //         String email = jwt.getClaim("e").asString(); // "e"
    //         String roles = String.join(", ", jwt.getClaim("a").asList(String.class)); // "a"

    //         // レスポンスにJWT情報を返す
    //         return ResponseEntity.ok(
    //                 "JWT Decoded:\n" +
    //                         "Subject: " + subject + "\n" +
    //                         "Email: " + email + "\n" +
    //                         "Roles: " + roles
    //         );

    //     } catch (Exception e) {
    //         return ResponseEntity.status(400).body("Invalid JWT: " + e.getMessage());
    //     }
    // }
    

    @GetMapping
    public List<RecipeEntity> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeEntity> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeEntity> createRecipe(
            @RequestHeader("Authorization") String authHeader, // Authorization ヘッダーを取得
            @RequestBody RecipeEntity recipe
    ) {
        try {
            // Authorizationヘッダーからトークンを取得
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(null); // ヘッダーがない場合はエラー
            }
            String token = authHeader.substring(7); // "Bearer "を削除
    
            // トークンをデコード
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                    .build()
                    .verify(token);
    
            // JWTからユーザーIDを取得
            Long userId = Long.parseLong(jwt.getSubject()); // "sub" からIDを取得
    
            // レシピ作成処理
            recipeService.createRecipe(recipe, userId);
    
            return ResponseEntity.ok(recipe);
    
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null); // トークンが無効な場合はエラー
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
