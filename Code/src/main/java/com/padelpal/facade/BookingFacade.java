package com.padelpal.facade;

import com.padelpal.factory.CourtFactory;
import com.padelpal.model.Court;
import com.padelpal.booking.Booking;
import com.padelpal.pricing.PricingStrategy;
import com.padelpal.events.EventPublisher;
import com.padelpal.events.BookingEvent;

public class BookingFacade {
    public static final class Quote {
        public final Booking booking;
        public final double price;
        public Quote(Booking booking, double price){ this.booking=booking; this.price=price; }
        @Override public String toString(){ return booking + " | price=" + price + " lei"; }
    }

    private final CourtFactory courtFactory;
    private final PricingStrategy pricing;
    private final EventPublisher events; // NEW

    public BookingFacade(CourtFactory courtFactory, PricingStrategy pricing, EventPublisher events) {
        this.courtFactory = courtFactory;
        this.pricing = pricing;
        this.events = events;
    }

    public Quote createAndPrice(String courtType, String bookingId, int hours, boolean completeNow) {
        Court court = courtFactory.create(courtType);
        Booking b = new Booking(bookingId, court);
        b.confirm(); b.start();
        if (completeNow) b.complete();
        double price = pricing.calculatePrice(court, hours);

        if (completeNow && events != null) { // publish Observer event
            events.publish(new BookingEvent(BookingEvent.COMPLETED, bookingId, price));
        }
        return new Quote(b, price);
    }
}
