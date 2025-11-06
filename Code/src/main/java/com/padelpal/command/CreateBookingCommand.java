package com.padelpal.command;

import com.padelpal.booking.Booking;

public class CreateBookingCommand implements Command {
    private final Booking booking;
    public CreateBookingCommand(Booking booking) { this.booking = booking; }

    @Override
    public void execute() {
        // In a real app you'd persist; in PoC we just log
        System.out.println("[Command] Created " + booking);
    }
}
