package com.padelpal.booking.decorators;

public class EquipmentAddon extends ChargeDecorator {
    public EquipmentAddon(ChargeComponent inner) { super(inner); }

    @Override
    public double total() { return super.total() + 40.0; }

    @Override
    public String breakdown() { return super.breakdown() + " + equipment: 40.0 lei"; }
}
