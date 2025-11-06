package com.padelpal.facade;

import com.padelpal.coaching.TrainingSession;
import com.padelpal.coaching.TrainingSessionComponent;
import com.padelpal.coaching.decorators.EquipmentRentalDecorator;
import com.padelpal.coaching.decorators.VideoRecordingDecorator;

public class CoachFacade {

    public TrainingSessionComponent buildSession(String coachName, int minutes,
                                                 boolean withEquipment, boolean withVideo) {
        TrainingSessionComponent s = new TrainingSession(coachName, minutes);
        if (withEquipment) s = new EquipmentRentalDecorator(s);
        if (withVideo)     s = new VideoRecordingDecorator(s);
        return s;
    }
}
