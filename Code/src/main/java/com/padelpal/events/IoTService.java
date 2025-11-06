package com.padelpal.events;
public class IoTService implements Observer {
    public void onEvent(BookingEvent e){
        if (BookingEvent.COMPLETED.equals(e.type()))
            System.out.println("[IoT] Turning off lights for " + e.bookingId());
    }
}