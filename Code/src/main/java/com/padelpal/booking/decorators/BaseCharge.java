package com.padelpal.booking.decorators;

public class BaseCharge implements ChargeComponent {
    private final double baseAmount;
    private final String label;

    public BaseCharge(double baseAmount, String label) {
        this.baseAmount = baseAmount;
        this.label = label;
    }

    @Override
    public double total() { return baseAmount; }

    @Override
    public String breakdown() {
        return label + ": " + baseAmount + " lei";
    }
}
