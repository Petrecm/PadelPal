package com.padelpal.model;
public class OutdoorCourt extends Court {
    public OutdoorCourt(String id, String name){ super(id,name); }
    @Override public double baseRatePerHour(){ return 60.0; }
    @Override public boolean isOutdoor(){ return true; }
}
