package com.cibertec.backend.qorifit.application.service;

import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.StepEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.entity.UserEntity;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.StepRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.persistence.jpa.repository.impl.UserRepoImpl;
import com.cibertec.backend.qorifit.infraestructure.web.dto.request.StepsRegister;
import com.cibertec.backend.qorifit.infraestructure.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StepsService {

    private final StepRepoImpl stepRepo;
    private final UserRepoImpl userRepo;

    @Transactional
    public void registerSteps(StepsRegister request){

        UserEntity user = userRepo.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.userId()));

        StepEntity newStepLog = new StepEntity();
        newStepLog.setUser(user);
        newStepLog.setDate(request.date());
        newStepLog.setCount(request.stepCount().intValue());

        StepEntity persistedStep = stepRepo.save(newStepLog);

    }

    @Transactional
    public


}
