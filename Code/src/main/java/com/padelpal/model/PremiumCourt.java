package com.padelpal.model;
public class PremiumCourt extends Court {
    public PremiumCourt(String id, String name){ super(id,name); }
    @Override public double baseRatePerHour(){ return 100.0; }
    @Override public boolean isOutdoor(){ return false; }
}
