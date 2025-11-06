package com.padelpal.pricing;

import com.padelpal.model.Court;

public class MemberDiscountStrategy implements PricingStrategy {
    private final PricingStrategy base;
    public MemberDiscountStrategy(PricingStrategy base) { this.base = base; }

    @Override
    public double calculatePrice(Court court, int hours) {
        double price = base.calculatePrice(court, hours);
        return price * 0.9; // 10% discount for members
    }
}
