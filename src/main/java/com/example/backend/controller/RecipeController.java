package com.example.backend.controller;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.entity.PhotoEntity;
import com.example.backend.entity.RecipeEntity;
import com.example.backend.security.JwtProperties;
import com.example.backend.service.PhotoService;
import com.example.backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://portfolio-kenta-926ed757a371.herokuapp.com"})
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtProperties properties;

    @Autowired
    private PhotoService photoService;

    /**
     * Fetch all recipes.
     *
     * @return A list of all RecipeEntity objects.
     */
    @GetMapping
    public List<RecipeEntity> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    /**
     * Fetch all recipes created by the authenticated user.
     *
     * @param authHeader The Authorization header containing the JWT token.
     * @return A ResponseEntity containing a list of RecipeEntity objects created by the user.
     */
    @GetMapping("/userrecipe")
    public ResponseEntity<List<RecipeEntity>> getUserRecipes(@RequestHeader("Authorization") String authHeader) {
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

    /**
     * Fetch a recipe by its ID.
     *
     * @param id The ID of the recipe.
     * @return A ResponseEntity containing the RecipeEntity if found, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeEntity> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Fetch the latest recipes.
     *
     * @return A ResponseEntity containing a list of the latest RecipeEntity objects.
     */
    @GetMapping("/latest")
    public ResponseEntity<List<RecipeEntity>> getLatestRecipes() {
        List<RecipeEntity> latestRecipes = recipeService.getLatestRecipes();
        return ResponseEntity.ok(latestRecipes);
    }

    /**
     * Fetch the photo associated with a recipe.
     *
     * @param id The ID of the recipe.
     * @return A ResponseEntity containing the Base64-encoded photo string, or 404 if not found.
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<String> getPhotoByRecipeId(@PathVariable Long id) {
        return recipeService.getPhotoByRecipeId(id)
                .map(photo -> ResponseEntity.ok("data:image/jpeg;base64," + photo.getBinaryPhoto()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Search for recipes using a keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of RecipeEntity objects matching the search criteria.
     */
    @GetMapping("/search")
    public List<RecipeEntity> searchRecipes(@RequestParam String keyword) {
        return recipeService.findRecipesByKeyword(keyword);
    }

    /**
     * Search for recipes by similarity in ingredients or tags.
     *
     * @param ingredientKeyword The ingredient keyword for similarity search (optional).
     * @param tagKeyword        The tag keyword for similarity search (optional).
     * @return A list of RecipeEntity objects matching the similarity criteria.
     */
    @GetMapping("/search/similarity")
    public List<RecipeEntity> searchRecipesBySimilarity(
            @RequestParam(required = false) String ingredientKeyword,
            @RequestParam(required = false) String tagKeyword
    ) {
        if (ingredientKeyword != null) {
            return recipeService.findRecipesByIngredientNameWithSimilarity(ingredientKeyword);
        } else if (tagKeyword != null) {
            return recipeService.findRecipesByTagNameWithSimilarity(tagKeyword);
        } else {
            throw new IllegalArgumentException("At least one search parameter is required");
        }
    }

    /**
     * Create a new recipe.
     *
     * @param authHeader The Authorization header containing the JWT token.
     * @param recipe     The RecipeEntity object to be created.
     * @return A ResponseEntity containing the created RecipeEntity.
     */
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

            if (recipe.getPhoto() != null) {
                PhotoEntity photo = recipe.getPhoto();
                photo = photoService.savePhoto(photo);
                recipe.setPhoto(photo);
            }

            RecipeEntity createdRecipe = recipeService.createRecipe(recipe, userId);
            return ResponseEntity.ok(createdRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    /**
     * Update an existing recipe by its ID.
     *
     * @param id            The ID of the recipe to update.
     * @param recipeDetails The updated RecipeEntity object.
     * @return A ResponseEntity containing the updated RecipeEntity, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeEntity> updateRecipe(@PathVariable Long id, @RequestBody RecipeEntity recipeDetails) {
        try {
            RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipeDetails);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update the photo of a recipe by its ID.
     *
     * @param id    The ID of the recipe to update.
     * @param photo The new PhotoEntity object.
     * @return A ResponseEntity containing the updated RecipeEntity, or 404 if not found.
     */
    @PutMapping("/{id}/photo")
    public ResponseEntity<RecipeEntity> updateRecipePhoto(@PathVariable Long id, @RequestBody PhotoEntity photo) {
        try {
            RecipeEntity recipe = recipeService.getRecipeById(id)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            recipe.setPhoto(photo);

            RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a recipe by its ID.
     *
     * @param id The ID of the recipe to delete.
     * @return A ResponseEntity with no content upon successful deletion, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove the photo from a recipe by its ID.
     *
     * @param id The ID of the recipe whose photo is to be removed.
     * @return A ResponseEntity containing the updated RecipeEntity, or 404 if not found.
     */
    @DeleteMapping("/{id}/photo")
    public ResponseEntity<RecipeEntity> deleteRecipePhoto(@PathVariable Long id) {
        try {
            RecipeEntity recipe = recipeService.getRecipeById(id)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            recipe.setPhoto(null);

            RecipeEntity updatedRecipe = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}