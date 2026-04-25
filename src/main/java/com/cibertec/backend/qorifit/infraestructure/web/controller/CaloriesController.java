package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.CalorieUseCase;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.CaloriesRegister;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.MealSummaryByDate;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calories")
@CrossOrigin(origins = "*")
public class CaloriesController {

    private final CalorieUseCase calorieUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> logMeal(
            @RequestBody @Valid CaloriesRegister request
    ) {
        calorieUseCase.logMeal(request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .message("Comida registrada exitosamente")
                .build());
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<MealSummaryByDate>>> getSummary(
            @RequestParam LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        List<MealSummaryByDate> summary = calorieUseCase.getMealSummaryByDates(startDate, endDate);

        return ResponseEntity.ok(ApiResponse.<List<MealSummaryByDate>>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(summary)
                .message("Resumen recibido")
                .build());
    }
}