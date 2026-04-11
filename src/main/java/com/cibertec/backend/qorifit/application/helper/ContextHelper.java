package com.cibertec.backend.qorifit.application.helper;

import com.cibertec.backend.qorifit.infraestructure.web.exception.BusinessException;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContextHelper {

    public Long extractUserId() {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (Long) auth.getPrincipal();
        }
        catch (Exception e){
            throw new BusinessException(InternalCodes.UNAUTHORIZED, "Token inválido");
        }

    }
}
