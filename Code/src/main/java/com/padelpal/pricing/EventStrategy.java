package com.padelpal.pricing;
import com.padelpal.model.Court;
public class EventStrategy implements PricingStrategy {
    private final PricingStrategy base;
    public EventStrategy(PricingStrategy base){ this.base = base; }
    public double calculatePrice(Court c, int h){ return base.calculatePrice(c, h) * 1.1; } // +10% during events
}