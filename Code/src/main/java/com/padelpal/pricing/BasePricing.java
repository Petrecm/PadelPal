package com.padelpal.pricing;

import com.padelpal.model.Court;

public class BasePricing implements PricingStrategy {
    @Override
    public double calculatePrice(Court court, int hours) {
        return court.baseRatePerHour() * hours;
    }
}
