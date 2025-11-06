package com.padelpal.coaching.decorators;

import com.padelpal.coaching.TrainingSessionComponent;

public class VideoRecordingDecorator extends SessionDecorator {
    public VideoRecordingDecorator(TrainingSessionComponent inner) { super(inner); }
    @Override public String description() { return inner.description() + " + video recording"; }
    @Override public double cost() { return inner.cost() + 60.0; }
}
