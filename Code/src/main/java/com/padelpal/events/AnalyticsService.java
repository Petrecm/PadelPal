package com.padelpal.events;
public class AnalyticsService implements Observer {
    public void onEvent(BookingEvent e){
        if (BookingEvent.COMPLETED.equals(e.type()))
            System.out.println("[Analytics] Logged completed booking " + e.bookingId());
    }
}