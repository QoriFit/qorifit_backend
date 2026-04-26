package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetailResponse{

    private Long recipeId;
    private String name;
    private String description;
    private String instructions;
    private String countryName;
    private String imagePath;
    private Integer estimatedCalories;
    private Integer popularity;
    private List<RecipeIngredientResponse> ingredients;
}
