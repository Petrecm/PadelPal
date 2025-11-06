package com.padelpal.booking.states;
import com.padelpal.booking.Booking;

public class ConfirmedState implements BookingState {
    public void confirm(Booking b){ /* no-op */ }
    public void start(Booking b){ b.setState(new InProgressState()); }
    public void complete(Booking b){ throw new IllegalStateException("Start first"); }
    public void cancel(Booking b){ b.setState(new CancelledState()); }
    public String getName(){ return "Confirmed"; }
}
