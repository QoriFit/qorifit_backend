package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"recipeIngredients", "mealLogs"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ingredients")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "calories_per_100g", precision = 6, scale = 2, nullable = false)
    private BigDecimal caloriesPer100g;

    @Column(name = "category", length = 100)
    private String category;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredientEntity> recipeIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "ingredient")
    private List<MealLogEntity> mealLogs = new ArrayList<>();
}
