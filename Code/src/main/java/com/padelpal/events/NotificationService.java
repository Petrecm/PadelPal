package com.padelpal.events;

public class NotificationService implements Observer {
    @Override public void onEvent(BookingEvent e) {
        if (BookingEvent.COMPLETED.equals(e.type())) {
            System.out.println("[Notify] Booking " + e.bookingId() + " completed. Amount " + e.amount() + " lei");
        }
    }
}
