package com.fcmb.inestservice.services.serviceImpl;

import com.fcmb.inestservice.models.SavingsPlanStatus;
import com.fcmb.inestservice.services.SavingsService;
import com.fcmb.inestservice.utils.DeductionFrequency;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SavingsServiceImplTest {
    private SavingsService savingsService;
    Logger log = LoggerFactory.logger(SavingsServiceImplTest.class);

    @BeforeEach
    void setUp() {
        savingsService = new SavingsServiceImpl();
    }

    @Test
    void calculateSavingsStatus() {
        Integer[] frequency = savingsService.calculateFrequency(LocalDateTime.now(), LocalDateTime.now().plusMonths(2), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);
        log.info("Frequency: " + Arrays.toString(frequency));

        assertEquals(frequency.length, 2);
        assertEquals(frequency[0], 2);
        assertEquals(frequency[1], 0);

        frequency = savingsService.calculateFrequency(LocalDateTime.now(), LocalDateTime.now().plusMonths(2).plusDays(5), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);
        log.info("Frequency: " + Arrays.toString(frequency));
        assertEquals(frequency.length, 2);
        assertEquals(frequency[0], 2);
        assertEquals(frequency[1], 3);
    }

    @Test
    void returnSavingsStatus() {
        Integer[] frequency = savingsService.calculateFrequency(LocalDateTime.now(), LocalDateTime.now().plusMonths(2), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);
        SavingsPlanStatus savingsPlanStatus = savingsService.returnSavingsStatus(frequency, LocalDateTime.now(), LocalDateTime.now().plusMonths(2), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);

        log.info(savingsPlanStatus);

        assertEquals(savingsPlanStatus.getAmount()[0], BigDecimal.valueOf(250_000).setScale(2, RoundingMode.CEILING));

        frequency = savingsService.calculateFrequency(LocalDateTime.now(), LocalDateTime.now().plusMonths(12).plusDays(3), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);
        savingsPlanStatus = savingsService.returnSavingsStatus(frequency, LocalDateTime.now(), LocalDateTime.now().plusMonths(12).plusDays(3), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);
        log.info(savingsPlanStatus);

        assertEquals(savingsPlanStatus.getAmount().length, 2);
        assertEquals(savingsPlanStatus.getAmount()[0].toString(), "41666.67");
        assertEquals(savingsPlanStatus.getAmount()[1].toString(), "38461.54");
    }
}