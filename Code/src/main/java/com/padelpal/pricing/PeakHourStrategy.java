package com.padelpal.pricing;

import com.padelpal.model.Court;

public class PeakHourStrategy implements PricingStrategy {
    private final PricingStrategy base;
    public PeakHourStrategy(PricingStrategy base) { this.base = base; }

    @Override
    public double calculatePrice(Court court, int hours) {
        double price = base.calculatePrice(court, hours);
        return price * 1.2; // +20% during peak hours
    }
}
