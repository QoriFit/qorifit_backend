package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.security.JwtUtils;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.LoginResponse;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.UserData;
import com.cibertec.backend.qorifit.infraestructure.web.exception.BusinessException;
import com.cibertec.backend.qorifit.infraestructure.web.exception.UserNotFoundException;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final UserRepoImpl userRepo;
    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(String email, String password) {

        String normalizedEmail = email.trim().toLowerCase();

        UserEntity user = userRepo.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuario con email "+normalizedEmail+" encontrado"));

        if (user.getIsActive() != true){
            throw new BusinessException(InternalCodes.USER_DISABLE, "El usuario se encuentra inhabilitado");
        }

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new BusinessException(InternalCodes.INVALID_CREDENTIALS, "Credenciales inválidas");
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(normalizedEmail, null, null);

        String token = jwtUtils.createAccessToken(authentication, user.getId());

        return LoginResponse.builder()
                .username(user.getUsername())
                .email(normalizedEmail)
                .accessToken(token)
                .userData(UserData.builder()
                        .birthDate(user.getBirthdate())
                        .maxCaloriesPerDay(user.getMaxCaloriesPerDay())
                        .stepsGoalPerDay(user.getStepsPerDay())
                        .goal(user.getGoal())

                        .weight(user.getWeight())
                        .height(user.getHeight())
                        .build())
                .build();
    }

    @Transactional
    public LoginResponse register(
            String username, String email,
            String password, LocalDate birthdate,
            BigDecimal weight, Long height,
            String goal, Long stepsGoal,
            BigDecimal maxCaloriesPerDay) {

        String normalizedEmail = email.trim().toLowerCase();

        if (userRepo.findByEmail(normalizedEmail).isPresent()) {
            throw new BusinessException(
                    InternalCodes.DUPLICATED_EMAIL_ADDRESS,
                    "El email ya se encuentra registrado"
            );
        }

        UserEntity newUser = UserEntity.builder()
                .username(username)
                .email(normalizedEmail)
                .password(passwordEncoder.encode(password))
                .birthdate(birthdate)
                .height(height)
                .goal(goal)
                .stepsPerDay(stepsGoal)
                .maxCaloriesPerDay(maxCaloriesPerDay)
                .weight(weight)
                .isActive(true)
                .role("user")
                .build();

        userRepo.save(newUser);

        return login(normalizedEmail, password);
    }
}
