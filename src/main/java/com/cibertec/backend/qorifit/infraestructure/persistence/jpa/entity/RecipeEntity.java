package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"recipeIngredients", "mealLogs"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "recipes")
public class    RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "recipe_id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "instructions", length = 500, nullable = true)
    private String instructions;

    @Column(name = "popularity")
    private Long popularity;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "estimated_calories")
    private Long estimatedCalories;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredientEntity> recipeIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe")
    private List<MealLogEntity> mealLogs = new ArrayList<>();
}
