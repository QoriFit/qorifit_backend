package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSummaryResponse{
    private Long recipeId;
    private String name;
    private String countryName;
    private String imagePath;
    private Integer estimatedCalories;
}