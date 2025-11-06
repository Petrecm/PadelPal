package com.padelpal.coaching.decorators;

import com.padelpal.coaching.TrainingSessionComponent;

public class EquipmentRentalDecorator extends SessionDecorator {
    public EquipmentRentalDecorator(TrainingSessionComponent inner) { super(inner); }
    @Override public String description() { return inner.description() + " + equipment rental"; }
    @Override public double cost() { return inner.cost() + 40.0; }
}
