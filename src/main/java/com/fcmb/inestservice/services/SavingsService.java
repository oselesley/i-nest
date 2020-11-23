package com.fcmb.inestservice.services;

import com.fcmb.inestservice.models.SavingsPlanStatus;
import com.fcmb.inestservice.utils.DeductionFrequency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SavingsService {
    Integer[] calculateSavingsStatus(LocalDateTime startDate, LocalDateTime endDate, BigDecimal target, DeductionFrequency deductionFrequency);

    SavingsPlanStatus returnSavingsStatus (Integer[] frequency, LocalDateTime startDate, LocalDateTime endDate, BigDecimal target, DeductionFrequency deductionFrequency);
}
