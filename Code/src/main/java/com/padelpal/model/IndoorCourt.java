package com.padelpal.model;
public class IndoorCourt extends Court {
    public IndoorCourt(String id, String name){ super(id,name); }
    @Override public double baseRatePerHour(){ return 80.0; }
    @Override public boolean isOutdoor(){ return false; }
}
