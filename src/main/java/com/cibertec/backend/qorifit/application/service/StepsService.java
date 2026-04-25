package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.application.helper.ContextHelper;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.StepEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.StepRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.StepsRegister;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepRecord;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepsByDate;
import com.cibertec.backend.qorifit.infraestructure.web.dto.response.StepsDetailed;
import com.cibertec.backend.qorifit.infraestructure.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

//        StepEntity stepLog = stepRepo.findByUserIdAndDate(userId, request.date())
//                .orElseGet(StepEntity::new);

        StepEntity stepEntity = new StepEntity();

        stepEntity.setUser(user);
        stepEntity.setDate(request.date());
        stepEntity.setCount(request.stepCount().intValue());

        stepRepo.save(stepEntity);

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

    public List<StepsDetailed> getStepsDetailsByDates(LocalDate startDate, LocalDate endDate) {

        Long userId = contextHelper.extractUserId();

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        List<StepEntity> stepEntities;

        if (endDate == null) {
            stepEntities = stepRepo.getStepsByUserIdAndDateSince(user.getId(), startDate);
        } else {
            stepEntities = stepRepo.getStepsByUserIdAndDatesRange(userId, startDate, endDate);
        }

        return stepEntities.stream()
                .collect(Collectors.groupingBy(StepEntity::getDate))
                .entrySet().stream()
                .map(entry -> {
                    List<StepEntity> daySteps = entry.getValue();

                    List<StepRecord> records = daySteps.stream()
                            .map(step -> StepRecord.builder()
                                    .stepCount((long) step.getCount())
                                    .recordedAt(step.getCreatedAt().toLocalTime())
                                    .build())
                            .toList();

                    int dailyTotal = daySteps.stream()
                            .mapToInt(StepEntity::getCount)
                            .sum();

                    return StepsDetailed.builder()
                            .date(entry.getKey())
                            .records(records)
                            .totalStepsPerDay(dailyTotal)
                            .build();
                })
                .sorted(Comparator.comparing(StepsDetailed::getDate))
                .toList();
    }

}
