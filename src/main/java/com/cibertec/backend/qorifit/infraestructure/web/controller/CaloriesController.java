package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.CalorieUseCase;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.LogCaloriesRequest;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.CalorieSummaryResponse;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calories")
@CrossOrigin(origins = "*")
public class CaloriesController {

    private final CalorieUseCase calorieUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> logMeal(
            @RequestBody @Valid LogCaloriesRequest request
    ) {
        calorieUseCase.logMeal(request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .message("Meal logged successfully")
                .build());
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<CalorieSummaryResponse>> getSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        CalorieSummaryResponse summary = calorieUseCase.getSummary(date);

        return ResponseEntity.ok(ApiResponse.<CalorieSummaryResponse>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(summary)
                .message("Summary retrieved")
                .build());
    }
}