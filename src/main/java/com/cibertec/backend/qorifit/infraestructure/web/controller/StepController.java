package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.StepsService;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.StepsRegister;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepsByDate;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepsDetailed;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/step")
@CrossOrigin(originPatterns = "*")
public class StepController {

    private final StepsService stepsService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerSteps(
            @Valid @RequestBody StepsRegister request
            ){

        stepsService.registerSteps(request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .message("Registered successfully")
                .build());
    }



    @GetMapping
    public ResponseEntity<ApiResponse<?>> getStepsSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){

        List<StepsByDate> response = stepsService.getStepsByDates(startDate, endDate);

        return ResponseEntity.ok(ApiResponse.<List<StepsByDate>>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(response)
                .message("Obtained successfully")
                .build());

    }

    @GetMapping("/details")
    public ResponseEntity<ApiResponse<?>> getStepsDetails(
            @RequestParam LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){

        List<StepsDetailed> response = stepsService.getStepsDetailsByDates(startDate, endDate);

        return ResponseEntity.ok(ApiResponse.<List<StepsDetailed> >builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(response)
                .message("Obtained successfully")
                .build());

    }

}
