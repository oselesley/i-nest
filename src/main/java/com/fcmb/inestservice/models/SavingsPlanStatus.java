package com.fcmb.inestservice.models;

import com.fcmb.inestservice.utils.DeductionFrequency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fcmb.inestservice.utils.DeductionFrequency.*;

@Getter
@Setter
public class SavingsPlanStatus {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal target;
    private Integer[] frequency;
    private BigDecimal[] amount;
    private DeductionFrequency frequencyOfDeduction;

    public SavingsPlanStatus withStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public SavingsPlanStatus withendDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public SavingsPlanStatus withdFrequency(Integer[] frequency) {
        this.frequency = frequency;
        return this;
    }

    public SavingsPlanStatus withAmount(BigDecimal[] amount) {
        this.amount = amount;
        return this;
    }

    public SavingsPlanStatus withTarget(BigDecimal target) {
        this.target = target;
        return this;
    }

    public SavingsPlanStatus withFrequencyOfDeduction(String frequencyOfDeduction) {
        setFrequencyOfDeduction(frequencyOfDeduction);
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
