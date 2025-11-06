package com.padelpal.pricing;
import com.padelpal.model.Court;
public class WeatherStrategy implements PricingStrategy {
    private final PricingStrategy base;
    public WeatherStrategy(PricingStrategy base){ this.base = base; }
    public double calculatePrice(Court c, int h){
        double p = base.calculatePrice(c, h);
        return c.isOutdoor() ? p * 0.95 : p; // -5% if outdoor (bad weather promo)
    }
}