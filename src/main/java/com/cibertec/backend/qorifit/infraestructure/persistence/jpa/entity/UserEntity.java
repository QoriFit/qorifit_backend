package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"userCountries", "mealLogs", "reminders", "steps"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "goal", length = 50)
    private String goal;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight; // KG

    @Column(name = "height", precision = 5, scale = 2)
    private Long height; // CM

    @Column(name = "is_active")
    private Boolean isActive = true;

    //Meta diaria por dia
    @Column(name = "steps_per_day", nullable = false)
    private Long stepsPerDay;

    //Meta, no consumir mas de estas calorías por dia
    @Column(name = "max_calories", nullable = false)
    private BigDecimal maxCaloriesPerDay;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCountryEntity> userCountries = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealLogEntity> mealLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReminderEntity> reminders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StepEntity> steps = new ArrayList<>();
}