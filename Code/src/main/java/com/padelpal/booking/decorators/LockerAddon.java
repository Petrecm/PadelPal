package com.padelpal.booking.decorators;

public class LockerAddon extends ChargeDecorator {
    public LockerAddon(ChargeComponent inner) { super(inner); }

    @Override
    public double total() { return super.total() + 15.0; }

    @Override
    public String breakdown() { return super.breakdown() + " + locker: 15.0 lei"; }
}
