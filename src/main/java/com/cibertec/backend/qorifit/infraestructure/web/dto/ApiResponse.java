package com.cibertec.backend.qorifit.infraestructure.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>{
    int code;
    String message;
    T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Map<String, Object> meta;
}