package com.padelpal.booking.decorators;

public abstract class ChargeDecorator implements ChargeComponent {
    protected final ChargeComponent inner;
    protected ChargeDecorator(ChargeComponent inner) { this.inner = inner; }

    @Override
    public double total() { return inner.total(); }

    @Override
    public String breakdown() { return inner.breakdown(); }
}
