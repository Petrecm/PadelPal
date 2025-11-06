package com.padelpal.booking.states;
import com.padelpal.booking.Booking;

public class RequestedState implements BookingState {
    public void confirm(Booking b){ b.setState(new ConfirmedState()); }
    public void start(Booking b){ throw new IllegalStateException("Confirm first"); }
    public void complete(Booking b){ throw new IllegalStateException("Start first"); }
    public void cancel(Booking b){ b.setState(new CancelledState()); }
    public String getName(){ return "Requested"; }
}
