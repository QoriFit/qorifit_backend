package com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"userCountries", "recipes"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "countries")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "country_id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "iso_code", length = 2, nullable = false)
    private String isoCode;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCountryEntity> userCountries = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    private List<RecipeEntity> recipes = new ArrayList<>();
}
