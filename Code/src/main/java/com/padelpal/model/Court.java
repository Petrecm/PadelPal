package com.padelpal.model;

public abstract class Court {
    private final String id, name;
    protected Court(String id, String name){ this.id=id; this.name=name; }
    public String getId(){ return id; }
    public String getName(){ return name; }
    public abstract double baseRatePerHour();
    public abstract boolean isOutdoor();
    @Override public String toString(){ return getClass().getSimpleName()+"["+id+"|"+name+"]"; }
}
