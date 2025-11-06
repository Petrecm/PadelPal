package com.padelpal.pricing;

import com.padelpal.model.Court;

public interface PricingStrategy {
    double calculatePrice(Court court, int hours);
}
