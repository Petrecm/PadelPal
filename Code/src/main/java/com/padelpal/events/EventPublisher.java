package com.padelpal.events;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {
    private final List<Observer> observers = new ArrayList<>();
    public void register(Observer o){ observers.add(o); }
    public void unregister(Observer o){ observers.remove(o); }
    public void publish(BookingEvent e){ for (var o: observers) o.onEvent(e); }
}
