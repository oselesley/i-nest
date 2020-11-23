package com.fcmb.inestservice.services.serviceImpl;

import com.fcmb.inestservice.models.SavingsPlanStatus;
import com.fcmb.inestservice.services.SavingsService;
import com.fcmb.inestservice.utils.DeductionFrequency;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;


@Service
public class SavingsServiceImpl implements SavingsService {
    Logger log = LoggerFactory.logger(SavingsServiceImpl.class);

    @Override
    public Integer[] calculateSavingsStatus(LocalDateTime startDate, LocalDateTime endDate, BigDecimal target, DeductionFrequency deductionFrequency) {
        int noOfDeductions = 0;
        Integer[] frequency;
                LocalDateTime nextDate = startDate;
        while (nextDate.isBefore(startDate)) {
            switch (deductionFrequency) {
                case DAILY:
                    noOfDeductions++;
                    nextDate.plusDays(1);
                case WEEKLY:
                    noOfDeductions++;
                    nextDate.plusWeeks(1);
                case MONTHLY:
                    noOfDeductions++;
                    nextDate.plusMonths(1);
            }
        }
        frequency = nextDate.isEqual(endDate) ? new Integer[]{noOfDeductions, 0} : new Integer[]{noOfDeductions, noOfDeductions+1};
        return frequency;
    }

    @Override
    public SavingsPlanStatus returnSavingsStatus (Integer[] frequency, LocalDateTime startDate, LocalDateTime endDate, BigDecimal target, DeductionFrequency deductionFrequency) {
        BigDecimal amount1 = target.divide(BigDecimal.valueOf(frequency[0])).setScale(2, RoundingMode.CEILING);
        BigDecimal amount2 = BigDecimal.ZERO;
        if (frequency[1] > 0) {
           amount2 = target.divide(BigDecimal.valueOf(frequency[2])).setScale(2, RoundingMode.CEILING);
        }
        return new SavingsPlanStatus()
                .withAmount(new BigDecimal[]{amount1, amount2})
                .withdFrequency(frequency)
                .withendDate(endDate)
                .withFrequencyOfDeduction(deductionFrequency.toString())
                .withStartDate(startDate);
    }
}
