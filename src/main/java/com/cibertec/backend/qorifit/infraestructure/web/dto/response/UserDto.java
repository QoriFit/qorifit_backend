package com.cibertec.backend.qorifit.infraestructure.web.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull
    private Long userId;
    private String username;
    private String imageUrl;
    private String email;

    private Boolean status;

}
