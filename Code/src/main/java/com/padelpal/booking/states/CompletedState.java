package com.padelpal.booking.states;
import com.padelpal.booking.Booking;

public class CompletedState implements BookingState {
    public void confirm(Booking b){ }
    public void start(Booking b){ }
    public void complete(Booking b){ }
    public void cancel(Booking b){ throw new IllegalStateException("Already completed"); }
    public String getName(){ return "Completed"; }
}
