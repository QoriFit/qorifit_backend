package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.application.helper.ContextHelper;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.StepEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.StepRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.StepsRegister;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepsByDate;
import com.cibertec.backend.qorifit.infraestructure.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StepsService {

    private final StepRepoImpl stepRepo;
    private final UserRepoImpl userRepo;
    private final ContextHelper contextHelper;

    @Transactional
    public void registerSteps(StepsRegister request){

        Long userId = contextHelper.extractUserId();

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        StepEntity newStepLog = new StepEntity();
        newStepLog.setUser(user);
        newStepLog.setDate(request.date());
        newStepLog.setCount(request.stepCount().intValue());

        StepEntity persistedStep = stepRepo.save(newStepLog);

    }

    public List<StepsByDate> getStepsByDates(LocalDate startDate, LocalDate endDate) {

        Long userId = contextHelper.extractUserId();

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        List<StepEntity> stepEntities;

        if (endDate == null) {
            stepEntities = stepRepo.getStepsByUserIdAndDateSince(user.getId(), startDate);
        } else {
            stepEntities = stepRepo.getStepsByUserIdAndDatesRange(userId, startDate, endDate);
        }

        Map<LocalDate, Integer> stepsByDateMap = stepEntities.stream()
                .collect(Collectors.groupingBy(
                        StepEntity::getDate,
                        Collectors.summingInt(StepEntity::getCount)
                ));

        LocalDate end = (endDate != null) ? endDate : LocalDate.now();

        List<StepsByDate> stepsByDates = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(end); date = date.plusDays(1)) {
            stepsByDates.add(StepsByDate.builder()
                    .date(date)
                    .steps(stepsByDateMap.getOrDefault(date, 0))
                    .build());
        }

        return stepsByDates;
    }


}
