package com.padelpal.events;

public record BookingEvent(String type, String bookingId, double amount) {
    public static final String COMPLETED = "BOOKING_COMPLETED";
}
