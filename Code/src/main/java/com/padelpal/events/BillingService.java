package com.padelpal.events;

public class BillingService implements Observer {
    @Override public void onEvent(BookingEvent e) {
        if (BookingEvent.COMPLETED.equals(e.type())) {
            System.out.println("[Billing] Invoice issued for " + e.bookingId() + " = " + e.amount() + " lei");
        }
    }
}
