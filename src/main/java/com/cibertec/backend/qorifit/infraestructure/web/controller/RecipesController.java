package com.cibertec.backend.qorifit.infraestructure.web.controller;

import com.cibertec.backend.qorifit.domain.OrderEnum;
import com.cibertec.backend.qorifit.infraestructure.web.dto.ApiResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepsByDate;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipesController {

//    @GetMapping
//    public ResponseEntity<ApiResponse<?>> getRecipes(
//            @RequestParam Long countryId,
//            @RequestParam String name,
//            @RequestParam Boolean popularity
//    ){
//
//        List<>
//
//        return ResponseEntity.ok(ApiResponse.<List<StepsByDate>>builder()
//                .code(InternalCodes.SUCCESS.getCode())
//                .data(response)
//                .message("Obtained successfully")
//                .build());
//    }
}
