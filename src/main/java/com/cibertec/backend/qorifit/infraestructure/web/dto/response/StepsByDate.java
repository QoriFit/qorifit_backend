package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class StepsByDate {

    private LocalDate date;
    private int steps;

}
