package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private String profilePicture;
    private Long stepsGoalPerDay;
    private BigDecimal maxCaloriesPerDay;
    private LocalDate birtDate;
    private String goal;


}
