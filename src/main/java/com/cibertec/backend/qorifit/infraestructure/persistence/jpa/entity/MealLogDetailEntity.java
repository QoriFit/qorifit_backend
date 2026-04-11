package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "meal_log_details")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MealLogDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", nullable = false)
    private MealLogEntity mealLog;

    @Column(name = "ingredient_id", nullable = false)
    private Long ingredientId;

    @Column(precision = 8, scale = 2)
    private BigDecimal quantityGrams;

    @Column(precision = 8, scale = 2)
    private BigDecimal calories;
}