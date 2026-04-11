package com.cibertec.backend.qorifit.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("ingredients")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IngredientsController {
}
