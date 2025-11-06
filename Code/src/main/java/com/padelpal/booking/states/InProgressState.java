package com.padelpal.booking.states;
import com.padelpal.booking.Booking;

public class InProgressState implements BookingState {
    public void confirm(Booking b){ /* no-op */ }
    public void start(Booking b){ /* no-op */ }
    public void complete(Booking b){ b.setState(new CompletedState()); }
    public void cancel(Booking b){ throw new IllegalStateException("Cannot cancel in progress"); }
    public String getName(){ return "InProgress"; }
}
