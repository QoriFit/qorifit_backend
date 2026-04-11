package com.cibertec.backend.qorifit.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
