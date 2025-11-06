package com.padelpal.booking.states;
import com.padelpal.booking.Booking;

public interface BookingState {
    void confirm(Booking b);
    void start(Booking b);
    void complete(Booking b);
    void cancel(Booking b);
    String getName();
}
