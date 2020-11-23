package com.fcmb.inestservice.services.serviceImpl;

import com.fcmb.inestservice.models.SavingsPlanStatus;
import com.fcmb.inestservice.services.SavingsService;
import com.fcmb.inestservice.utils.DeductionFrequency;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
        Integer[] frequency = savingsService.calculateSavingsStatus(LocalDateTime.now(), LocalDateTime.now().plusMonths(2), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);
        log.info("Frequency: " + Arrays.toString(frequency));

        assertEquals(frequency.length, 2);
        assertEquals(frequency[0], 2);
        assertEquals(frequency[1], 0);

        frequency = savingsService.calculateSavingsStatus(LocalDateTime.now(), LocalDateTime.now().plusMonths(2).plusDays(5), BigDecimal.valueOf(500_000), DeductionFrequency.MONTHLY);
        log.info("Frequency: " + Arrays.toString(frequency));
        assertEquals(frequency.length, 2);
        assertEquals(frequency[0], 2);
        assertEquals(frequency[1], 3);
    }

    @Test
    void returnSavingsStatus() {
    }
}