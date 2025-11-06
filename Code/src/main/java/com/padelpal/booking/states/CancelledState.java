package com.padelpal.booking.states;
import com.padelpal.booking.Booking;

public class CancelledState implements BookingState {
    public void confirm(Booking b){ throw new IllegalStateException("Cancelled booking"); }
    public void start(Booking b){ throw new IllegalStateException("Cancelled booking"); }
    public void complete(Booking b){ throw new IllegalStateException("Cancelled booking"); }
    public void cancel(Booking b){ }
    public String getName(){ return "Cancelled"; }
}
