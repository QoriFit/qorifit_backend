package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.application.service.StepsService;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.StepsRegister;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/step")
public class StepController {

    private final StepsService stepsService;

//    @GetMapping("/resume")
//
//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse<?>> addFootSteps(
//            @RequestBody Long steps
//    ){
//
//    }


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

}
