package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "meal_logs")
public class MealLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient;

    @Column(name = "meal_type", length = 50)
    private String mealType;

    @Column(name = "quantity_grams", precision = 6, scale = 2, nullable = false)
    private BigDecimal quantityGrams;

    @Column(name = "calories", precision = 6, scale = 2, nullable = false)
    private BigDecimal calories;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "logged_at", updatable = false, insertable = false)
    private LocalDateTime loggedAt;
}
