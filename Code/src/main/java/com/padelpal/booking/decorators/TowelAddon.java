package com.padelpal.booking.decorators;

public class TowelAddon extends ChargeDecorator {
    public TowelAddon(ChargeComponent inner) { super(inner); }

    @Override
    public double total() { return super.total() + 10.0; }

    @Override
    public String breakdown() { return super.breakdown() + " + towel: 10.0 lei"; }
}
