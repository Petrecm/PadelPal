package com.padelpal.booking;

import com.padelpal.model.Court;
import com.padelpal.booking.states.*;

public class Booking {
    private final String id;
    private final Court court;
    private BookingState state = new RequestedState();

    public Booking(String id, Court court){ this.id=id; this.court=court; }
    public String getId(){ return id; }
    public Court getCourt(){ return court; }
    public BookingState getState(){ return state; }
    public void setState(BookingState s){ this.state = s; }

    public void confirm(){ state.confirm(this); }
    public void start(){ state.start(this); }
    public void complete(){ state.complete(this); }
    public void cancel(){ state.cancel(this); }

    @Override public String toString(){
        return "Booking(" + id + ", " + court + ", state=" + state.getName() + ")";
    }
}
