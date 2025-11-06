package com.padelpal.main;

import com.padelpal.factory.CourtFactory;
import com.padelpal.model.Court;
import com.padelpal.booking.Booking;

// ===== Strategy (pricing) =====
import com.padelpal.pricing.BasePricing;
import com.padelpal.pricing.MemberDiscountStrategy;
import com.padelpal.pricing.PeakHourStrategy;
import com.padelpal.pricing.WeatherStrategy;
import com.padelpal.pricing.EventStrategy;
import com.padelpal.pricing.PricingStrategy;

// ===== Facade =====
import com.padelpal.facade.BookingFacade;
import com.padelpal.facade.CoachFacade;

// ===== Observer (events) =====
import com.padelpal.events.EventPublisher;
import com.padelpal.events.BillingService;
import com.padelpal.events.NotificationService;
import com.padelpal.events.AnalyticsService;
import com.padelpal.events.IoTService;

// ===== Command =====
import com.padelpal.command.Command;
import com.padelpal.command.CreateBookingCommand;
import com.padelpal.command.CancelBookingCommand;

// ===== Booking decorators (add-ons over priced booking) =====
import com.padelpal.booking.decorators.BaseCharge;
import com.padelpal.booking.decorators.ChargeComponent;
import com.padelpal.booking.decorators.EquipmentAddon;
import com.padelpal.booking.decorators.TowelAddon;
import com.padelpal.booking.decorators.LockerAddon;

// ===== Coaching decorators (session add-ons) =====
import com.padelpal.coaching.TrainingSessionComponent;

public class App {

    public static void main(String[] args) {

        System.out.println("\n=== PadelPal — Milestone 2 Proof of Concept ===");

        // ---------------------------------------------------------------------
        // 1) Strategy chain (Base -> Member -> Peak -> Weather -> Event)
        // ---------------------------------------------------------------------
        PricingStrategy pricing = new EventStrategy(
                new WeatherStrategy(
                        new PeakHourStrategy(
                                new MemberDiscountStrategy(
                                        new BasePricing()))));

        // ---------------------------------------------------------------------
        // 2) Observer setup
        // ---------------------------------------------------------------------
        EventPublisher events = new EventPublisher();
        events.register(new BillingService());
        events.register(new NotificationService());
        events.register(new AnalyticsService());
        events.register(new IoTService());

        // ---------------------------------------------------------------------
        // 3) Facade run (uses Factory + State + Strategy + Observer)
        // ---------------------------------------------------------------------
        BookingFacade facade = new BookingFacade(new CourtFactory(), pricing, events);
        var quote = facade.createAndPrice("Premium", "B-2001", 2, true);
        System.out.println("\n[Facade] " + quote);

        // Keep a reference to the court for decorator demo below
        Court court = quote.booking.getCourt();

        // ---------------------------------------------------------------------
        // 4) State demo (second booking, simple cancel flow)
        // ---------------------------------------------------------------------
        CourtFactory f = new CourtFactory();
        Court court2 = f.create("Outdoor");
        Booking b2 = new Booking("B-2002", court2);
        System.out.println("\n[State] Init: " + b2);
        b2.cancel(); // allowed from Requested
        System.out.println("[State] After cancel: " + b2);

        // ---------------------------------------------------------------------
        // 5) Command demo (create then cancel on a fresh booking)
        // ---------------------------------------------------------------------
        Booking b3 = new Booking("B-2003", court2);
        System.out.println("\n[Command] Queue two commands on " + b3.getId());
        Command c1 = new CreateBookingCommand(b3);
        Command c2 = new CancelBookingCommand(b3);
        c1.execute();
        c2.execute();

        // ---------------------------------------------------------------------
        // 6) Booking Decorators (extras added over the priced booking)
        //     Start from the final price returned by Strategy chain (quote.price)
        // ---------------------------------------------------------------------
        ChargeComponent charge = new BaseCharge(quote.price, "Base booking (all strategies, 2h)");
        charge = new EquipmentAddon(charge);
        charge = new TowelAddon(charge);
        charge = new LockerAddon(charge);

        System.out.println("\n[Booking Decorators] " + charge.breakdown());
        System.out.println("[Booking Decorators] Total with add-ons: " + charge.total() + " lei");

        // ---------------------------------------------------------------------
        // 7) Coaching Decorators via CoachFacade (separate decorator family)
        // ---------------------------------------------------------------------
        CoachFacade coachFacade = new CoachFacade();
        TrainingSessionComponent session =
                coachFacade.buildSession("Coach Alex", 90, true, true); // equipment + video

        System.out.println("\n[Coaching Decorators] " + session.description());
        System.out.println("[Coaching Decorators] Total coaching cost: " + session.cost() + " lei");

        System.out.println("\n=== Done ===\n");
    }
}
