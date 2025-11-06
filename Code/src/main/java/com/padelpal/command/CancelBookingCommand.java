package com.padelpal.command;

import com.padelpal.booking.Booking;

public class CancelBookingCommand implements Command {
    private final Booking booking;
    public CancelBookingCommand(Booking booking) { this.booking = booking; }

    @Override
    public void execute() {
        booking.cancel();
        System.out.println("[Command] Cancelled " + booking.getId());
    }
}
