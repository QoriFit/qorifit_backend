package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.StepsService;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.StepsDateRange;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.StepsRegister;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepsByDate;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody @Valid StepsDateRange request
    ){

        List<StepsByDate> response = stepsService.getStepsByDates(request.startDate() , request.endDate());

        return ResponseEntity.ok(ApiResponse.<List<StepsByDate>>builder()
                .code(InternalCodes.SUCCESS.getCode())
                .data(response)
                .message("Obtained successfully")
                .build());

    }

}
