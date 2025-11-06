package com.padelpal.coaching.decorators;

import com.padelpal.coaching.TrainingSessionComponent;

public abstract class SessionDecorator implements TrainingSessionComponent {
    protected final TrainingSessionComponent inner;
    protected SessionDecorator(TrainingSessionComponent inner) { this.inner = inner; }

    @Override public String description() { return inner.description(); }
    @Override public double cost() { return inner.cost(); }
}
