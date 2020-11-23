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
    public Integer[] calculateFrequency(LocalDateTime startDate, LocalDateTime endDate, BigDecimal target, DeductionFrequency deductionFrequency) {
        int noOfDeductions = 0;
        log.info("start date: " + startDate);
        Integer[] frequency;
        LocalDateTime nextDate = startDate;

        switch (deductionFrequency) {
            case DAILY:
                while (!nextDate.toLocalDate().isEqual(endDate.toLocalDate())) {
                    nextDate = nextDate.plusDays(1);
                    noOfDeductions++;
                }
                break;
            case WEEKLY:
                while (!nextDate.toLocalDate().isEqual(endDate.toLocalDate())) {
                    nextDate = nextDate.plusWeeks(1);
                    noOfDeductions++;
                }
                break;
            case MONTHLY:
                while (nextDate.toLocalDate().isBefore(endDate.toLocalDate())) {
                    nextDate = nextDate.plusMonths(1);
                    noOfDeductions++;
                }
                break;
            }

        frequency = nextDate.toLocalDate().isEqual(endDate.toLocalDate()) ? new Integer[]{noOfDeductions, 0} : new Integer[]{noOfDeductions - 1, noOfDeductions};
        return frequency;
    }


    @Override
    public SavingsPlanStatus returnSavingsStatus (Integer[] frequency, LocalDateTime startDate, LocalDateTime endDate, BigDecimal target, DeductionFrequency deductionFrequency) {
        BigDecimal amount1 = target.divide(BigDecimal.valueOf(frequency[0]), 2, RoundingMode.CEILING);
        BigDecimal amount2 = BigDecimal.ZERO;
        if (frequency[1] > 0) {
           amount2 = target.divide(BigDecimal.valueOf(frequency[1]), 2, RoundingMode.CEILING);
        }
        return new SavingsPlanStatus()
                .withAmount(new BigDecimal[]{amount1, amount2})
                .withdFrequency(frequency)
                .withendDate(endDate)
                .withTarget(target)
                .withFrequencyOfDeduction(deductionFrequency.toString())
                .withStartDate(startDate);
    }
}
