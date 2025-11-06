package com.padelpal.factory;

import com.padelpal.model.*;

public class CourtFactory {
    public Court create(String type){
        return switch (type) {
            case "Indoor"  -> new IndoorCourt("C-IN-1","Indoor Court 1");
            case "Outdoor" -> new OutdoorCourt("C-OUT-1","Outdoor Court 1");
            case "Premium" -> new PremiumCourt("C-PR-1","Premium Court 1");
            default -> throw new IllegalArgumentException("Unknown court type: " + type);
        };
    }
}
