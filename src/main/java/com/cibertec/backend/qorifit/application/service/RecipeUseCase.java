package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.RecipeIngredientEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.RecipeRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.EditRecipe;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.RegisterRecipe;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.RecipeDetailResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.RecipeIngredientResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.RecipeSummaryResponse;
import com.cibertec.backend.qorifit.infraestructure.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeUseCase {

    private final RecipeRepoImpl recipeRepoImpl;
    private final UserRepoImpl userRepoImpl;
    private final NutritionCalculatorService nutritionCalculator;

    // ── List with filters ──────────────────────────────────────────────────

    public List<RecipeSummaryResponse> listRecipes(
            Long countryId,
            String name,
            Boolean popular,
            Boolean sortByPopularity
            //Long userId
    ) {
        // If no filters are provided AND userId is given, filter by user's goal
        List<RecipeEntity> recipes;

        boolean hasFilters = countryId != null || name != null
                || popular != null || sortByPopularity != null;

        if (hasFilters) {
            recipes = recipeRepoImpl.findAllFiltered(
                    countryId, name, popular,
                    Boolean.TRUE.equals(sortByPopularity)
            );

        } else {
            recipes = recipeRepoImpl.findAllActive();
        }

        return recipes.stream()
                .map(this::toSummary)
                .toList();
    }

    // ── Detail ─────────────────────────────────────────────────────────────

    public RecipeDetailResponse getDetail(Long recipeId) {
        RecipeEntity recipe = recipeRepoImpl.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));

        List<RecipeIngredientResponse> ingredients = recipe.getRecipeIngredients()
                .stream()
                .map(this::toIngredientResponse)
                .toList();

        return RecipeDetailResponse.builder()
                .recipeId(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .instructions(recipe.getInstructions())
                .countryName(recipe.getCountry() != null ? recipe.getCountry().getName() : null)
                .imagePath(recipe.getImagePath())
                .estimatedCalories(recipe.getEstimatedCalories())
                .popularity(recipe.getPopularity())
                .ingredients(ingredients)
                .build();
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private List<RecipeEntity> filterByGoal(String goal) {
        if (goal == null) return recipeRepoImpl.findAllActive();

        return switch (goal.toUpperCase()) {
            // Low calorie recipes for weight loss
            case "LOSE_WEIGHT" -> recipeRepoImpl.findAllActive().stream()
                    .filter(r -> r.getEstimatedCalories() != null
                            && r.getEstimatedCalories() <= 400)
                    .toList();
            // High calorie recipes for muscle gain
            case "GAIN_MUSCLE" -> recipeRepoImpl.findAllActive().stream()
                    .filter(r -> r.getEstimatedCalories() != null
                            && r.getEstimatedCalories() >= 500)
                    .toList();
            // All active recipes for maintenance
            default -> recipeRepoImpl.findAllActive();
        };
    }

    private RecipeSummaryResponse toSummary(RecipeEntity r) {
        return RecipeSummaryResponse.builder()
                .recipeId(r.getId())
                .name(r.getName())
                .countryName(r.getCountry() != null ? r.getCountry().getName() : null)
                .imagePath(r.getImagePath())
                .estimatedCalories(r.getEstimatedCalories())
                .build();
    }

    private RecipeIngredientResponse toIngredientResponse(RecipeIngredientEntity ri) {
        BigDecimal calories = nutritionCalculator
                .calculateFromIngredient(ri.getIngredient(), ri.getQuantityGrams());

        return new RecipeIngredientResponse(
                ri.getIngredient().getId(),
                ri.getIngredient().getName(),
                ri.getIngredient().getCategory(),
                ri.getQuantityGrams(),
                calories
        );
    }


    @Transactional
    public void registerNewRecipe(RegisterRecipe request)
    {
        RecipeEntity newRecipe = new RecipeEntity();
        newRecipe.setName(request.recipeName());
        newRecipe.setDescription(request.description());
        newRecipe.setInstructions(request.instructions());
        newRecipe.setEstimatedCalories(request.estimatedCalories());
        newRecipe.setImagePath(request.imageUrl());

        recipeRepoImpl.save(newRecipe);
    }

    @Transactional
    public void editRecipe(EditRecipe request){

        RecipeEntity recipe = recipeRepoImpl.findById(request.recipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + request.recipeId()));


        if (request.recipeName() != null) {
            recipe.setName(request.recipeName());
        }

        if (request.description() != null) {
            recipe.setDescription(recipe.getDescription());
        }

        if (request.instructions() != null) {
            recipe.setInstructions(request.instructions());
        }

        if (request.estimatedCalories() != null) {
            recipe.setEstimatedCalories(request.estimatedCalories());
        }

        if (request.imagePath() != null) {
            recipe.setImagePath(request.imagePath());
        }

        recipeRepoImpl.save(recipe);

    }
}

