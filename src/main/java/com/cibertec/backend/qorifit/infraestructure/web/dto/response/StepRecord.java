package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepRecord {

    private Long stepCount;
    private LocalTime recordedAt;

}
