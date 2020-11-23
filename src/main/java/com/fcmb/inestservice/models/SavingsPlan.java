package com.fcmb.inestservice.models;

import com.fcmb.inestservice.utils.DeductionFrequency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static com.fcmb.inestservice.utils.DeductionFrequency.*;
import static java.math.BigInteger.*;

@Getter
@Setter
public class SavingsPlan {
    private User user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigInteger totalAmount = ZERO;
    private Integer duration;
    private BigDecimal amount;
    private DeductionFrequency frequencyOfDeduction;

    public SavingsPlan withStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public SavingsPlan withendDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public SavingsPlan withdurationDate(Integer duration) {
        this.duration = duration;
        return this;
    }

    public SavingsPlan withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public SavingsPlan withFrequencyOfDedcution(String frequencyOfDedcution) {
        setFrequencyOfDeduction(frequencyOfDedcution);
        return this;
    }

    public void setFrequencyOfDeduction(String frequencyOfDeduction) {
        switch (frequencyOfDeduction.toLowerCase()) {
            case "day": case "daily":
                this.frequencyOfDeduction = DAILY;
                return;
            case "week": case "weekly":
                this.frequencyOfDeduction = WEEKLY;
                return;
            case "month": case "monthly":
                this.frequencyOfDeduction = MONTHLY;
                return;
            default:
                this.frequencyOfDeduction = MONTHLY;
        }
    }
}
