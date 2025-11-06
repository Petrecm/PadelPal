package com.padelpal.coaching;

public class TrainingSession implements TrainingSessionComponent {
    private final String coachName;
    private final int minutes;

    public TrainingSession(String coachName, int minutes) {
        this.coachName = coachName;
        this.minutes = minutes;
    }

    @Override
    public String description() {
        return "Coaching session with " + coachName + " (" + minutes + " min)";
    }

    @Override
    public double cost() {
        // base: 120 lei / 60 min
        return 120.0 * (minutes / 60.0);
    }
}
